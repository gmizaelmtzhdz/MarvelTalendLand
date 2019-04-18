package com.gmmh.heroes;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmmh.heroes.Controller.CtrlImagen;
import com.gmmh.heroes.Object.ConexionApi;

import org.json.JSONArray;
import org.json.JSONObject;

public class InfoHeroe extends Activity {
    private Configuracion configuracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_heroe);

        this.configuracion=((Configuracion) this.getApplication());
        int i=this.configuracion.getIndex();
        String respuesta=this.configuracion.getJsonContenido();
        try
        {
            JSONObject jsonObject = new JSONObject(respuesta);
            JSONObject mainObject = jsonObject.getJSONObject("data");
            JSONArray resultado = mainObject.getJSONArray("results");
            ConexionApi conexionApi = new ConexionApi();


            JSONObject heroe = resultado.getJSONObject(i);
            int id = heroe.getInt("id");
            String nombre = heroe.getString("name");
            String descripcion = heroe.getString("description");
            JSONObject imagen = heroe.getJSONObject("thumbnail");
            String imagen_url = imagen.getString("path") + "." + imagen.getString("extension");
            System.out.println("url imagen: " + imagen_url);
            ImageView imagview =(ImageView)findViewById(R.id.img_heroe);
            new CtrlImagen(imagview).execute(imagen_url);

            ((TextView)findViewById(R.id.nombre_heroe)).setText(nombre);
            ((TextView)findViewById(R.id.biografia_heroe)).setText(descripcion);
            JSONArray comics=heroe.getJSONObject("comics").getJSONArray("items");
            JSONArray series=heroe.getJSONObject("series").getJSONArray("items");
            String comics_txt="";
            for(i=0;i<comics.length();i++)
            {
                comics_txt=comics_txt+"• "+comics.getJSONObject(i).getString("name")+"\n";
            }
            ((TextView)findViewById(R.id.comics_heroe)).setText(comics_txt);


            String series_txt="";
            for(i=0;i<series.length();i++)
            {
                series_txt=series_txt+"• "+series.getJSONObject(i).getString("name")+"\n";
            }
            ((TextView)findViewById(R.id.series_heroe)).setText(series_txt);

        }catch (Exception e)
        {

        }

    }
}
