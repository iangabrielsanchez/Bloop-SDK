package com.tdc.bloop.listener.core

import com.esotericsoftware.jsonbeans.Json
import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.tdc.bloop.listener.utilities.BloopLogger
import com.tdc.bloop.listener.utilities.BloopSecurity
import com.tdc.bloop.model.HelloRequest
import com.tdc.bloop.model.HelloResponse
import com.tdc.bloop.model.HelloThanks
import groovy.json.JsonBuilder
import groovy.transform.CompileStatic
/**
 * Contains all the default bloop listeners. This determines
 * what should happen to a request depending on its type.
 *
 * @since 1.0
 * @author Ian Gabriel Sanchez
 */
@CompileStatic
class BloopDefaultListeners extends Listener {

    private BloopLogger logger = new BloopLogger( this.class.getSimpleName() )

    @Override
    void received( Connection connection, Object object ) {

        if ( BloopListenerService.secured && object instanceof String ) {
            String json = (String)object
            if ((object = convertJson(json, HelloRequest.class))) {
                logger.log("Received HelloRequest")
                connection.sendTCP(
                        BloopSecurity.encrypt(
                                new JsonBuilder( new HelloResponse((HelloRequest) object)).toString(),
                                getKey(connection)
                        )
                )
                logger.log("Sent HelloResponse")
            }
            else if ((object = convertJson(json, HelloResponse.class))) {
                logger.log("Received HelloResponse")

                connection.sendTCP(
                        BloopSecurity.encrypt(
                                new JsonBuilder(new HelloThanks((HelloResponse) object)).toString(),
                                getKey(connection)
                        )
                )
                logger.log("Sent HelloThanks")
            }
            else if ((object = convertJson(json, HelloThanks.class))) {
                logger.log("Received HelloThanks")
//            if( BloopListenerService.clients.containsKey( ( ( HelloThanks ) object ).hostIP ) ) {
                BloopListenerService.clients.get(((HelloThanks) object).hostIP).key = ((HelloThanks) object).key
                logger.log("Added Key")
                connection.close()
//            }
            }
        }
        else {
            if (object instanceof HelloRequest) {
                logger.log("Received HelloRequest")
                connection.sendTCP(new HelloResponse((HelloRequest) object))
                logger.log("Sent HelloResponse")
            } else if (object instanceof HelloResponse) {
                logger.log("Received HelloResponse")
                connection.sendTCP(new HelloThanks((HelloResponse) object))
                logger.log("Sent HelloThanks")
            } else if (object instanceof HelloThanks) {
                logger.log("Received HelloThanks")
//            if( BloopListenerService.clients.containsKey( ( ( HelloThanks ) object ).hostIP ) ) {
                BloopListenerService.clients.get(((HelloThanks) object).hostIP).key = ((HelloThanks) object).key
                logger.log("Added Key")
                connection.close()
//            }
            }
        }
    }

    static Object convertJson(String json, Class<?> clazz){
        try{
            return new Json().fromJson(clazz,json)
        }
        catch(Exception ex){
            return null
        }
    }

    static String getKey(Connection connection){
        return BloopListenerService.clients.get(connection.remoteAddressTCP.getHostString()).key
    }


}

