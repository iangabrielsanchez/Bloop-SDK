package com.tdc.bloop.core

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.tdc.bloop.models.StringRequest
import groovy.transform.CompileStatic

/**
 * Created by ianga on 1 Jul 2017.
 */
@CompileStatic
class BloopDefaultListeners extends Listener{

    @Override
    void received( Connection connection, Object object ) {
        if( object instanceof StringRequest ) {
            //do something
        }
    }

}
