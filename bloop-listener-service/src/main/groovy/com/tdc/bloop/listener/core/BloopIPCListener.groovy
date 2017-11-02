package com.tdc.bloop.listener.core

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.tdc.bloop.listener.utilities.BloopLogger
import com.tdc.bloop.model.BloopExecuteRequest
import com.tdc.bloop.model.BloopExecuteResponse
import com.tdc.bloop.model.BloopRequest
import com.tdc.bloop.model.BloopResponse
import org.apache.commons.lang3.StringEscapeUtils
/**
 * Contains all the default bloop listeners. This determines
 * what should happen to a request depending on its type.
 *
 * @since 1.0
 * @author Ian Gabriel Sanchez
 */
class BloopIPCListener extends Listener {

    private BloopLogger logger = new BloopLogger( this.class.getSimpleName() )
    private Map<String, Connection> connectionMap = new HashMap<>()
    private List<Connection> connectionList = new ArrayList<>()
    int connectionID = 0


    @Override
    void received( Connection connection, Object object ) {
        if( object instanceof BloopRequest ) {
            if( BloopListenerService.applications.containsKey( ( ( BloopRequest ) object ).applicationName ) ) {
                String version = BloopListenerService.applications[ ( ( BloopRequest ) object ).applicationName ].applicationVersion
                if( version == ( ( BloopRequest ) object ).applicationVersion ) {
                    BloopClient client = new BloopClient( BloopListenerService.bloopSettings )
                    client.withHost( ( ( BloopRequest ) object ).targetHost ).connect()
                    client.sendTCP( new BloopExecuteRequest(
                            applicationVersion: ( ( BloopRequest ) object ).applicationVersion,
                            applicationName: ( ( BloopRequest ) object ).applicationName,
                            bloopObject: ( ( BloopRequest ) object ).bloopObject,
                            connectionID: connectionID
                    ) )
                    connectionList[ connectionID++ ] = connection
                }
            }
        }
        else if( object instanceof BloopExecuteRequest ) {

            String command = BloopListenerService.applications[ ( ( BloopExecuteRequest ) object ).applicationName ].command
            String path = BloopListenerService.applications[ ( ( BloopExecuteRequest ) object ).applicationName ].applicationDir
            String param = "\"" + StringEscapeUtils.escapeJava( ( ( BloopExecuteRequest ) object ).bloopObject ) + "\""
            param = StringEscapeUtils.escapeJava( ( ( BloopExecuteRequest ) object ).bloopObject )
            String name = BloopListenerService.applications[ ( ( BloopExecuteRequest ) object ).applicationName ].applicationName
            if( name == "TextEditor" ) {
                command = new StringBuilder().append( "\"C:\\Program Files\\Java\\jdk1.8.0_141\\bin\\java\" \"-javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2017.2.1\\lib\\idea_rt.jar=1977:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2017.2.1\\bin\" -Dfile.encoding=UTF-8 -classpath \"C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\charsets.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\deploy.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\access-bridge-64.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\cldrdata.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\dnsns.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\jaccess.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\jfxrt.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\localedata.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\nashorn.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\sunec.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\sunjce_provider.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\sunmscapi.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\sunpkcs11.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\zipfs.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\javaws.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\jce.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\jfr.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\jfxswt.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\jsse.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\management-agent.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\plugin.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\resources.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\rt.jar;D:\\Test_Cases\\Code-Text-Editor\\target\\classes;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\com\\tdc\\bloop-api-core\\1.0.0-SNAPSHOT\\bloop-api-core-1.0.0-SNAPSHOT.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\com\\tdc\\bloop-listener-service\\1.0.0-SNAPSHOT\\bloop-listener-service-1.0.0-SNAPSHOT.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\commons-net\\commons-net\\3.6\\commons-net-3.6.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\org\\apache\\commons\\commons-text\\1.1\\commons-text-1.1.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\org\\apache\\commons\\commons-lang3\\3.5\\commons-lang3-3.5.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\com\\esotericsoftware\\kryonet\\2.22.0-RC1\\kryonet-2.22.0-RC1.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\com\\esotericsoftware\\kryo\\kryo\\2.24.0\\kryo-2.24.0.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\com\\esotericsoftware\\minlog\\minlog\\1.2\\minlog-1.2.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\org\\objenesis\\objenesis\\2.1\\objenesis-2.1.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\com\\esotericsoftware\\jsonbeans\\0.7\\jsonbeans-0.7.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\org\\codehaus\\groovy\\groovy-all\\2.5.0-beta-1\\groovy-all-2.5.0-beta-1.jar\"" )
                println command
                println name
                println param
                new ProcessBuilder( command, name, param ).start()
            }
            else if(name == "Game"){
                command = new StringBuilder().append( "\"C:\\Program Files\\Java\\jdk1.8.0_141\\bin\\java\" \"-javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2017.2.1\\lib\\idea_rt.jar=2870:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2017.2.1\\bin\" -Dfile.encoding=UTF-8 -classpath \"C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\charsets.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\deploy.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\access-bridge-64.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\cldrdata.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\dnsns.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\jaccess.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\jfxrt.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\localedata.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\nashorn.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\sunec.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\sunjce_provider.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\sunmscapi.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\sunpkcs11.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\ext\\zipfs.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\javaws.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\jce.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\jfr.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\jfxswt.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\jsse.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\management-agent.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\plugin.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\resources.jar;C:\\Program Files\\Java\\jdk1.8.0_141\\jre\\lib\\rt.jar;D:\\Test_Cases\\Wave-Game-Build\\target\\classes;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\com\\tdc\\bloop-api-core\\1.0.0-SNAPSHOT\\bloop-api-core-1.0.0-SNAPSHOT.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\com\\tdc\\bloop-listener-service\\1.0.0-SNAPSHOT\\bloop-listener-service-1.0.0-SNAPSHOT.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\commons-net\\commons-net\\3.6\\commons-net-3.6.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\org\\apache\\commons\\commons-text\\1.1\\commons-text-1.1.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\org\\apache\\commons\\commons-lang3\\3.5\\commons-lang3-3.5.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\com\\esotericsoftware\\kryonet\\2.22.0-RC1\\kryonet-2.22.0-RC1.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\com\\esotericsoftware\\kryo\\kryo\\2.24.0\\kryo-2.24.0.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\com\\esotericsoftware\\minlog\\minlog\\1.2\\minlog-1.2.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\org\\objenesis\\objenesis\\2.1\\objenesis-2.1.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\com\\esotericsoftware\\jsonbeans\\0.7\\jsonbeans-0.7.jar;C:\\Users\\Carlos Dela Cruz\\.m2\\repository\\org\\codehaus\\groovy\\groovy-all\\2.5.0-beta-1\\groovy-all-2.5.0-beta-1.jar\"" )
                println command
                println name
                println param
                new ProcessBuilder( command, name, param ).start()
            }
            else if( command.contains( "jar" ) ) {
                Process process = new ProcessBuilder( "cmd", "/k", command, path + name, param ).start();
            }

            else {
                String drive = path.split( ":" )[ 0 ]
                Process process = new ProcessBuilder( "cmd", "/k", drive + ":&&cd ${ path }&&", command, name, param ).start()

            }

            connection.sendTCP( new BloopExecuteResponse(
                    status: BloopIPCStatus.ALLOWED,
                    description: "BloopSuccessful",
                    connectionID: ( ( BloopExecuteRequest ) object ).connectionID
            ) )
        }
        else if( object instanceof BloopExecuteResponse ) {
            connectionList[ ( ( BloopExecuteResponse ) object ).connectionID ].sendTCP(
                    new BloopResponse(
                            description: "Successful"
                    )
            )
        }
    }
}

