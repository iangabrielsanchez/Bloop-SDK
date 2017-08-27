package com.tdc.bloop.listener.model

import com.tdc.bloop.listener.core.BloopListenerService

/**
 * Created by tjako on 8/13/2017.
 */
class ClientListRequest extends Response{
    static Map<String, HostInformation> clients = [:]

    void response( boolean succeeded, String message ) {
        this.succeeded = succeeded
        this.message = message
        this.clients = BloopListenerService.clients
    }

    ClientListRequest( HelloResponse response ) {
        if( !response.succeeded ) {
            basicResponse( false, "Client List Request Failed, Hello Request Failed" )
        }
        else {
            this.response( true, "Client List Request Successful" )
        }
    }
}
