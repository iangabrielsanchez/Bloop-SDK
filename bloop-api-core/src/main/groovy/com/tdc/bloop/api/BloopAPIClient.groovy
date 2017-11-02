package com.tdc.bloop.api

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.tdc.bloop.listener.core.BloopClient
import com.tdc.bloop.model.BloopHostRequest
import com.tdc.bloop.model.BloopHosts
import com.tdc.bloop.model.BloopRequest
import com.tdc.bloop.model.BloopResponse

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

    Future<BloopResponse> bloop( BloopRequest request ) {
        return executeBloop( request )
    }

    final private Future<BloopResponse> executeBloop( BloopRequest request ) {
        bloopClient.connect()
        BloopResponse result = null
        bloopClient.addListener( new Listener() {

            @Override
            void received( Connection connection, Object object ) {
                if( object instanceof BloopResponse ) {
                    result = ( BloopResponse ) object
                }
            }
        } )
        bloopClient.sendTCP( request )

        ExecutorService executor = Executors.newSingleThreadExecutor()

        return executor.submit( new GroovyCallable<BloopResponse>() {

            @Override
            BloopResponse call() throws Exception {
                Instant start = Instant.now()
                while( result == null ) {
                    Instant check = Instant.now
                    if( Duration.between( start, check ).getNano() >= request.timeout * 1000000 ) {
                        bloopClient.close()
                        result = new BloopResponse(
                                description: "Connection Timed out"
                        )
                        break
                    }
                }
                return result
            }
        } )
    }

    Future<BloopHosts> listBloopHosts( BloopHostRequest request ) {
        return executeListBloopHost( request )
    }

    final private Future<BloopHosts> executeListBloopHost( BloopHostRequest request ) {
        bloopClient.connect()
        BloopHosts hosts = null
        bloopClient.addListener( new Listener() {

            @Override
            void received( Connection connection, Object object ) {
                if( object instanceof BloopHosts ) {
                    hosts = ( BloopHosts ) object
                }
            }
        } )
        bloopClient.sendTCP( request )

        ExecutorService executor = Executors.newSingleThreadExecutor()
        return executor.submit( new GroovyCallable<BloopHosts>() {

            @Override
            BloopHosts call() throws Exception {
                Instant start = Instant.now()
                while( result == null ) {
                    Instant check = Instant.now
                    if( Duration.between( start, check ).getNano() >= request.timeout * 1000000 ) {
                        bloopClient.close()
                        result = new BloopHosts(
                                requestStatus: RequestStatus.CONNECTION_TIMEOUT
                        )
                        break
                    }
                }
                return result
            }
        } )
    }
}
