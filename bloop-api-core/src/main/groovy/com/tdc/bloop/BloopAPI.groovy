package com.tdc.bloop

import com.tdc.bloop.model.BloopHostRequest
import com.tdc.bloop.model.BloopHosts
import com.tdc.bloop.model.BloopRequest
import com.tdc.bloop.model.BloopResult

import java.util.concurrent.Future

interface BloopAPI {

    Future<BloopResult> bloop( BloopRequest request )

    BloopHosts listBloopHosts( BloopHostRequest request )

}
