package com.tdc.bloop.listener.core

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.tdc.bloop.listener.model.*
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

        println connection.getRemoteAddressTCP().getHostName()

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
            if( BloopListenerService.clients.containsKey( ( ( HelloThanks ) object ).hostIP ) ) {
                BloopListenerService.clients.get( ( ( HelloThanks ) object ).hostIP ).key = ( ( HelloThanks ) object ).key
                logger.log( "Added Key" )
            }
        }
        else if( object instanceof ClientListRequest ) {
            println "RECEIVED: " + ( ClientListRequest ) object
            connection.sendTCP( new ClientListResponse( ( ClientListRequest ) object ) )
        }
        else if( object instanceof ClientListResponse ) {
            if( ( ( ClientListResponse ) object ).succeeded ) {
//                println "RECEIVED: " + ( ClientListRequest ) object
//                BloopListenerService.clients = ( ( ClientListResponse ) object ).clients
            }
        }
        else if( object instanceof String ) {
            println "RECEIVED: " + object.toString()
        }
    }
}

