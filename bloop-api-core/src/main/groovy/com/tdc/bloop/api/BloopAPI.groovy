package com.tdc.bloop.api

import com.tdc.bloop.model.BloopHostRequest
import com.tdc.bloop.model.BloopHosts
import com.tdc.bloop.model.BloopRequest
import com.tdc.bloop.model.BloopResult

import java.util.concurrent.Future

interface BloopAPI {

    Future<BloopResult> bloop( BloopRequest request )

    Future<BloopHosts> listBloopHosts( BloopHostRequest request )

    //will add more features after i finish the listener

}
