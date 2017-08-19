package com.tdc.bloop.listener.model

import com.tdc.bloop.listener.core.BloopListenerService

/**
 * Created by tjako on 8/13/2017.
 */
class ClientListResponse extends Response {

    List<Map<String, String>> clients = null;

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
            List<Map<String, String>> clients
            // Synchronize the Client List of the current device and the responder
            clients = synchronizeClients(response.clients,  BloopListenerService.clients)
            // Update the device list
            BloopListenerService.clients = clients
            // Return the updated list to the responder
            this.response( true, "Client List Request Successful", clients )
        }
    }
}
