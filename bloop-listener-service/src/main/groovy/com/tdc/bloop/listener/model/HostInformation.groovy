package com.tdc.bloop.listener.model

import com.tdc.bloop.listener.model.ipc.ApplicationInformarion

class HostInformation {

    InetAddress inetAddress
    NetworkInterface networkInterface
    String macAddress
    String signature
    int networkPrefix
    List<ApplicationInformarion> applications
}
