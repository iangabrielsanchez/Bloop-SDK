package com.tdc.bloop.listener.model

/**
 * Created by tjako on 8/13/2017.
 */
class Response {
    boolean succeeded
    String message
    String token

    void basicResponse( boolean succeeded, String message, String token ) {
        this.succeeded = succeeded
        this.message = message
        this.token = token
    }
}
