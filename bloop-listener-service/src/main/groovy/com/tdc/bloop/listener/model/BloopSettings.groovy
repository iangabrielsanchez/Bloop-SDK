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
    int hostPort
    String host
    String applicationName
    String applicationVersion

    /**
     * Creates a new instance of BloopSettings with the default values.
     * The default values are:
     * <ul>
     * <li>timeout: 5000
     * <li>hostPort: 25667
     * <li>host: 'localhost'
     * <li>applicationName: 'BloopApplication'
     * <li>applicationVersion: '1.0.0'
     * </ul>
     */
    BloopSettings() {
        timeout = 5000
        hostPort = 25667
        host = 'localhost'
        applicationName = 'BloopApplication'
        applicationVersion = '1.0.0'
    }

    /**
     * Creates a new instance of BloopSettings with the specified parameters
     * @param applicationName The name of your application.
     * @param applicationVersion The version of your application
     * @param host The IP Address or Computer name of the host
     * @param hostPort The port that will be used when the device is selected as a server
     * @param timeout The timeout before the application decides that it's waiting too long
     */
    BloopSettings( String applicationName, String applicationVersion, String host, int hostPort, int timeout ) {
        this.applicationName = applicationName
        this.applicationVersion = applicationVersion
        this.host = host
        this.hostPort = hostPort
        this.timeout = timeout
    }

    BloopSettings withApplicationName( String applicationName ) {
        this.applicationName = applicationName
        return this
    }

    BloopSettings withApplicationVersion( String applicationVersion ) {
        this.applicationVersion = applicationVersion
        return this
    }

    BloopSettings withHost( String host ) {
        this.host = host
        return this
    }

    BloopSettings withHostPort( int hostPort ) {
        this.hostPort = hostPort
        return this
    }

    BloopSettings withTimeout( int timeout ) {
        this.timeout = timeout
        return this
    }

}
