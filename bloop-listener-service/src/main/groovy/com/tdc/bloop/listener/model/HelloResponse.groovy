package com.tdc.bloop.listener.model

import com.tdc.bloop.listener.core.BloopListenerService
import com.tdc.bloop.listener.core.BloopRequestHandler as handler

/**
 * Created by tjako on 8/13/2017.
 */
class HelloResponse extends Response {
    Map<String, String> clients = BloopListenerService.clienrs

    HelloResponse( HelloRequest request ) {
        if( !handler.autenticate( request.key ) ) {
            basicResponse( false, "Authentication Failed, Key Mismatch" )
        }
        // Check if client is already saved in client list
        else if( clients.contains( request.ip) ) {
            basicResponse( false, "Hello Failed, Client Already Exists" )
        }
        else {
            // Save hello request info (IP and macs)
            clients.put( request.ip, request.macAddress)
            basicResponse( true, "Hello Request Successful", handler.generateKey( request.key ))
        }
    }
}
