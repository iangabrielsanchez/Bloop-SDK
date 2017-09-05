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

        String app = "Lucky9"

        String command = bloopApplicationMap[ app ].command
        String path = bloopApplicationMap[ app ].applicationDir
        String params = ""
        String drive = path.split( ":" )[ 0 ]
        String name = bloopApplicationMap[ app ].applicationName
        println "Starting program..."

        new ProcessBuilder( "cmd", "/k", drive + ":&&cd ${ path }&&", command, name, params ).start()
        new ProcessBuilder( "cmd", "/k", command, path, params ).start()

    }
}