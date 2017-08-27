package com.tdc.bloop.listener.model.ipc

import com.tdc.bloop.listener.model.Response

/**
 * Created by tjako on 8/26/2017.
 */
class BloopResponse extends Response {
    String applicationID

    BloopResponse(BloopRequest request ){
        checkHost("Host ID");
    }
}

