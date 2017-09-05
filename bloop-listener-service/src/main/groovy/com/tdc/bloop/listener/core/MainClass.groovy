package com.tdc.bloop.listener.core

import com.tdc.bloop.model.BloopApplication
import groovy.json.JsonSlurper

class MainClass {

    static void main( String[] args ) {
        Map<String, BloopApplication> bloopApplicationMap = new HashMap<>()
        File appList
        try {
            appList = new File( System.getProperty( "user.dir" ), "ApplicationsList.json" )
            bloopApplicationMap = ( Map<String, BloopApplication> ) new JsonSlurper().parse( appList )
        }
        catch( Exception ex ) {
            //something occured
        }


        String command = bloopApplicationMap[ "Lucky9" ].command
        String path = bloopApplicationMap[ "Lucky9" ].applicationPath
        String params = ""
        println "Starting program..."
        new ProcessBuilder( "cmd", "/k", command, path, params ).start()
    }
}