package com.tdc.bloop.listener.model

/**
 * Created by tjako on 8/13/2017.
 */
class Response {
    boolean succeeded
    String message

    void basicResponse( boolean succeeded, String message ) {
        this.succeeded = succeeded
        this.message = message
    }
}
