package com.tdc.bloop.listener.core

/**
 * Created by tjako on 8/19/2017.
 */
class BloopRequestHandler {
    Map<String, String> synchronizeClients(Map<String, String> list, Map<String, String> list2) {
        if( !list.contains( client ) ) {
            list.add( client )
        }
        if( !list2.contains( client ) ) {
            list.add( client )
        }
        list
    }

    static boolean autenticate (String key) {
        // Confirm hello key is from a legit listener
        return true;
    }
    // Check if Listener is Legit
    String generateKey( String key ) {
        // generate token.
    }

}
