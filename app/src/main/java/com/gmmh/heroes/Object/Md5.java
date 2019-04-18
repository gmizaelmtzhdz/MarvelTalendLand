package com.gmmh.heroes.Object;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by root on 16/04/19.
 */

public class Md5 {
    public static String generarMD5(String contenido)
    {
        StringBuilder resultado = new StringBuilder();
        try
        {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update((contenido).getBytes());
            byte[] bytes = mDigest.digest();
            for(byte b : bytes)
            {
                resultado.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.println(e.toString());
        }
        return resultado.toString();
    }
}
