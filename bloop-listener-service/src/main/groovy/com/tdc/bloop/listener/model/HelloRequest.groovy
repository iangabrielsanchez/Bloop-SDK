package com.tdc.bloop.listener.model

import com.tdc.bloop.models.HostInformation

class HelloRequest {
    String hostIP
    String macAddress
    String key
    Map<String, String> clients
}