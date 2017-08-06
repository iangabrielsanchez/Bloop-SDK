package com.tdc.bloop.models

class HostInformation {
    InetAddress inetAddress
    NetworkInterface networkInterface
    int networkPrefix

    String toString(){
        return "inetAddress:${inetAddress}\n" +
                "networkInterface:${networkInterface}\n" +
                "networkPrefix:${networkPrefix}"
    }
}
