package com.tdc.bloop.listener.core

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.tdc.bloop.listener.model.*
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

    @Override
    void received( Connection connection, Object object ) {

        println connection.getRemoteAddressTCP().getHostName()

        if( object instanceof HelloRequest ) {
            println "RECEIVED: " + ( HelloRequest ) object
            connection.sendTCP( new HelloResponse( ( HelloRequest ) object ) )
        }
        else if( object instanceof HelloResponse ) {
            println "RECEIVED: " + ( HelloResponse ) object
//            connection.sendTCP( new ClientListRequest( ( HelloResponse ) object ) )
//            connection.sendTCP( new HelloThanks( ( HelloResponse ) object ) )
        }
        else if( object instanceof HelloThanks ) {
//            connection.sendTCP( new Hello)
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

