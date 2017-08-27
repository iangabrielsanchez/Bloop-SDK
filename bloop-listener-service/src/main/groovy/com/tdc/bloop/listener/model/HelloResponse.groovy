package com.tdc.bloop.listener.model

import com.tdc.bloop.listener.core.BloopListenerService
import com.tdc.bloop.listener.core.HelloStatus
import com.tdc.bloop.listener.utilities.BloopAuditor
import com.tdc.bloop.listener.utilities.BloopSecurity
/**
 * Created by tjako on 8/13/2017.
 */
class HelloResponse extends Response {

//    private BloopLogger logger = new BloopLogger( this.class.getSimpleName() )

    HelloStatus status
    Client client

    HelloResponse() {}

    HelloResponse( HelloRequest request ) {
        if( BloopAuditor.compareVersion( "<=", BloopListenerService.bloopSettings.listenerVersion, request.version ) ) {
            if( !BloopListenerService.clients.containsKey( request.hostIP ) ) {
                BloopListenerService.clients.put( request.hostIP,
                        new Client(
                                hostIP: request.hostIP,
                                macAddress: request.macAddress,
                                bloopPort: request.bloopPort,
                                version: request.version,
                                key: null
                        )
                )
                status = HelloStatus.AUTHORIZED
                HostInformation hostInformation = BloopAuditor.getHostInformation()
                client = new Client(
                        hostIP: hostInformation.getInetAddress().getHostAddress(),
                        macAddress: hostInformation.getMacAddress(),
                        bloopPort: BloopListenerService.bloopSettings.tcpPort,
                        version: BloopListenerService.bloopSettings.listenerVersion,
                        key: BloopSecurity.generateRandomKey()
                )
            }
            else if( BloopListenerService.clients[ request.hostIP ].macAddres == request.macAddress ) {
                status = HelloStatus.ALREADY_EXISTS
            }
            else {
                status = HelloStatus.MAC_MISMATCH
                BloopListenerService.clients.remove( request.hostIP )
            }
        }
        else {
            status = HelloStatus.VERSION_MISMATCH
        }
    }
}
