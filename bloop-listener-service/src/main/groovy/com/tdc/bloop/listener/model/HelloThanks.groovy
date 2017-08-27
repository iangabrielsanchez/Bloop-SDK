package com.tdc.bloop.listener.model

import com.tdc.bloop.listener.core.HelloStatus

class HelloThanks {
//    String key =

    HelloThanks(HelloResponse response){
        if(response.status == HelloStatus.AUTHORIZED){

        }
        else if(response.status == HelloStatus.MAC_MISMATCH){

        }
        else if(response.status == HelloStatus.VERSION_MISMATCH){

        }
    }

}
