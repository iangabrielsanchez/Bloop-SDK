package com.tdc.bloop.listener.model.ipc

import com.tdc.bloop.listener.core.BloopListenerService

/**
 * Created by tjako on 8/26/2017.
 */
class ListClientsResponse {
    String[] HostNames
    String[] Signatures

    ListClientsRequest ( ListClientsResponse request ) {
        switch( request.filter ){
            case "app":
                break;
            case "app-ver":
                break;
            case "listener-ver":
            default: // All
                HostNames = BloopListenerService.clients;
                Signatures
                break;
        }
        this.
    }
}
