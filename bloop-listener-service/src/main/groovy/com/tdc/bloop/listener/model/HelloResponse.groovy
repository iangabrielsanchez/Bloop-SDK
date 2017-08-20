package com.tdc.bloop.listener.model

import com.tdc.bloop.listener.core.BloopListenerService
import com.tdc.bloop.listener.core.BloopRequestHandler

/**
 * Created by tjako on 8/13/2017.
 */
class HelloResponse extends Response {

    HelloResponse( HelloRequest request ) {
        if( !BloopRequestHandler.autenticate( request.key ) ) {
            basicResponse( false, "Authentication Failed, Key Mismatch" )
        }
        // Check if client is already saved in client list
        else if( BloopListenerService.clients.contains( request.ip) ) {
            if(BloopListenerService.clients."${request.ip}" == request.macAddress) {
                basicResponse( false, "Hello Failed, Client Already Exists" )
            }
            else {
                println( "IP Reissued" )
                BloopListenerService.clients.remove( request.ip )
            }
        }
        else {
            // Save hello request info (IP and macs)
            clients.put( request.ip, request.macAddress)
            basicResponse( true, "Hello Request Successful", BloopRequestHandler.generateKey( request.key ))
        }
    }
}
