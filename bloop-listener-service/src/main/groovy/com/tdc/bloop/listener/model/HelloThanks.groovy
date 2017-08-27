package com.tdc.bloop.listener.model

import com.tdc.bloop.listener.core.BloopListenerService
import com.tdc.bloop.listener.core.HelloStatus
import com.tdc.bloop.listener.utilities.BloopAuditor
import com.tdc.bloop.listener.utilities.BloopLogger
import com.tdc.bloop.listener.utilities.BloopSecurity

class HelloThanks {

    String key
    String hostIP

    HelloThanks( HelloResponse response ) {
        BloopLogger logger = new BloopLogger( this.class.getSimpleName() )
        if( response.status == HelloStatus.AUTHORIZED ) {
            this.key = BloopSecurity.generateRandomKey()
            this.hostIP = BloopAuditor.getHostInformation().inetAddress.getHostAddress()
            BloopListenerService.clients.put( response.client.hostIP, response.client )
            logger.log( "Added ${ response.client.hostIP } to clientsList" )
        }
        else if( response.status == HelloStatus.MAC_MISMATCH ) {
            //do nothing for now
        }
        else if( response.status == HelloStatus.VERSION_MISMATCH ) {
            //do nothing for now
        }
        else if( response.status == HelloStatus.ALREADY_EXISTS ) {
            //do nothing for now
        }
    }

}
