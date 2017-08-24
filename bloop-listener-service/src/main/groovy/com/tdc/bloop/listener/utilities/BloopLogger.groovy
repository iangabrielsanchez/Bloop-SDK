package com.tdc.bloop.listener.utilities

import java.time.LocalDateTime

class BloopLogger {

    private String className
    private StringBuilder builder
    private static StringBuilder allLogs = StringBuilder.newInstance()

    BloopLogger( String className ) {
        this.className = className
        builder = StringBuilder.newInstance()
    }

    void error( String title, String description ) {
        LocalDateTime now = LocalDateTime.now()
        String log = "${ now } [${ className }] ERROR: \"${ title }\" - ${ description }\n"
        builder.append( log )
        System.err.println( log )
        allLogs.append( log )
    }

    void error( String description ) {
        LocalDateTime now = LocalDateTime.now()
        String log = "${ now } [${ className }] ERROR   ${ description }"
        builder.append( log )
        System.err.println( log )
        allLogs.append( log )
    }

    void warn( String title, String description ) {
        LocalDateTime now = LocalDateTime.now()
        String log = "${ now } [${ className }] WARNING \"${ title }\" - ${ description }\n"
        builder.append( log )
        System.out.println( log )
        allLogs.append( log )
    }

    void warn( String description ) {
        LocalDateTime now = LocalDateTime.now()
        String log = "${ now } [${ className }] WARNING ${ description }\n"
        builder.append( log )
        System.out.println( log )
        allLogs.append( log )
    }

    void log( String title, String description ) {
        LocalDateTime now = LocalDateTime.now()
        String log = "${ now } [${ className }] LOG     \"${ title }\" - ${ description }\n"
        builder.append( log )
        System.out.println( log )
        allLogs.append( log )
    }

    void log( String description ) {
        LocalDateTime now = LocalDateTime.now()
        String log = "${ now } [${ className }] LOG     ${ description }\n"
        builder.append( log )
        System.out.println( log )
        allLogs.append( log )
    }

}