package com.tdc.bloop.listener.core

import com.tdc.bloop.listener.utilities.BloopAuditor
import com.tdc.bloop.listener.utilities.BloopLogger
import com.tdc.bloop.listener.utilities.BloopNetworkMapper
import com.tdc.bloop.model.BloopApplication
import com.tdc.bloop.model.BloopSettings
import com.tdc.bloop.model.Client
import com.tdc.bloop.model.HelloRequest
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

class BloopListenerService {

    BloopLogger logger = new BloopLogger( this.class.getSimpleName() )

    static BloopServer bloopServer
    static BloopSettings bloopSettings
    static Map<String, Client> clients = [ : ]
    static Map<String, BloopApplication> applications

    private static Thread discovery
    private static File settingFile

    void initialize() {
        logger.log( 'Initializing BloopListener' )
        //initialize settings
        //if path is not null,try to initialize, if fail or else, create file
        bloopSettings = loadBloopSettings()
        try {
            applications = ( Map<String, BloopApplication> ) new JsonSlurper().parse(
                    new File( System.getProperty( "user.dir" ), "ApplicationsList.json" )
            )
        }
        catch( Exception ex ) {
            logger.error( "Unexpected error occurred", ex.message )
        }

        //Initialize server
        bloopServer = new BloopServer( bloopSettings )
        logger.log( 'BloopServer initialized' )

        logger.log( "Broadcasting HelloRequest" )
        try {
            HelloRequest hello = BloopAuditor.generateHelloRequest()
            String message = new JsonBuilder( hello ).toString()
            InetAddress broadcastAddress = InetAddress.getByName( "255.255.255.255" )
            new DatagramSocket().send( new DatagramPacket( message.getBytes(), message.length(), broadcastAddress, bloopSettings.udpPort ) )
            logger.log( "Broadcast Successful" )
        }
        catch( Exception ex ) {
            logger.error( "UDP Broadcast Failed", ex.message )
        }

        //Start host discovery thread
        logger.log( 'Initializing NetworkMapper' )
        discovery = new Thread(
                new BloopNetworkMapper( bloopServer.settings.udpPort, bloopServer.settings.bufferSize ),
                "NetworkMapper"
        )
        logger.log( 'Starting NetworkMapper' )
        discovery.run()

        bloopServer.addListener( new BloopIPCListener() )
    }

    static BloopSettings loadBloopSettings() {
        BloopLogger logger = new BloopLogger( this.class.getSimpleName() )
        try {
            settingFile = new File( System.getProperty( "user.dir" ), "BloopSettings.json" )
            return ( BloopSettings ) new JsonSlurper().parse( settingFile )
        }
        catch( FileNotFoundException ex ) {
            logger.error( "BloopSettings.json not found.", "Generating from default settings." )
            try {

                if( settingFile.createNewFile() ) {
                    FileWriter writer = new FileWriter( settingFile, true )
                    BufferedWriter bufferedWriter = new BufferedWriter( writer )
                    bufferedWriter.write( new JsonBuilder( new BloopSettings() ).toPrettyString() )
                    bufferedWriter.flush()
                    bufferedWriter.close()
                    Scanner fileReader = new Scanner( settingFile )
                    StringBuilder json = StringBuilder.newInstance()
                    while( fileReader.hasNextLine() ) {
                        json.append( fileReader.nextLine() )
                    }
                    fileReader.close()
                    return ( BloopSettings ) new JsonSlurper().parseText( json.toString() )
                }
            }
            catch( Exception ex2 ) {
                logger.error( 'Failed to create BloopSettings.json', ex2.message )
            }
        }
        catch( Exception ex ) {
            logger.error( "Unexpected error occurred.", ex.message )
        }
    }

    static void main( String[] args ) {
        new BloopListenerService().initialize()
    }
}