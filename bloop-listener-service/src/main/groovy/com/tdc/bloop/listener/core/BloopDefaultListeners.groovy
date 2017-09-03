package com.tdc.bloop.listener.core

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.tdc.bloop.listener.model.HelloRequest
import com.tdc.bloop.listener.model.HelloResponse
import com.tdc.bloop.listener.model.HelloThanks
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
class BloopDefaultListeners extends Listener {

    private BloopLogger logger = new BloopLogger( this.class.getSimpleName() )

    @Override
    void received( Connection connection, Object object ) {

        if( object instanceof HelloRequest ) {
            logger.log( "Received HelloRequest" )
            connection.sendTCP( new HelloResponse( ( HelloRequest ) object ) )
            logger.log( "Sent HelloResponse" )
        }
        else if( object instanceof HelloResponse ) {
            logger.log( "Received HelloResponse" )
            connection.sendTCP( new HelloThanks( ( HelloResponse ) object ) )
            logger.log( "Sent HelloThanks" )
        }
        else if( object instanceof HelloThanks ) {
            logger.log( "Received HelloThanks" )
//            if( BloopListenerService.clients.containsKey( ( ( HelloThanks ) object ).hostIP ) ) {
            BloopListenerService.clients.get( ( ( HelloThanks ) object ).hostIP ).key = ( ( HelloThanks ) object ).key
            logger.log( "Added Key" )
            connection.close()
//            }
        }
    }
}

