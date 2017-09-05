package com.tdc.bloop.listener.core

import com.esotericsoftware.kryonet.JsonSerialization
import com.esotericsoftware.kryonet.Server
import com.tdc.bloop.model.BloopSettings
import groovy.transform.CompileStatic
/**
 * Represents the server that the BloopClients will connect to.
 * The Bloop Listener Service automatically identifies if the current
 * application instance will be the server or the client.
 *
 * @since 1.0
 * @author Ian Gabriel Sanchez
 */
@CompileStatic
class BloopServer extends Server {

    BloopSettings settings
    private boolean initialized = false

    /**
     * Initializes a BloopServer instance with the provided BloopSettings.
     * @param settings The BloopSettings that contains all the required parameters for Blooping.
     */
    BloopServer( BloopSettings settings ) {
        //super()
        super( 16384, 2048, new JsonSerialization() )
//        Log.set( Log.LEVEL_NONE )
        this.settings = settings
        this.initializeComponents()
    }

    private void initializeComponents() {
        try {
            this.start()
            this.bind( settings.tcpPort )
            this.addListener( new BloopDefaultListeners() )
            this.addListener( new BloopIPCListener() )
            initialized = true
            println "Bloop server is up"
        }
        catch( Exception ex ) {
            //TODO: Add exception logger
            initialized = false
        }
    }

    /**
     * Checks if the current BloopServer instance is initialized.
     * @return The initialized status of the BloopServer
     */
    boolean isInitialized() {
        return initialized
    }

}
