package com.tdc.bloop.listener.core

import com.tdc.bloop.listener.utilities.BloopSecurity
import org.apache.commons.text.RandomStringGenerator

class MainClass {

    static void main( String[] args ) {

        RandomStringGenerator generator = RandomStringGenerator.Builder.newInstance().withinRange( 33, 126 ).build()
        String key = generator.generate( 16 )
        String encr = BloopSecurity.encrypt( "{\"hostIP\":\"192.168.2.125\",\"bloopPort\":25667,\"version\":\"1.0.0\",\"macAddress\":\"4C-0 F-6E-43-F1-74\"}", key )
        println encr

        println "\n\n"

        println BloopSecurity.decrypt( encr, key )

    }
}