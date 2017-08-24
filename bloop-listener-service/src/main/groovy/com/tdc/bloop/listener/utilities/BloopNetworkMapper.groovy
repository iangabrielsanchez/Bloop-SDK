package com.tdc.bloop.listener.utilities

import com.tdc.bloop.listener.core.BloopClient
import com.tdc.bloop.listener.model.HelloRequest
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
        DatagramSocket socket = new DatagramSocket( 25668 )
        logger.log( "DatagramSocket bound to port ${ socket.port }" )

        //I'm not really sure if this is required.
        //TODO: test if disabling this would affect the program
        socket.setBroadcast( true )
        logger.log( "DatagramSocket setBroadcast is set to ${ socket.getBroadcast() }" )

        while( true ) {
            byte[] buffer = new byte[bufferSize]
            DatagramPacket packet = new DatagramPacket( buffer, buffer.length )
            logger.log( 'Waiting for UDP broadcast' )
            socket.receive( packet )
            buffer = packet.getData()

            //String receivedData = new String( buffer, 'UTF-8' )
            //Parse bytes directly to object to save memory
            Object parsedObject = new JsonSlurper().parse( buffer, "UTF-8" )
            logger.log( "Received ${ buffer.size() } bytes of data" )

            if( parsedObject instanceof HelloRequest ) {
                //start new thread for authenticating
                new Thread( new Runnable() {

                    @Override
                    void run() {
                        BloopClient client = new BloopClient()
                    }
                } ).run()
            }
        }
    }
}
