package com.tdc.bloop

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.tdc.bloop.listener.core.BloopClient
import com.tdc.bloop.model.BloopHostRequest
import com.tdc.bloop.model.BloopHosts
import com.tdc.bloop.model.BloopRequest
import com.tdc.bloop.model.BloopResult

import java.time.Duration
import java.time.Instant
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class BloopAPIClient implements BloopAPI {

    private BloopClient bloopClient

    protected BloopAPIClient( BloopClient bloopClient ) {
        this.bloopClient = bloopClient
    }

    Future<BloopResult> bloop( BloopRequest request ) {
        return executeBloop( request )
    }

    final private Future<BloopResult> executeBloop( BloopRequest request ) {
        bloopClient.connect()
        BloopResult result = null
        bloopClient.addListener( new Listener() {

            @Override
            void received( Connection connection, Object object ) {
                if( object instanceof BloopResult ) {
                    result = ( BloopResult ) object
                }
            }
        } )
        bloopClient.sendTCP( request )

        ExecutorService executor = Executors.newSingleThreadExecutor()
        return executor.submit( new GroovyCallable<BloopResult>() {

            @Override
            BloopResult call() throws Exception {
                Instant start = Instant.now()
                while( result == null ) {
                    Instant check = Instant.now
                    if( Duration.between( start, check ).getNano() >= request.timeout * 1000000 ) {
                        bloopClient.close()
                        result = new BloopResult(
                                requestStatus: BloopRequestStatus.CONNECTION_TIMEOUT
                        )
                        break
                    }
                }
                return result
            }
        } )
    }

    BloopHosts listBloopHosts( BloopHostRequest request ) {
        return executeListBloopHost( request )
    }

    final private BloopHosts executeListBloopHost( BloopHostRequest request ) {
        //do something
    }

}
