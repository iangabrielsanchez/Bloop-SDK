package com.tdc.bloop.core

import com.esotericsoftware.kryonet.Client
import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.tdc.bloop.models.StringRequest
import com.tdc.bloop.models.StringResponse
import com.tdc.bloop.utilities.BloopAuditor

/**
 * Created by ian.sanchez on 5/31/17.
 */
class BloopClient {

    private Client client
    private int port
    private BloopSettings settings

    //todo: implement add settings

    static void main( String[] args ) {
        BloopClient bloopClient = new BloopClient();
    }


    BloopClient(BloopSettings settings) {
        this.settings = settings
        initializeComponents()

    }

    private void initializeComponents() {
        client = new Client()
        client.start()
        client.connect()

        BloopAuditor.registerClasses( client.getKryo() )

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
        client.addListener( listener )
    }


    Client client = new Client();
    client.start();
    client.connect(5000, "192.168.0.4", 54555, 54777);

    SomeRequest request = new SomeRequest();
    request.text = "Here is the request";
    client.sendTCP(request);



}
