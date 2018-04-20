package com.tdc.bloop.listener.core.rewrite

import com.tdc.bloop.listener.core.rewrite.util.BloopProperties
import org.apache.log4j.LogManager
import org.apache.log4j.Logger

class ListenerService {

    private static final Logger logger = LogManager.getLogger( getClass().getSimpleName() )
    private static BloopProperties properties

    ListenerService(){
        properties = new BloopProperties()
    }

    static void main( String[] args ) {
        new ListenerService()
    }

}
