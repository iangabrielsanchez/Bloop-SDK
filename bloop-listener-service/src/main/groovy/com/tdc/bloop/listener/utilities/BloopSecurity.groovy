package com.tdc.bloop.listener.utilities

import sun.misc.BASE64Decoder
import sun.misc.BASE64Encoder

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.security.Key

class BloopSecurity {

    private static final String ALGO = "AES"

    /**
     * Encrypt a string with AES algorithm.
     *
     * @param data is a string
     * @return the encrypted string
     */
    static String encrypt( String data, String keyValue ) throws Exception {
        Key key = generateKey( keyValue )
        Cipher c = Cipher.getInstance( ALGO )
        c.init( Cipher.ENCRYPT_MODE, key )
        byte[] encVal = c.doFinal( data.getBytes() )
        return new BASE64Encoder().encode( encVal )
    }

    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    static String decrypt( String encryptedData, String keyValue ) throws Exception {
        Key key = generateKey( keyValue )
        Cipher c = Cipher.getInstance( ALGO )
        c.init( Cipher.DECRYPT_MODE, key )
        byte[] decordedValue = new BASE64Decoder().decodeBuffer( encryptedData )
        byte[] decValue = c.doFinal( decordedValue )
        return new String( decValue )
    }

    /**
     * Generate a new encryption key.
     */
    private static Key generateKey( String keyValue ) throws Exception {
        byte[] bytes = keyValue.getBytes()
        return new SecretKeySpec( bytes, ALGO )
    }

}