package com.gmmh.heroes.Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class ConexionApi {

        public ConexionApi()
        {

        }
        public String getInformacion(String url_peticion)
        {
            URL url = this.armar_url(url_peticion);
            String json_respuesta = null;
            try {
                json_respuesta = this.petcionHTTP(url);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            return json_respuesta;
        }
    private  String leerContenido(InputStream contenido) throws IOException
    {
        StringBuilder resultado = new StringBuilder();
        if (contenido != null)
        {
            InputStreamReader lector_contenido = new InputStreamReader(contenido, Charset.forName("UTF-8"));
            BufferedReader lector = new BufferedReader(lector_contenido);
            String fila = lector.readLine();
            while (fila != null)
            {
                resultado.append(fila);
                fila = lector.readLine();
            }
        }
        return resultado.toString();
    }
        private URL armar_url(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException e) {
                System.out.println(e.toString());
            }
            return url;
        }

        private String petcionHTTP(URL url) throws IOException
        {
            String json_respuesta = "";
            if (url == null)
            {
                return json_respuesta;
            }
            HttpURLConnection conexion = null;
            InputStream contenido = null;
            try {
                conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestMethod("GET");
                conexion.setConnectTimeout(35000);
                conexion.setReadTimeout(35000);
                conexion.connect();


                if (conexion.getResponseCode() == 200)
                {
                    contenido = conexion.getInputStream();
                    json_respuesta = leerContenido(contenido);
                }
            }
            catch (IOException e)
            {
                System.out.println(e.toString());
            }
            finally
            {
                if(conexion != null)
                {
                    conexion.disconnect();
                }
                if(contenido != null)
                {
                    contenido.close();
                }
            }
            return json_respuesta;
        }


}
