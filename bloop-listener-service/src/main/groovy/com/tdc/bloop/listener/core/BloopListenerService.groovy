package com.tdc.bloop.listener.core

import com.tdc.bloop.listener.model.BloopSettings
import com.tdc.bloop.listener.utilities.BloopAuditor
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

class BloopListenerService {

    static BloopServer bloopServer
    static BloopClient bloopClient
    static BloopSettings bloopSettings
    static Map<String, String> clients = [ : ]

    private static String settingsFilePath
//    private static Thread discovery

    void initialize() {
        //initialize settings
        //if path is not null,try to initialize, if fail or else, create file
        if( settingsFilePath != null ) {

            try {
                Scanner fileReader = new Scanner( new File( settingsFilePath ) )
                StringBuilder json = StringBuilder.newInstance()
                while( fileReader.hasNextLine() ) {
                    json.append( fileReader.nextLine() )
                }
                bloopSettings = new JsonSlurper().parseText( json.toString() )
            }
            catch( Exception ex ) {
                println ex.toString()
            }
        }
        else {
            //TODO: create json file if it does not exist.
            try {
                File file = new File( System.getProperty( "user.home" ), "Desktop/settings.json" )
                if( file.createNewFile() ) {
                    FileWriter writer = new FileWriter( file, true )
                    BufferedWriter bufferedWriter = new BufferedWriter( writer )
                    bufferedWriter.write( new JsonBuilder( new BloopSettings() ).toPrettyString() )
                    bufferedWriter.flush()
                    bufferedWriter.close()
                }
            }
            catch( Exception ex ) {
                //do nothing
            }
        }

        //Start thread for host discovery;


        if( !isServer ) {
            bloopClient = new BloopClient( bloopSettings )
            println "Client is connecting to bloopServer"
            bloopClient.connect(
                    bloopClient.settings.timeout,
                    bloopClient.settings.host,
                    bloopClient.settings.hostPort
            )
            BloopAuditor.registerDefaultClasses( bloopClient.kryo )
        }
        else{
            println "Initializing bloop server"
            bloopServer = new BloopServer( bloopSettings )
            BloopAuditor.registerDefaultClasses( bloopServer.kryo )
        }
//        discovery = new Thread( new BloopNetworkMapper(), "NetworkMapper" )
//        discovery.run()
    }
    static boolean isServer = false

    static void main( String[] args ) {
        if( args.length > 0 ) {
            //settingsFilePath = args[ 0 ]
            settingsFilePath = "C:\\Users\\ianga\\Desktop\\settings.json"
            isServer = Boolean.parseBoolean( args[ 0 ] )
        }
        settingsFilePath = "C:\\Users\\ianga\\Desktop\\settings.json"
        //isServer = Boolean.parseBoolean( args[ 0 ] )?true
        new BloopListenerService().initialize()
    }

}