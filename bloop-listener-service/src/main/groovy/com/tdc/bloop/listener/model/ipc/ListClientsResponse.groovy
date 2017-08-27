package com.tdc.bloop.listener.model.ipc

import com.sun.org.apache.xpath.internal.operations.Bool
import com.tdc.bloop.listener.core.BloopListenerService
import com.tdc.bloop.listener.model.HostInformation
import com.tdc.bloop.listener.utilities.BloopLogger

/**
 * Created by tjako on 8/26/2017.
 */
class ListClientsResponse {
    String[] hostNames
    String[] signatures

    ListClientsResponse ( ListClientsRequest request ) {
        BloopLogger logger = new BloopLogger( this.class.getSimpleName() )
        Map<String, HostInformation> clients =  BloopListenerService.clients
        List<ApplicationInformarion> applications = BloopListenerService.applications

        switch( request.filter.toUpperCase() ) {
            case "APP":
                def checkApplication = appicationExists( applications, request )
                if(checkApplication.result) {
                    setFields()
                } else {
                    logger.error( 'Request Failure', checkApplication.message )
                }
                break;
            case "APP-VER":
                def checkApplication = appicationExists( applications, request, true )
                if(checkApplication.result) {
                    setFields()
                } else {
                    logger.error( 'Request Failure', checkApplication.message )
                }
                break;
            default: // Returns All Applications
                setFields()
                break
        }
    }

    void setFields() {
        clients.keySet().each { -> key
            hostNames.add( key )
            signatures.add( clients.get( key ))
        }
        this.HostNames = hostNames.toArray()
        this.Signatures = signatures.toArray()
    }

    Map<Boolean, String> appicationExists( List<ApplicationInformarion> applicationList, ListClientsRequest request, boolean versionCheck = false) {
        applicationList.each { -> application
            if( application.signature == request.applicationSignature) {
                if(versionCheck) {
                    if(application.version == request.applicationVersion) {
                        return [result: true, message:"Application Registered."];
                    }
                    return [result: false, message:"Application Version Mismatch."];
                }
                return [result: true, message:"Application Registered."];
            }
        }
        return [result: false, message:"Application not registered."];
    }
}
