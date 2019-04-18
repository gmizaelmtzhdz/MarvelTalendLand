package com.gmmh.heroes.Controller;

import android.os.AsyncTask;

import com.gmmh.heroes.Object.CallbackGenerico;
import com.gmmh.heroes.Object.ConexionApi;

/**
 * Created by root on 16/04/19.
 */

public class CtrlConexion extends AsyncTask<Void,Void,String>
{
    private String url;
    private CallbackGenerico callbackGenerico;
    @Override
    protected String doInBackground(Void... voids) {
        ConexionApi conexionApi=new ConexionApi();
        String cont = conexionApi.getInformacion(this.url);
        System.out.println(cont);

        callbackGenerico.callBackExito(cont);
        return cont;
    }

    public CallbackGenerico getCallbackGenerico() {
        return callbackGenerico;
    }

    public void setCallbackGenerico(CallbackGenerico callbackGenerico) {
        this.callbackGenerico = callbackGenerico;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
