package com.tdc.bloop.listener.core

import com.esotericsoftware.jsonbeans.Json
import com.esotericsoftware.jsonbeans.OutputType
import com.tdc.bloop.listener.utilities.BloopSecurity
import com.tdc.bloop.model.BloopApplication
import com.tdc.bloop.model.BloopObject
import com.tdc.bloop.model.BloopRequest
import com.tdc.bloop.model.HelloRequest
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.apache.commons.lang3.StringEscapeUtils

import java.lang.reflect.Constructor

class MainClass {

    static void main( String[] args ) {
        def x = new HelloRequest(
                hostIP: "255.255.255.255",
                bloopPort: 25667,
                macAddress: "AB-CD-EF-GH-IJ",
                version:  "1.0.0"
        )
//        String key = BloopSecurity.generateRandomKey()
        String encrypted = BloopSecurity.encrypt(new JsonBuilder(x).toString(),key)
//        println encrypted
//        println "KEY IS ${key}"

        String json = BloopSecurity.decrypt("0bl5jAGy5LsNMNRalssq4Gv1tjaq7mltSbN5TEZWyzjuKP/5ScyMKQS+hoSRmwm88V5WOvyFu4ik\n" +
                "zK/82MWyZvquVqGBbZCeEenLQiXUNNC8id4EDJlUqdrGXmntCo6x", "B2`lVUhD,Lw++#I\"")

        println json
//        Object o = new JsonSlurper().parseText(json)
//        println  (canBeCastedTo(o,HelloRequest.class))
////        Json js = new Json()
////        try{
////            Object obj = js.fromJson(BloopRequest.class, json)
////            println obj.class
////        }
////        catch (Exception ex){
////            println "fail"
////        }
        Object obj
        if ( (obj = convertJson(json, HelloRequest.class))){
            println obj
        }
        else{
            println "fail"
        }

    }

    static Object convertJson(String json, Class<?> clazz){
        try{
            return new Json().fromJson(clazz,json)
        }
        catch(Exception ex){
            return null
        }
    }
}