package com.tdc.bloop.listener.core

import groovy.json.JsonSlurper
import org.apache.commons.lang3.SystemUtils;

/**
 * Created by tjako on 8/20/2017.
 */
class BloopIPC {
    Pipe pipe

    void createPipe() {
        if(SystemUtils.IS_OS_WINDOWS) {
            pipe = new Pipe('writepipe', 'readpipe', 'windows')
        }
        else if(SystemUtils.IS_OS_LINUX) {
            System.out.println( 'It.\'s a Linux OS' );
        }
    }
}

class Pipe {
    RandomAccessFile writer
    RandomAccessFile reader
    Pipe(String writer, reader, os) {
        if(os == 'windows') {
            this.reader = new RandomAccessFile("\\\\.\\pipe\\${writer}", 'rw')
            this.writer = new RandomAccessFile("\\\\.\\pipe\\${reader}", 'rw')
        }
        readMessage()
    }

    void sendMessage(String message) {
        writer.write ( message.getBytes() )
    }

    void readMessage() {
        def thread = Thread.start {
            while(true) {
                try {
                    String request = reader.readLine()
                    BloopRequestHandler.handleRequest( new JsonSlurper().parseText( request ) );
                }  catch (IOException e) {
                    println 'Pipe Closed'
                }
                break
            }
        }
        println( "Listening" )
    }

    void close() {
        writer.close()
        reader.close()
    }
}

