package com.tdc.bloop.core

import groovy.transform.CompileStatic

/**
 * Created by ian.sanchez on 5/31/17.
 */
@CompileStatic
class BloopSettings {

    private int timeout = 5000
    private int hostPort = 25667
    private String hostName
    private String applicationName = 'BloopApplication'

    int getHostPort() {
        return hostPort
    }

    void setHostPort( int hostPort ) {
        this.hostPort = hostPort
    }

    int getTimeout() {
        return timeout
    }

    void setTimeout( int timeout ) {
        this.timeout = timeout
    }

    String getApplicationName() {
        return applicationName
    }

    void setApplicationName( String applicationName ) {
        this.applicationName = applicationName

    }

}
