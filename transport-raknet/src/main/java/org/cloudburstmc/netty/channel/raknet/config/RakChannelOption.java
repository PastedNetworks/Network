package org.cloudburstmc.netty.channel.raknet.config;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelOption;

public class RakChannelOption<T> extends ChannelOption<T> {

    /**
     * TODO: max amount of child channels per server
     */
    public static final ChannelOption<Integer> RAK_MAX_CHANNELS =
            valueOf(RakChannelOption.class, "RAK_MAX_CHANNELS");

    /**
     * Maximum amount of ordering channels each RakNet connection can have. (1-256)
     */
    public static final ChannelOption<Integer> RAK_ORDERING_CHANNELS =
            valueOf(RakChannelOption.class, "RAK_ORDERING_CHANNELS");

    /**
     * MTU that the RakNet client will use when initially connecting.
     */
    public static final ChannelOption<Integer> RAK_MTU =
            valueOf(RakChannelOption.class, "RAK_MTU");

    /**
     * Unique ID of the RakNet peer sent.
     */
    public static final ChannelOption<Long> RAK_GUID =
            valueOf(RakChannelOption.class, "RAK_GUID");

    /**
     * Maximum allowed connections to the RakNet server. Subsequent connections will be denied.
     */
    public static final ChannelOption<Integer> RAK_MAX_CONNECTIONS =
            valueOf(RakChannelOption.class, "RAK_MAX_CONNECTIONS");

    /**
     * RakNet protocol version to send to remote peer.
     */
    public static final ChannelOption<Integer> RAK_PROTOCOL_VERSION =
            valueOf(RakChannelOption.class, "RAK_PROTOCOL_VERSION");

    /**
     * Versions supported by the RakNet server.
     */
    public static final ChannelOption<int[]> RAK_SUPPORTED_PROTOCOLS =
            valueOf(RakChannelOption.class, "RAK_SUPPORTED_PROTOCOLS");

    /**
     * Magic used to make unconnected packets unique.
     */
    public static final ChannelOption<ByteBuf> RAK_UNCONNECTED_MAGIC =
            valueOf(RakChannelOption.class, "RAK_UNCONNECTED_MAGIC");

    /**
     * Timeout delay used during client offline phase in millis.
     */
    public static final ChannelOption<Long> RAK_CONNECT_TIMEOUT =
            valueOf(RakChannelOption.class, "RAK_CONNECT_TIMEOUT");

    @SuppressWarnings("deprecation")
    protected RakChannelOption() {
        super(null);
    }
}