package com.gmmh.heroes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmmh.heroes.Controller.CtrlConexion;
import com.gmmh.heroes.Controller.CtrlImagen;
import com.gmmh.heroes.Object.CallbackGenerico;
import com.gmmh.heroes.Object.ConexionApi;
import com.gmmh.heroes.Object.Md5;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CallbackGenerico {

    private Configuracion configuracion;
    private TableLayout tableLayout;
    private EditText editTextBuscar;
    private Button btnbuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.editTextBuscar=(EditText)findViewById(R.id.busqueda_edit);
        this.btnbuscar=(Button)findViewById(R.id.busqueda_btn);
        this.btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearHeroes(editTextBuscar.getText().toString());
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





        this.configuracion=((Configuracion) this.getApplication());
        System.out.println("Marvel personajes: ");
        long unixTime = System.currentTimeMillis() / 1000L;
        String ts =  ""+unixTime;
        String hash= Md5.generarMD5(ts+this.configuracion.getPRIVATE_KEY()+this.configuracion.getPUBLIC_KEY());
        String url="http://gateway.marvel.com/v1/public/characters?apikey="+this.configuracion.getPUBLIC_KEY()+"&ts="+ts+"&hash="+hash;
        System.out.println(url);


        CtrlConexion ctrlConexion=new CtrlConexion();
        ctrlConexion.setCallbackGenerico(this);
        ctrlConexion.setUrl(url);
        ctrlConexion.execute();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), Carga.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio)
        {

        } else if (id == R.id.nav_acerca) {
            lanzar_vista(Creditos.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void agregarHeroe(int index, String id, String nombre, ImageView imagen)
    {
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tableParams.setMargins(0,30,0,30);
        tableParams.weight=1.0f;

        TableRow.LayoutParams rowParamsTitulo = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT);
        rowParamsTitulo.setMargins(10,0,0,0);
        rowParamsTitulo.weight=1.0f;


        TableRow tableRow = new TableRow(this);
        tableRow.setId(index);
        tableRow.setBackgroundResource(R.drawable.border);
        tableRow.setLayoutParams(tableParams);
        tableRow.setGravity(Gravity.CENTER);


        RelativeLayout.LayoutParams paramsImg = new RelativeLayout.LayoutParams(100, 100);
        if(imagen==null)
        {
            imagen=new ImageView(this);
            imagen.setImageResource(R.drawable.carpeta_marvel);
        }
        //imagen.setLayoutParams(paramsImg);

        TextView txtid = new TextView(this);
        txtid.setLayoutParams(rowParamsTitulo);
        txtid.setText(id);
        txtid.setTextColor(Color.rgb(0,0,0));

        TextView txtnombre = new TextView(this);
        txtnombre.setLayoutParams(rowParamsTitulo);
        txtnombre.setText(nombre);
        txtnombre.setTextColor(Color.rgb(0,0,0));

        if(index>=0)
        {
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    configuracion.setIndex(v.getId());
                    lanzar_vista(InfoHeroe.class);
                }
            });
        }
        tableRow.addView(imagen);
        tableRow.addView(txtid);
        tableRow.addView(txtnombre);
        this.tableLayout.addView(tableRow);


        imagen.getLayoutParams().height = 200;
        imagen.getLayoutParams().width = 200;
        imagen.setScaleType(ImageView.ScaleType.FIT_XY);
    }
    public void crearHeroes(String buscar)
    {
        String respuesta=this.configuracion.getJsonContenido();
        try {
            this.tableLayout = (TableLayout)findViewById(R.id.table);
            this.tableLayout.removeAllViews();
            JSONObject jsonObject = new JSONObject(respuesta);
            JSONObject mainObject = jsonObject.getJSONObject("data");
            JSONArray resultado = mainObject.getJSONArray("results");
            ConexionApi conexionApi=new ConexionApi();

            agregarHeroe(-1,"Identificador", "Nombre", null);

            for (int i = 0; i < resultado.length(); i++)
            {
                JSONObject heroe = resultado.getJSONObject(i);
                int id = heroe.getInt("id");
                String nombre = heroe.getString("name");
                JSONObject imagen = heroe.getJSONObject("thumbnail");
                String imagen_url = imagen.getString("path") + "." + imagen.getString("extension");
                System.out.println("url imagen: "+imagen_url);
                ImageView imagview= new ImageView(this);
                imagview.setMaxWidth(50);

                //buscar
                if(buscar!=null)
                {
                    if(!nombre.toLowerCase().contains(buscar.toLowerCase()))
                    {
                        continue;
                    }
                }
                new CtrlImagen(imagview).execute(imagen_url);
                this.agregarHeroe(i, ""+id, nombre, imagview);
            }
        } catch (JSONException e){
            System.out.println(e.toString());
        }
    }
    @Override
    public void callBackExito(String respuesta) {
        this.configuracion.setJsonContenido(respuesta);
        if(respuesta!=null)
        {
            runOnUiThread(new Runnable() {

                @Override
                public void run()
                {
                    crearHeroes(null);
                }
            });
        }
    }

    @Override
    public void callBackFallo(String mensaje) {

    }
    public void lanzar_vista(Class<?> clase) {
        Intent intent = new Intent(getApplicationContext(), clase);
        startActivity(intent);
    }

}
