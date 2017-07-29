package com.tdc.bloop.listener.core

import com.tdc.bloop.core.BloopClient
import com.tdc.bloop.core.BloopServer
import com.tdc.bloop.core.BloopSettings
import com.tdc.bloop.utilities.BloopAuditor

class BloopListenerService {

    static BloopServer bloopServer
    static BloopClient bloopClient
    static BloopSettings bloopSettings

    void initialize() {
        //bloopSettings = new BloopSettings()
        print BloopAuditor.getHostInformation().toString()
        //println BloopAuditor.getMachineLANAddress().hostAddress
    }

    static void main( String[] args ) {
        new BloopListenerService().initialize()
    }

}
