package com.tdc.bloop.models

class StringResponse implements Serializable, Cloneable {

    private String text

    void setText( String text ) {
        this.text = text
    }

    String getText() {
        return this.text
    }

}
