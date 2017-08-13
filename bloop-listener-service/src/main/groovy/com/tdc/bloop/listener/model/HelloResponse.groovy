package com.tdc.bloop.listener.model

import com.tdc.bloop.listener.core.BloopListenerService

/**
 * Created by tjako on 8/13/2017.
 */
class HelloResponse extends Response {
    List<Map<String, String>> clients = BloopListenerService.clienrs

    HelloResponse( HelloRequest request ) {
        if( !autenticate( request.key ) ) {
            basicResponse( false, "Authentication Failed, Key Mismatch" )
        }
        // Check if client is already saved in client list
        else if( clients.contains( [ ip: request.ip, macAddress: request.macAddress ] ) ) {
            basicResponse( false, "Hello Failed, Client Already Exists" )
        }
        else {
            // Save hello request info (IP and macs)
            clients.add( [ ip: request.ip, macAddress: request.macAddress ] )
            basicResponse( true, "Hello Request Successful" )
        }
    }

    // Check if Listener is Legit
    private boolean autenticate( String key ) {
        return true
    }

}
