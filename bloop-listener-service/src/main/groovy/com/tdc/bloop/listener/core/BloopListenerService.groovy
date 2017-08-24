package com.tdc.bloop.listener.core

import com.tdc.bloop.listener.model.BloopSettings
import com.tdc.bloop.listener.model.HelloRequest
import com.tdc.bloop.listener.utilities.BloopAuditor
import com.tdc.bloop.listener.utilities.BloopLogger
import com.tdc.bloop.listener.utilities.BloopNetworkMapper
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

class BloopListenerService {

    BloopLogger logger = new BloopLogger( this.class.getSimpleName() )

    static BloopServer bloopServer
    static BloopClient bloopClient
    static BloopSettings bloopSettings
    static Map<String, String> clients = [ : ]

    private static String settingsFilePath
    private static Thread discovery

    void initialize() {
        logger.log( 'Initializing BloopListener' )
        //initialize settings
        //if path is not null,try to initialize, if fail or else, create file
        if( settingsFilePath != null ) {
            try {
                Scanner fileReader = new Scanner( new File( settingsFilePath ) )
                StringBuilder json = StringBuilder.newInstance()
                while( fileReader.hasNextLine() ) {
                    json.append( fileReader.nextLine() )
                }
                bloopSettings = ( BloopSettings ) new JsonSlurper().parseText( json.toString() )
            }
            catch( Exception ex ) {
                logger.error( 'Failed to load bloopSettings.', ex.message )
            }
        }
        else {
            logger.warn( 'JSON with settings not found.', 'Generating from default settings.' )
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
                logger.error( 'Failed to create settings.json', ex.message )
            }
        }

        //Initialize server (everyone is a server)
        bloopServer = new BloopServer( bloopSettings )
        logger.log( 'BloopServer initialized' )
        BloopAuditor.registerDefaultClasses( bloopServer.kryo )
        logger.log( 'Registered default classes to kryo engine' )

        logger.log( "Sending I'm here broadcast" )
        try {
            HelloRequest hello = new HelloRequest()
            message = new JsonBuilder( hello ).toString()
            InetAddress broadcastAddress = InetAddress.getByName( "255.255.255.255" )
            new DatagramSocket().send( new DatagramPacket( message.getBytes(), message.length(), broadcastAddress, bloopSettings.udpPort ) )
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

        //Start thread for host discovery;

        if( !isServer ) {
            bloopClient = new BloopClient( bloopSettings )
            println "Client is connecting to bloopServer"
            bloopClient.connect(
                    bloopClient.settings.timeout,
                    bloopClient.settings.host,
                    bloopClient.settings.tcpPort
            )
            BloopAuditor.registerDefaultClasses( bloopClient.kryo )
        }
        else {
            println "Initializing bloop server"
            bloopServer = new BloopServer( bloopSettings )
            BloopAuditor.registerDefaultClasses( bloopServer.kryo )
        }

    }
    static boolean isServer = true

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