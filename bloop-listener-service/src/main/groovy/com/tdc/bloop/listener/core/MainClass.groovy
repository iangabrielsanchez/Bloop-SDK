//package com.tdc.bloop.listener.core
//
//import com.tdc.bloop.model.BloopApplication
//import groovy.json.JsonSlurper
//import org.apache.commons.lang3.StringEscapeUtils
//
//class MainClass {
//
//    static void main( String[] args ) {
//
//        Map<String, BloopApplication> bloopApplicationMap = new HashMap<>()
//        File appList
//        try {
//            appList = new File( System.getProperty( "user.dir" ), "ApplicationsList.json" )
//            bloopApplicationMap = ( Map<String, BloopApplication> ) new JsonSlurper().parse( appList )
//        }
//        catch( Exception ex ) {
//            //something occured
//        }
//
//        String app = "TextEditor"
//
//        String command = bloopApplicationMap[ app ].command
//        String path = bloopApplicationMap[ app ].applicationDir
//        String params = "{\"caretPostion\":4,\"isEnabled\":true,\"title\":\"Untitled | Code-Text Editor\",\"content\":\"REGULAR \"}"
//        String drive = path.split( ":" )[ 0 ]
//        String name = bloopApplicationMap[ app ].applicationName
//        println "Starting program..."
//        println command
//        println path
//        params = ""StringEscapeUtils.escapeJson( params )
//        println params
//        println drive
//        println name
//        Process process = new ProcessBuilder( "cmd", "/k", drive + ":&&cd ${ path }&&", command, name, params, "&&exit" ).start()
////        new ProcessBuilder( "cmd", "/k", command, path, params ).start()
//    }
//}