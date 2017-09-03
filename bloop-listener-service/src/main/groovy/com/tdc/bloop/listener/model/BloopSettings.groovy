package com.tdc.bloop.listener.model

import groovy.transform.CompileStatic

/**
 * BloopSettings is a class that contains all the
 * parameters required for blooping across the network.
 *
 * @since 1.0
 * @author Ian Gabriel Sanchez
 */
@CompileStatic
class BloopSettings extends Object {

    int timeout
    int ipcPort
    int tcpPort
    int udpPort
    int bufferSize
    String listenerVersion

    /**
     * Creates a new instance of BloopSettings with the default values.
     * The default values are:
     * <ul>
     * <li>timeout: 5000
     * <li>ipcPort: 25666
     * <li>tcpPort: 25667
     * <li>udpPort: 25668
     * <li>bufferSize: 1024
     * <li>listenerVersion: '1.0.0'
     * </ul>
     */
    BloopSettings() {
        timeout = 5000
        ipcPort = 25666
        tcpPort = 25667
        udpPort = 25668
        bufferSize = 1024
        listenerVersion = '1.0.0'
    }

    /**
     * Creates a new instance of BloopSettings with the specified parameters
     * @param listenerVersion The version of your application
     * @param host The IP Address or Computer name of the host
     * @param ipcPort The port that will be used for inter-process communication
     * @param tcpPort The port that will be used for the BloopServer
     * @param udpPort The port that will be used for network discovery
     * @param timeout The timeout before the application decides that it's waiting too long
     */
    BloopSettings( String listenerVersion, int ipcPort, int tcpPort, int udpPort, int timeout, int bufferSize ) {
        this.listenerVersion = listenerVersion
        this.ipcPort = ipcPort
        this.tcpPort = tcpPort
        this.udpPort = udpPort
        this.timeout = timeout
        this.bufferSize = bufferSize
    }

}
