package com.tdc.bloop.core

import com.esotericsoftware.kryonet.Client
import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.tdc.bloop.models.StringRequest
import com.tdc.bloop.models.StringResponse
import com.tdc.bloop.utilities.BloopAuditor
import groovy.transform.CompileStatic

/**
 * Created by ian.sanchez on 5/31/17.
 */
@CompileStatic
class BloopClient extends Client {

    BloopSettings settings

    BloopClient( BloopSettings settings ) {
        super()
        this.settings = settings
        initializeComponents()
    }

    private void initializeComponents() {

        this.start()
        this.connect( settings.timeout, settings.host, settings.hostPort )
        BloopAuditor.registerClasses( this.kryo )

        this.addListener( new BloopDefaultListeners() )

    }

    void registerClasses() {
        BloopAuditor.registerClasses( this.kryo )
    }

    void registerClasses( Class[] dataTypes ) {
        BloopAuditor.registerClasses( this.kryo, dataTypes )
    }

}
