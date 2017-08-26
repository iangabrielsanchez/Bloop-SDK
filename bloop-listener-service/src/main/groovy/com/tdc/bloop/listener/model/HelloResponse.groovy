package com.tdc.bloop.listener.model

import com.tdc.bloop.listener.core.BloopListenerService
import com.tdc.bloop.listener.core.BloopRequestHandler
import com.tdc.bloop.listener.utilities.BloopAuditor
import com.tdc.bloop.listener.utilities.BloopLogger

/**
 * Created by tjako on 8/13/2017.
 */
class HelloResponse extends Response {

//    private BloopLogger logger = new BloopLogger( this.class.getSimpleName() )

    boolean authorized
    Integer bloopPort

    HelloResponse(){}

    HelloResponse( HelloRequest request ) {
//        if( !BloopRequestHandler.autenticate( request.key ) ) {
//            basicResponse( false, "Authentication Failed, Key Mismatch" )
//        }
//        // Check if client is already saved in client list
//        else if( BloopListenerService.clients.contains( request.ip ) ) {
//            if( BloopListenerService.clients."${ request.ip }" == request.macAddress ) {
//                basicResponse( false, "Hello Failed, Client Already Exists" )
//            }
//            else {
//                println( "IP Reissued" )
//                BloopListenerService.clients.remove( request.ip )
//            }
//        }
//        else {
//            // Save hello request info (IP and macs)
//            clients.put( request.ip, request.macAddress )
//            basicResponse( true, "Hello Request Successful", BloopRequestHandler.generateKey( request.key ) )
//        }

        if( BloopAuditor.compareVersion( "<=", BloopListenerService.bloopSettings.applicationVersion, request.version ) ) {
            authorized = true
            bloopPort = BloopListenerService.bloopSettings.tcpPort
        }
        else {
            authorized = false
            bloopPort = null
        }
    }
}
