package com.tdc.bloop.model

import com.tdc.bloop.listener.core.BloopIPCStatus

class BloopExecuteResponse {

    BloopIPCStatus status
    String description
    int connectionID
}
