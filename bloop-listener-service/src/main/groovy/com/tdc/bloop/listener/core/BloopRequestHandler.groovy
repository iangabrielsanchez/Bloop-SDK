package com.tdc.bloop.listener.core

import com.tdc.bloop.listener.model.HostInformation
import com.tdc.bloop.listener.model.ipc.BloopRequest
import com.tdc.bloop.listener.model.ipc.BloopResponse
import com.tdc.bloop.listener.model.ClientListRequest
import com.tdc.bloop.listener.model.ClientListResponse
import com.tdc.bloop.listener.model.HelloRequest
import com.tdc.bloop.listener.model.HelloResponse

/**
 * Created by tjako on 8/19/2017.
 */
class BloopRequestHandler {

    Map<String, HostInformation> synchronizeClients(  Map<String, HostInformation> list,  Map<String, HostInformation> list2 ) {

        list
    }

    static boolean autenticate( String key ) {
        // Confirm hello key is from a legit listener
        return true
    }
    // Check if Listener is Legit
    String generateKey( String key ) {
        // generate token.
    }

    static Object handleRequest( Object object) {
        if( object instanceof HelloRequest ) {
            println "RECEIVED: " + ( HelloRequest ) object
            return new HelloResponse( ( HelloRequest ) object )
        }
        else if( object instanceof HelloResponse ) {
            println "RECEIVED: " + ( HelloResponse ) object
            return new ClientListRequest( ( HelloResponse ) object )
        }
        else if( object instanceof ClientListRequest ) {
            println "RECEIVED: " + ( ClientListRequest ) object
            return new ClientListResponse( ( ClientListRequest ) object )
        }
        else if( object instanceof ClientListResponse ) {
            if( ( ( ClientListResponse ) object ).succeeded ) {
                println "RECEIVED: " + ( ClientListRequest ) object
                BloopListenerService.clients = ( ( ClientListResponse ) object ).clients
            }
        }
        else if( object instanceof ListClientsRequest ) {
            return new ListClientsResponse( (ListClientsReqiest) object)
        }
        else if(object instanceof BloopRequest) {
            return new BloopResponse( (BloopRequest) object )
        }


        else if( object instanceof String ) {
            println "RECEIVED: " + object.toString()
        }
    }
}
