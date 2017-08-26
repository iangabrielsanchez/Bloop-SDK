package com.tdc.bloop.listener.model

import com.tdc.bloop.listener.core.BloopListenerService
import com.tdc.bloop.listener.core.BloopRequestHandler

/**
 * Created by tjako on 8/13/2017.
 */
class ClientListResponse extends Response {

    Map<String, String> clients = [ : ]

    void response( boolean succeeded, String message, List<Map<String, String>> clients ) {
        this.succeeded = succeeded
        this.message = message
        this.clients = clients
    }

    ClientListResponse( ClientListRequest response ) {
        if( !response.succeeded ) {
            basicResponse( false, "Client List Response Failed, Client List Request Failed" )
        }
        else {
            Map<String, String> clients
            // Synchronize the Client List of the current device and the responder
            clients = BloopRequestHandler.syncronizeClients( response.clients, BloopListenerService.clients )
            // Update the device list
            BloopListenerService.clients = clients
            // Return the updated list to the responder
            this.response( true, "Client List Request Successful", clients )
        }
    }
}
