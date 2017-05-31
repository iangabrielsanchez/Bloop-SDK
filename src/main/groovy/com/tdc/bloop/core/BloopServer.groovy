package com.tdc.bloop.core

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.esotericsoftware.kryonet.Server
import com.tdc.bloop.models.StringRequest
import com.tdc.bloop.models.StringResponse
import com.tdc.bloop.utilities.BloopAuditor
import groovy.util.logging.Log

/**
 * Created by ian.sanchez on 5/31/17.
 */
final class BloopServer {

    private Server server

    BloopServer() {
        this.initializeComponents()
        new BloopSettings().timeout

    }

    private void initializeComponents() {
        server = new Server()
        server.start()
        server.bind( 25667 )

        this.registerClasses()

        this.addListener( new Listener() {
            void received( Connection connection, Object object ) {
                if ( object instanceof StringRequest ) {
                    StringRequest request = ( StringRequest ) object
                    System.out.println( request.text )

                    StringResponse response = new StringResponse()
                    response.text = "Thanks"
                    connection.sendTCP( response )
                }
            }
        } )

    }

    void addListener( Listener listener ) {
        server.addListener( listener )
    }

    void registerClasses() {
        BloopAuditor.registerClasses()
    }

    void registerClasses( Class[] dataTypes ) {
        BloopAuditor.registerClasses( server.getKryo(), dataTypes )
    }

    static void main( String[] args ) {
        BloopServer bloopServer = new BloopServer();
    }


}
