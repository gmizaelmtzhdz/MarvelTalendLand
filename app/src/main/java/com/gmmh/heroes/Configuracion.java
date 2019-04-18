package com.gmmh.heroes;

import android.app.Application;

/**
 * Created by root on 15/04/19.
 */

public class Configuracion extends Application
{
    private String PRIVATE_KEY;
    private String PUBLIC_KEY;
    private int index;
    private String jsonContenido;

    public Configuracion()
    {
        this.PRIVATE_KEY="";
        this.PUBLIC_KEY="";
    }

    public String getPRIVATE_KEY() {
        return PRIVATE_KEY;
    }

    public void setPRIVATE_KEY(String PRIVATE_KEY) {
        this.PRIVATE_KEY = PRIVATE_KEY;
    }

    public String getPUBLIC_KEY() {
        return PUBLIC_KEY;
    }

    public void setPUBLIC_KEY(String PUBLIC_KEY) {
        this.PUBLIC_KEY = PUBLIC_KEY;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getJsonContenido() {
        return jsonContenido;
    }

    public void setJsonContenido(String jsonContenido) {
        this.jsonContenido = jsonContenido;
    }
}
