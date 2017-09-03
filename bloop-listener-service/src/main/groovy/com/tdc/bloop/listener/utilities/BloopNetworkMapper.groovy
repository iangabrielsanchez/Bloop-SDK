package com.tdc.bloop.listener.utilities

import com.tdc.bloop.listener.core.BloopClient
import com.tdc.bloop.listener.core.BloopListenerService
import com.tdc.bloop.listener.model.BloopSettings
import com.tdc.bloop.listener.model.HelloRequest
import com.tdc.bloop.listener.model.HelloResponse
import groovy.json.JsonException
import groovy.json.JsonSlurper

class BloopNetworkMapper implements Runnable {

    BloopLogger logger = new BloopLogger( this.class.getSimpleName() )

    private int port
    private int bufferSize

    BloopNetworkMapper( int port, int bufferSize ) {
        this.port = port
        this.bufferSize = bufferSize
    }

    //This is the method that listens for incoming UDP broadcasts on port 25668
    void run() {

        //Open port 25668 to listen for broadcasts. Port number might fail.
        //TODO: Talk about this with the group
        DatagramSocket socket = new DatagramSocket( port )
        logger.log( "DatagramSocket bound to port ${ socket.port }" )

        //I'm not really sure if this is required.
        //TODO: test if disabling this would affect the program
//        socket.setBroadcast( true )
//        logger.log( "DatagramSocket setBroadcast is set to ${ socket.getBroadcast() }" )

        while( true ) {
            try {
                BloopSettings settings = BloopListenerService.bloopSettings
                byte[] buffer = new byte[bufferSize]
                DatagramPacket packet = new DatagramPacket( buffer, bufferSize )
                logger.log( 'Waiting for UDP broadcast' )
                socket.receive( packet )
                buffer = packet.getData()
                //TODO: FIX THIS
                logger.log( "Received ${ packet.getData().size() } bytes of data" )
                logger.log( new String( buffer, 'UTF-8' ) )
                //Parse bytes directly to object to save memory
                try {
                    Object parsedObject = new JsonSlurper().parse( buffer, "UTF-8" )

//                    if( parsedObject instanceof HelloRequest ) {
                    if( ( HelloRequest ) parsedObject ) {
                        logger.log( "Received HelloRequest" )
                        //start new thread for authenticating
                        new Thread( new Runnable() {

                            @Override
                            void run() {
                                HelloRequest received = ( HelloRequest ) parsedObject
                                BloopClient client = new BloopClient( settings )
                                HelloResponse response = new HelloResponse( received )
                                logger.log( "Connecting to ${ received.hostIP }:${ received.bloopPort }..." )
                                client.connect( settings.timeout, received.hostIP, received.bloopPort )
                                logger.log( "Connected to ${ received.hostIP }:${ received.bloopPort }" )
                                logger.log( "Sending HelloResponse" )
                                client.sendTCP( response )
                            }
                        } ).run()
                    }
                }
                catch( JsonException ex ) {
                    logger.warn( 'Received object is not a valid JSON', ex.message )
                }
            }
            catch( Exception ex ) {
                logger.error( 'Unexpected error occured.', ex.message )
                ex.printStackTrace()
            }
        }
    }
}
