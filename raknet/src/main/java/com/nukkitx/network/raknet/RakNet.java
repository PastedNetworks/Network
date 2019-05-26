package com.nukkitx.network.raknet;

import com.nukkitx.network.BootstrapUtils;
import com.nukkitx.network.NetworkInterface;
import com.nukkitx.network.util.Preconditions;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.DatagramPacket;

import javax.annotation.Nonnegative;
import javax.annotation.ParametersAreNonnullByDefault;
import java.net.InetSocketAddress;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@ParametersAreNonnullByDefault
public abstract class RakNet implements NetworkInterface, AutoCloseable {
    final long guid = ThreadLocalRandom.current().nextLong();
    final Bootstrap bootstrap;
    final Executor executor;
    final InetSocketAddress bindAddress;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final ScheduledFuture<?> tickFuture;
    int protocolVersion = RakNetConstants.RAKNET_PROTOCOL_VERSION;
    private volatile boolean closed;

    RakNet(InetSocketAddress bindAddress, ScheduledExecutorService scheduler, Executor executor) {
        this.bindAddress = bindAddress;
        this.executor = executor;

        this.bootstrap = new Bootstrap().option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

        BootstrapUtils.setupBootstrap(this.bootstrap, true);

        tickFuture = scheduler.scheduleAtFixedRate(this::onTick, 50, 50, TimeUnit.MILLISECONDS);
    }

    static void send(ChannelHandlerContext ctx, InetSocketAddress recipient, ByteBuf buffer) {
        ctx.writeAndFlush(new DatagramPacket(buffer, recipient), ctx.voidPromise());
    }

    public CompletableFuture<Void> bind() {
        Preconditions.checkState(this.running.compareAndSet(false, true), "RakNet has already been started");

        CompletableFuture<Void> future = bindInternal();

        future.whenComplete((aVoid, throwable) -> {
            if (throwable != null) {
                // Failed to start. Set running to false
                this.running.compareAndSet(true, false);
            }
        });
        return future;
    }

    public void close() {
        this.tickFuture.cancel(false);
        this.closed = true;
    }

    protected abstract CompletableFuture<Void> bindInternal();

    protected abstract void onTick();

    public boolean isRunning() {
        return this.running.get();
    }

    public boolean isClosed() {
        return closed;
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    @Nonnegative
    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(@Nonnegative int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public InetSocketAddress getBindAddress() {
        return bindAddress;
    }

    public long getGuid() {
        return guid;
    }
}
