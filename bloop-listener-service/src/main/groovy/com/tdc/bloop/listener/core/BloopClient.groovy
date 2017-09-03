package com.tdc.bloop.listener.core

import com.esotericsoftware.kryonet.Client
import com.esotericsoftware.kryonet.JsonSerialization
import com.tdc.bloop.listener.exception.BloopException
import com.tdc.bloop.listener.model.BloopSettings
import groovy.transform.CompileStatic
/**
 * Represents the client that is connected to the BloopServer.
 * The Bloop Listener Service automatically identifies if the current
 * application instance will be the server or the client.
 *
 * @since 1.0
 * @author Ian Gabriel Sanchez
 */
@CompileStatic
class BloopClient extends Client {

    BloopSettings settings
    String host

    /**
     * Initializes a BloopClient instance with the provided BloopSettings.
     * @param settings The BloopSettings that contains all the required parameters for Blooping.
     */
    BloopClient( BloopSettings settings ) {
        //super()
        super( 16384, 2048, new JsonSerialization() )
        this.settings = settings
        initializeComponents()
    }

    private void initializeComponents() {
        try {
            this.start()
            this.addListener( new BloopDefaultListeners() )
        }
        catch( Exception ex ) {
            //TODO: Add exception logger.
        }
    }

    BloopClient withHost(String host){
        this.host = host
        return this
    }

    void connect() throws BloopException {
        if( host == null ) {
            throw new BloopException( "Field host is not initialized" )
        }
        connect( settings.timeout, host, settings.tcpPort )
    }

}