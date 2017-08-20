package com.tdc.bloop.listener.utilities

import com.esotericsoftware.kryo.Kryo
import com.tdc.bloop.listener.model.*
import groovy.transform.CompileStatic
/**
 * This class contains all the methods required for auditing all BloopTransactions.
 *
 * @since 1.0.0
 * @author Ian Gabriel Sanchez
 */
@CompileStatic
class BloopAuditor {

    private static final Class[] defaultTypes = [
            ClientListRequest.class,
            ClientListResponse.class,
            HelloRequest.class,
            HelloResponse.class,
            Response.class
    ]

    /**
     * Classes that are going to be sent over the network should be first registered
     * on both client and server. This allows them to be serialized by the
     * Kryo Serialization Library
     * @see <a href="https://github.com/EsotericSoftware/kryo">Kryo Serialization Library</a>
     * @param kryo The kryo instance of the BloopClient or the BloopServer
     * @param dataTypes The datatypes that need to be registered.
     */
    static void registerClasses( Kryo kryo, Class[] dataTypes ) {
        for( Class dataType : dataTypes ) {
            kryo.register( dataType )
        }
    }

    /**
     * Registers the default classes to be serialized by the Kryo Serialization Library
     * @see <a href="https://github.com/EsotericSoftware/kryo">Kryo Serialization Library</a>
     * @param kryo The kryo instance of the BloopClient or the BloopServer
     */
    static void registerDefaultClasses( Kryo kryo ) {
        registerClasses( kryo, defaultTypes )
    }

    static HostInformation getHostInformation() {

        Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces()

        while( networkInterfaces.hasMoreElements() ) {

            NetworkInterface networkInterface = ( NetworkInterface ) networkInterfaces.nextElement()
            Enumeration networkAddresses = networkInterface.inetAddresses
            List<InterfaceAddress> addressList = networkInterface.interfaceAddresses
            HostInformation information = new HostInformation()

            while( networkAddresses.hasMoreElements() ) {

                InetAddress inetAddress = ( InetAddress ) networkAddresses.nextElement()

                if( inetAddress.isSiteLocalAddress() ) {
                    information.inetAddress = inetAddress
                    information.networkInterface = networkInterface

                    for( InterfaceAddress address in addressList ) {
                        if( address.address instanceof Inet4Address ) {
                            information.networkPrefix = address.networkPrefixLength
                        }
                    }
                    return information
                }
            }
        }
    }

}
