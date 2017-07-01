package com.tdc.bloop.utilities

import com.esotericsoftware.kryo.Kryo
import com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile
import com.tdc.bloop.models.StringRequest
import com.tdc.bloop.models.StringResponse
import groovy.transform.CompileStatic

/**
 * Contains methods for registering and au
 * Created by ian.sanchez on 5/31/17.
 */
@CompileStatic
class BloopAuditor {

    private static final Class[] defaultTypes = [
            StringRequest.class,
            StringResponse.class,
    ]

    static void registerClasses(Kryo kryo, Class[] dataTypes){
        for(Class dataType: dataTypes){
            kryo.register( dataType )
        }
    }

    static void registerClasses(Kryo kryo){
        registerClasses(kryo, defaultTypes)
    }

}
