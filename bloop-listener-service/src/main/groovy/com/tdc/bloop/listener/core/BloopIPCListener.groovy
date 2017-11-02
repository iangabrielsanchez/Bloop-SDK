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
            
            new ProcessBuilder("cmd", "/k", "java", "-jar", "${path}${name}", param).start()

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