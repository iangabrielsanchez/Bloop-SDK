package com.tdc.bloop.listener.core.rewrite.util

import org.apache.log4j.LogManager
import org.apache.log4j.Logger


//https://crunchify.com/java-properties-file-how-to-read-config-properties-values-in-java/
class BloopProperties {

    private static final String PROP_FILE_NAME = 'config.properties'
    private final Logger logger = LogManager.getLogger( getClass().getSimpleName() )
    private final Properties properties;
    private final File propFile
    private boolean autoSave = false

    private int timeout
    private int ipcPort
    private int tcpPort
    private int udpPort
    private int bufferSize
    private String listenerVersion

    BloopProperties() {
        properties = new Properties()
        propFile = new File( getClass().getClassLoader().getResource( PROP_FILE_NAME ).getFile() )
        loadProperties()
    }

    void loadProperties() throws IOException {
        logger.info( "reload() > Loading ${ PROP_FILE_NAME }" )
        try {

            FileInputStream inputStream = new FileInputStream( propFile )
            properties.load( inputStream )
            inputStream.close()
            assignProperties()
        }
        catch( IOException ex ) {
            logger.error( "reload() > ${ PROP_FILE_NAME } not found" )
            throw ex
        }
    }

    private void assignProperties() {
        timeout = Integer.parseInt( properties.getProperty( "timeout", "4000" ) )
        ipcPort = Integer.parseInt( properties.getProperty( "ipcPort", "25666" ) )
        tcpPort = Integer.parseInt( properties.getProperty( "tcpPort", "25667" ) )
        udpPort = Integer.parseInt( properties.getProperty( "udpPort", "25668" ) )
        bufferSize = Integer.parseInt( properties.getProperty( "bufferSize", "1024" ) )
        listenerVersion = properties.getProperty( "listenerVersion" )
    }

    void save() {
        save( '' )
    }

    void save( String comment ) {
        FileOutputStream outputStream = new FileOutputStream( propFile )
        store( outputStream, comment )
        outputStream.close()
    }

    boolean isAutoSave() {
        return autoSave
    }

    void setAutoSave( boolean autoSave ) {
        this.autoSave = autoSave
    }

    @Override
    synchronized Object setProperty( String key, String value ) {
        Object previousValue = super.setProperty( key, value )

        if( isAutoSave() )
            save()

        return previousValue
    }
}
