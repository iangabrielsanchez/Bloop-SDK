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
            // Client
            List<Map<String, String>> clients

            // Compare Current Device Clients with Sender Client List
            response.clients.each { -> client
                if( !BloopListenerService.clients.contains( client ) ) {
                    // Add clients in sender that doesnt exist in current device
                    BloopListenerService.clients.add( client )
                }
            }

            // Compare Sender Client List with Current Device List
            BloopListenerService.clients.each { -> client
                if( !response.clients.contains( client ) ) {
                    // Send clients in current device that doesnt exist to sender
                    clients.add( client )
                }
            }

            this.response( true, "Client List Request Successful", clients )
        }
    }
}
