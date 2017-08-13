package com.tdc.bloop.listener.core

import com.tdc.bloop.core.BloopSettings
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

class BloopListenerService {

    static BloopServer bloopServer
    static BloopClient bloopClient
    static BloopSettings bloopSettings

    private static String settingsFilePath

    public List<Map<String, String>> clients;


    void initialize() {
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
                File file = new File( "/home/ian/Desktop/settings.json" )
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
        bloopServer = new BloopServer( bloopSettings )
        bloopClient = new BloopClient( bloopSettings )
    }

    static void main( String[] args ) {

        if( args.length > 0 ) {
            settingsFilePath = args[ 0 ]
        }

        new BloopListenerService().initialize()
    }

}