package com.tdc.bloop.core

import com.esotericsoftware.kryonet.Server
import com.tdc.bloop.utilities.BloopAuditor
import groovy.transform.CompileStatic

/**
 * Created by ian.sanchez on 5/31/17.
 */
@CompileStatic
class BloopServer extends Server {

    BloopSettings settings

    BloopServer( BloopSettings settings ) {
        super()
        this.settings = settings
        this.initializeComponents()
    }

    private void initializeComponents() {

        this.start()
        this.bind( settings.getHostPort() )
        this.registerClasses()

        this.addListener( new BloopDefaultListeners() )

    }

    void registerClasses() {
        BloopAuditor.registerClasses( this.kryo )
    }

    void registerClasses( Class[] dataTypes ) {
        BloopAuditor.registerClasses( this.kryo, dataTypes )
    }

}
