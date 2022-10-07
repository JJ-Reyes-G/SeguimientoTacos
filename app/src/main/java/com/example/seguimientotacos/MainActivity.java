package com.example.seguimientotacos;

import android.content.ContentValues;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    static Httppostaux post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btn_consultar(View v){
        String url = "http://intranet.grupolacabana.net:8080/ingcampo/soliagricola/ws/soliagricola_ws.php/btnEjemplo";

        Map<String, String> params = new HashMap();
        params.put("nombre", "mensaje ejemplo jjbc");


        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //textView.setText("Response: " + response.toString());

                /*
                * {
                    "cat":"it",
                    "languages":[
                        {"id":1,"ide":"Eclipse","name":"Java"},
                        {"id":2,"ide":"XCode","name":"Swift"},
                        {"id":3,"ide":"Visual Studio","name":"C#"}
                    ]
                }
                * */
                //JSONArray array = root.getJSONArray ("languages");
                try {
                    JSONArray jsonArray = response.getJSONArray("name");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject e = jsonArray.getJSONObject(i);
                        //e.getString("userName")
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //TODO: handle success
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);

    }

    public boolean descargarConfiguracionDispositivo( String user, String pass, String dominio )
    {
        try{
            ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
            postparameters2send.add(new BasicNameValuePair("USERDOMINIO",dominio));
            postparameters2send.add(new BasicNameValuePair("USER",user));
            postparameters2send.add(new BasicNameValuePair("PASS",pass));
            postparameters2send.add(new BasicNameValuePair("IMEI", ""));
            postparameters2send.add(new BasicNameValuePair("NOMBREAPP" ,""));
            postparameters2send.add(new BasicNameValuePair("APPVERSION",""));
            postparameters2send.add(new BasicNameValuePair("DBVERSION", "Integer.toString(dbhelper.DATABASE_VERSION)"));

            ContentValues values = new ContentValues();
            values.put("USERDOMINIO", dominio);
            values.put("USER", user);

            JSONArray datosjs = post.getserverdata(postparameters2send,"url");

            //JSONArray datosjs = post.getserverdata(values,"url");
            Log.d(" Informe JSONArray", "Valor que contiene JSONArray los datos: " + datosjs);
            Log.d(" Informe JSONArray", "Valor que contiene POST: " + postparameters2send);
            if (datosjs != null && datosjs.length() > 0) {

                for(int i=0;i<datosjs.length();i++){
                    JSONObject e = datosjs.getJSONObject(i);
/*
                    db = dbhelper.getReadableDatabase();
                    db.execSQL("INSERT INTO "+dbhelper.TABLE_ADMINITRACION+" VALUES ("
                            +""+e.getString("usuCombId")  	     +"  , " //
                            +"'"+e.getString("nombreUsuario")    +"' , " //
                            +"'"+pass         					 +"' , " //
                            +"'"+user       					 +"' , " //
                            +"'"+e.getString("userName")  	     +"' , " //
                            +"'"+e.getString("nivelAcceso")  	 +"'   " //
                            +")");

                    //Config.key_EmprId = e.getString("EmprId");
                    //Config.key_UsuId_ = e.getString("USERID");
                    db.close();
                    */

                }
                return true;
            }else{
                return false;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }
}