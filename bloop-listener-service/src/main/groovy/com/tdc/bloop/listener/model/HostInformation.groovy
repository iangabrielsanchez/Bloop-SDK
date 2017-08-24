package com.tdc.bloop.listener.model

class HostInformation {

    InetAddress inetAddress
    NetworkInterface networkInterface
    String macAddress
    int networkPrefix

    String toString() {
        return "inetAddress:${ inetAddress }\n" +
                "networkInterface:${ networkInterface }\n" +
                "networkPrefix:${ networkPrefix }"
    }
}
