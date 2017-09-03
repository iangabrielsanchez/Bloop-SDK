package com.tdc.bloop.listener.core

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.tdc.bloop.listener.model.BloopApplication
import com.tdc.bloop.listener.model.BloopIPCResponse
import com.tdc.bloop.listener.model.BloopRequest
import com.tdc.bloop.listener.utilities.BloopLogger
import groovy.transform.CompileStatic
/**
 * Contains all the default bloop listeners. This determines
 * what should happen to a request depending on its type.
 *
 * @since 1.0
 * @author Ian Gabriel Sanchez
 */
@CompileStatic
class BloopIPCListener extends Listener {

    private BloopLogger logger = new BloopLogger( this.class.getSimpleName() )

    @Override
    void received( Connection connection, Object object ) {
        if( object instanceof BloopRequest ) {
            if( BloopListenerService.applications.containsKey( ( ( BloopRequest ) object ).applicationName ) ) {
                BloopApplication app = BloopListenerService.applications.get( ( ( BloopRequest ) object ).applicationName )
                if( app.applicationVersion == ( ( BloopRequest ) object ).applicationVersion ) {

                    connection.sendTCP( new BloopIPCResponse(
                            description: "Bloop allowed",
                            status: BloopIPCStatus.ALLOWED
                    ) )
                }
                else {
                    connection.sendTCP( new BloopIPCResponse(
                            description: "Application version doesn't match",
                            status: BloopIPCStatus.VERSION_MISMATCH
                    ) )
                }
            }
            else {
                connection.sendTCP( new BloopIPCResponse(
                        description: "Unknown host. Not a registered bloop host",
                        status: BloopIPCStatus.UNKNOWN_HOST
                ) )
            }
        }
    }
}

