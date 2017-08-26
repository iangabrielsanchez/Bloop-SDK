package com.tdc.bloop.listener.core

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.tdc.bloop.listener.model.ClientListRequest
import com.tdc.bloop.listener.model.ClientListResponse
import com.tdc.bloop.listener.model.HelloRequest
import com.tdc.bloop.listener.model.HelloResponse
import groovy.transform.CompileStatic
/**
 * Contains all the default bloop listeners. This determines
 * what should happen to a request depending on its type.
 *
 * @since 1.0
 * @author Ian Gabriel Sanchez
 */
@CompileStatic
class BloopDefaultListeners extends Listener {

    @Override
    void received( Connection connection, Object object ) {
      connection.sendTCP( BloopRequestHandler.handleRequest(object) );
    }
}

