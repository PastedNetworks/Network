package org.cloudburstmc.netty.channel.raknet;

public enum RakDisconnectReason {
    CLOSED_BY_REMOTE_PEER,
    SHUTTING_DOWN,
    DISCONNECTED,
    TIMED_OUT,
    CONNECTION_REQUEST_FAILED,
    ALREADY_CONNECTED,
    NO_FREE_INCOMING_CONNECTIONS,
    INCOMPATIBLE_PROTOCOL_VERSION,
    IP_RECENTLY_CONNECTED,
    BAD_PACKET
}