package com.tdc.bloop.listener.ipc

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.esotericsoftware.kryonet.Server
import com.tdc.bloop.listener.core.BloopClient
import com.tdc.bloop.listener.core.BloopListenerService
import com.tdc.bloop.listener.model.BloopApplication
import com.tdc.bloop.listener.model.BloopIPCResponse
import com.tdc.bloop.listener.model.BloopRequest
import com.tdc.bloop.listener.model.BloopSettings
import com.tdc.bloop.listener.utilities.BloopAuditor
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
class BloopIPCServer extends Server {

    BloopSettings settings
    private boolean initialized = false

    /**
     * Initializes a BloopServer instance with the provided BloopSettings.
     * @param settings The BloopSettings that contains all the required parameters for Blooping.
     */
    BloopIPCServer( BloopSettings settings ) {
        super()
        this.settings = settings
        this.initializeComponents()
    }

    private void initializeComponents() {
        try {
            this.start()
            this.bind( settings.tcpPort )
            BloopAuditor.registerDefaultClasses( this.kryo )
            this.addListener( new Listener() {

                @Override
                void received( Connection connection, Object object ) {
                    if( object instanceof BloopRequest ) {
                        if( BloopListenerService.applications.containsKey( ( ( BloopRequest ) object ).applicationName ) ) {
                            BloopApplication app = BloopListenerService.applications.get( ( ( BloopRequest ) object ).applicationName )
                            if( app.applicationVersion == ( ( BloopRequest ) object ).applicationVersion ) {
                                new BloopIPCResponse(
                                        description: "Bloop allowed",
                                        status: BloopIPCStatus.ALLOWED
                                )
                            }
                            else {
                                new BloopIPCResponse(
                                        description: "Application version doesn't match",
                                        status: BloopIPCStatus.VERSION_MISMATCH
                                )
                            }
                        }
                        else {
                            new BloopIPCResponse(
                                    description: "Unknown host. Not a registered bloop host",
                                    status: BloopIPCStatus.UNKNOWN_HOST
                            )
                        }
                    }
                }
            } )
            initialized = true
        }
        catch( Exception ex ) {
            //TODO: Add exception logger
            initialized = false
        }
    }
}