package com.tdc.bloop.listener.utilities

import com.esotericsoftware.kryo.Kryo
import com.tdc.bloop.listener.core.BloopListenerService
import com.tdc.bloop.listener.core.HelloStatus
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
            Response.class,
            BloopLogger.class,
            StringBuilder.class,
            HelloStatus.class,
            Client.class
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
        HostInformation information = new HostInformation()

        while( networkInterfaces.hasMoreElements() ) {
            NetworkInterface networkInterface = ( NetworkInterface ) networkInterfaces.nextElement()
            Enumeration networkAddresses = networkInterface.inetAddresses
            List<InterfaceAddress> addressList = networkInterface.interfaceAddresses

            while( networkAddresses.hasMoreElements() && information.inetAddress == null ) {
                information = new HostInformation()
                InetAddress inetAddress = ( InetAddress ) networkAddresses.nextElement()

                if( inetAddress.isSiteLocalAddress() ) {
                    information.inetAddress = inetAddress
                    information.networkInterface = networkInterface

                    for( InterfaceAddress address in addressList ) {
                        if( address.address instanceof Inet4Address ) {
                            information.networkPrefix = address.networkPrefixLength
                            byte[] mac = information.networkInterface.getHardwareAddress()
                            StringBuilder builder = StringBuilder.newInstance()
                            for( int i = 0; i < mac.length; i++ ) {
                                builder.append( String.format( "%02X%s", mac[ i ], ( i < mac.length - 1 ) ? "-" : "" ) )
                            }
                            information.macAddress = builder.toString()
                            information.bloopPort = BloopListenerService.bloopSettings.tcpPort
                            return information
                        }
                    }
                }
            }
        }
    }

    static HelloRequest generateHelloRequest() {
        HostInformation hostInfo = getHostInformation()
        HelloRequest helloRequest = new HelloRequest(
                hostIP: hostInfo.inetAddress.hostAddress,
                bloopPort: BloopListenerService.bloopSettings.tcpPort,
                macAddress: hostInfo.macAddress,
                version: BloopListenerService.bloopSettings.listenerVersion
        )
    }

    static boolean compareVersion( String operation, String version1, String version2 ) {
        String[] parts1 = version1.split( '-' )[ 0 ].split( '\\.' )
        String[] parts2 = version2.split( '-' )[ 0 ].split( '\\.' )
        if( parts1.length != 3 || parts2.length != 3 ) {
            throw new Exception( "Invalid versioning" )
        }

        int major1 = Integer.parseInt( parts1[ 0 ] )
        int minor1 = Integer.parseInt( parts1[ 1 ] )
        int patch1 = Integer.parseInt( parts1[ 2 ] )

        int major2 = Integer.parseInt( parts2[ 0 ] )
        int minor2 = Integer.parseInt( parts2[ 1 ] )
        int patch2 = Integer.parseInt( parts2[ 2 ] )

        switch( operation ) {
            case '<':
                if( major1 < major2 ) {
                    return true
                }
                else if( minor1 < minor2 ) {
                    return true
                }
                else if( patch1 < patch2 ) {
                    return true
                }
                else {
                    return false
                }
                break
            case '>':
                if( major1 > major2 ) {
                    return true
                }
                else if( minor1 > minor2 ) {
                    return true
                }
                else if( patch1 > patch2 ) {
                    return true
                }
                return false
            case '==':
                if( major1 == major2 ) {
                    if( minor1 == minor2 ) {
                        if( patch1 == patch2 ) {
                            return true
                        }
                    }
                }
                return false
            case '<=':
                if( major1 == major2 ) {
                    if( minor1 == minor2 ) {
                        if( patch1 == patch2 ) {
                            return true
                        }
                        else if( patch1 < patch2 ) {
                            return true
                        }
                    }
                    else if( minor1 < minor2 ) {
                        return true
                    }
                }
                else if( major1 < major2 ) {
                    return true
                }
                return false
            case '>=':
                if( major1 == major2 ) {
                    if( minor1 == minor2 ) {
                        if( patch1 == patch2 ) {
                            return true
                        }
                        else if( patch1 > patch2 ) {
                            return true
                        }
                    }
                    else if( minor1 > minor2 ) {
                        return true
                    }
                }
                else if( major1 > major2 ) {
                    return true
                }
                return false
        }
    }

    static boolean withinVersion(String version1, String version2){

    }

}
