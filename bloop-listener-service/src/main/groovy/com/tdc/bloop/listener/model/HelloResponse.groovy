package com.tdc.bloop.listener.model

import com.tdc.bloop.listener.core.BloopListenerService
import com.tdc.bloop.listener.core.HelloStatus
import com.tdc.bloop.listener.utilities.BloopAuditor
import org.apache.commons.text.RandomStringGenerator

import javax.crypto.KeyGenerator
/**
 * Created by tjako on 8/13/2017.
 */
class HelloResponse extends Response {

//    private BloopLogger logger = new BloopLogger( this.class.getSimpleName() )

    boolean authorized
    Integer bloopPort
    String key
    HelloStatus status

//    Map<String, HostInformation> clientList

    HelloResponse() {}

    HelloResponse( HelloRequest request ) {
        authorized = false
        bloopPort = null
        key = null
        if( BloopAuditor.compareVersion( "<=", BloopListenerService.bloopSettings.listenerVersion, request.version ) ) {
            if( BloopListenerService.clients[ request.hostIP ].macAddres == request.macAddress ) {
                authorized = true
                bloopPort = BloopListenerService.bloopSettings.tcpPort
                clientList = BloopListenerService.clients
                RandomStringGenerator generator = RandomStringGenerator.Builder.newInstance().withinRange( 33, 126 ).build()
                key = generator.generate( 16 )
                status = HelloStatus.AUTHORIZED
            }
            else {
                status = HelloStatus.MAC_MISMATCH
            }
        }
        else {
            status = HelloStatus.VERSION_MISMATCH
        }
    }
}
