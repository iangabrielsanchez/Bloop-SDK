package com.tdc.bloop.utilities

import com.esotericsoftware.kryo.Kryo
import com.tdc.bloop.models.StringRequest
import com.tdc.bloop.models.StringResponse

/**
 * Contains methods for registering and au
 * Created by ian.sanchez on 5/31/17.
 */
class BloopAuditor {

    private static final Class[] defaultTypes = [
            StringRequest.class,
            StringResponse.class,
    ]

    static void registerClasses(Kryo kryo, Class[] dataTypes){
        for(Class dataType: dataTypes){
            kryo.register( dataTypes )
        }
    }

    static void registerClasses(Kryo kryo){
        registerClasses(kryo, defaultTypes)
    }

}
