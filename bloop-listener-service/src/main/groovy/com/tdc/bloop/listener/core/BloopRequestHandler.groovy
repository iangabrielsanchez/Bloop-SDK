package com.tdc.bloop.listener.core

import com.tdc.bloop.listener.model.BloopRequest
import com.tdc.bloop.listener.model.ClientListRequest
import com.tdc.bloop.listener.model.ClientListResponse
import com.tdc.bloop.listener.model.HelloRequest
import com.tdc.bloop.listener.model.HelloResponse

/**
 * Created by tjako on 8/19/2017.
 */
class BloopRequestHandler {

    Map<String, String> synchronizeClients( Map<String, String> list, Map<String, String> list2 ) {
        if( !list.contains( client ) ) {
            list.add( client )
        }
        if( !list2.contains( client ) ) {
            list.add( client )
        }
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
            return new HelloResponse( ( HelloRequest ) object )
        }
        else if( object instanceof HelloResponse ) {
            return new ClientListRequest( ( HelloResponse ) object )
        }
        else if( object instanceof ClientListRequest ) {
            return new ClientListResponse( ( ClientListRequest ) object )
        }
        else if( object instanceof ClientListResponse ) {
            if(((ClientListResponse) object).succeeded) {
                BloopListenerService.clients = ( ( ClientListResponse ) object ).clients
            }
        }
        else if( object instanceof BloopRequest ){

        }
    }
}
