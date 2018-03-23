package com.tdc.bloop.api

import com.tdc.bloop.listener.core.BloopClient
import com.tdc.bloop.listener.core.BloopListenerService
import com.tdc.bloop.listener.utilities.BloopLogger
import com.tdc.bloop.model.BloopSettings

class BloopAPIClientBuilder {

    static BloopLogger logger = new BloopLogger( this.class.getSimpleName() )

    static BloopAPIClient defaultClient() {
        logger.log( "Initializing BloopAPIClient" )
        BloopSettings settings = BloopListenerService.loadBloopSettings()
        return new BloopAPIClient( new BloopClient( settings ).withHost( "127.0.0.1" ) )
    }

}
