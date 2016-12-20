package com.example.sgdjibo.hello;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends Activity {
    EditText champs;
    TextView etat,temps, temperatureMatin, temperatureAp, vitesse, niveau;
    Button valider;
    String etatString,tempsString, temperatureMatinString, temperatureApString, vitesseString, niveauString;
    ProgressDialog progress;
    Switch mySwitch;
    Resources res;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        champs= (EditText)findViewById(R.id.champs_id);
        etat=(TextView)findViewById(R.id.etat_id);
        temps =(TextView)findViewById(R.id.temps_id);
        temperatureMatin =(TextView)findViewById(R.id.temperature_matin);
        temperatureAp=(TextView)findViewById(R.id.temperature_ap_id);
        vitesse = (TextView)findViewById(R.id.vitesse_id);
        niveau = (TextView)findViewById(R.id.niveau_id);
        valider= (Button)findViewById(R.id.valider_id);
        mySwitch=(Switch) findViewById(R.id.switch_id);
        mySwitch.setChecked(true);
        res = getResources();

            etatString = etat.getText().toString();
            tempsString = temps.getText().toString();
            temperatureMatinString = temperatureMatin.getText().toString();
            temperatureApString = temperatureAp.getText().toString();
            vitesseString = vitesse.getText().toString();
            niveauString = niveau.getText().toString();


        sharedpreferences = getSharedPreferences("tmp", Context.MODE_PRIVATE);
        if(sharedpreferences.getString("nameStation",null)!=null)
champs.setText(sharedpreferences.getString("nameStation",null));

    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putString("etat", etatString);
        outState.putString("temps",tempsString );
        outState.putString("temperatureMatin", temperatureMatinString );
        outState.putString("temperatureAp", temperatureApString);
        outState.putString("vitesse", vitesseString);
        outState.putString("niveau", niveauString);
       }
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if( savedInstanceState != null ) {
            etat.setText(savedInstanceState.getString("etat"));
            temps.setText(savedInstanceState.getString("temps"));
            temperatureMatin.setText( savedInstanceState.getString("temperatureMatin"));
            temperatureAp.setText(savedInstanceState.getString("temperatureAp"));
            vitesse.setText(savedInstanceState.getString("vitesse"));
            niveau.setText(  savedInstanceState.getString("niveau"));
        }


    }
    ProgressDialog getProgress()
    {
        return progress;
    }
    Switch getMySwitch()
    {
       return mySwitch;
    }

public void buttonClicked(View v)
    {
        etat.setText(res.getString(R.string.etatString));
        temps.setText(res.getString(R.string.tempsString));
        temperatureMatin.setText(res.getString(R.string.temperatureMatinString));
        temperatureAp.setText(res.getString(R.string.temperatureApString)  );
        vitesse.setText(res.getString(R.string.vitesseString));
        niveau.setText( res.getString(R.string.niveauString));
        URL url= null;
        try {
            url = new URL("http://snowlabri.appspot.com/snow?station=gourette");
            new RequestHttp(this).execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Context context = getApplicationContext();
            CharSequence text = "Connexion echouée: URL incorrect!";
            int duration = Toast.LENGTH_SHORT;


            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("nameStation",champs.getText().toString());
        editor.commit();
    }
    public void localiserClicked(View v)
    {


        String requete ="geo:0,0?q="+champs.getText().toString();
        Intent intent;
        intent= new Intent(Intent.ACTION_VIEW, Uri.parse(requete));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("nameStation",champs.getText().toString());
        editor.commit();
 }
    public void selectClicked(View v)
    {
        Intent i = new Intent(this,StationListActivity.class);
        startActivityForResult(i, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK)
            {
                String result=data.getStringExtra("result");
                champs.setText(result);
            }

        }
    }
    public void    updateWidgets(String result)
    {
        try {
            JSONObject jo =new JSONObject(result);
            etat.setText(res.getString(R.string.etatString)+" "+jo.getString("ouverte"));
            etatString=res.getString(R.string.etatString)+" "+jo.getString("ouverte");

            temps.setText(res.getString(R.string.tempsString)+" "+jo.getString("temps"));
            tempsString=res.getString(R.string.tempsString)+" "+jo.getString("temps");

            temperatureMatin.setText(res.getString(R.string.temperatureMatinString)+" "+jo.getString("temperatureMatin"));
            temperatureMatinString=res.getString(R.string.temperatureMatinString)+" "+jo.getString("temperatureMatin");

            temperatureAp.setText(res.getString(R.string.temperatureApString)+" "+jo.getString("temperatureMidi"));
            temperatureApString="Temperature après midi "+jo.getString("temperatureMidi");

            vitesse.setText(res.getString(R.string.vitesseString)+" "+jo.getString("vent"));
            vitesseString=res.getString(R.string.vitesseString)+" "+jo.getString("vent");

            niveau.setText( res.getString(R.string.niveauString)+" "+jo.getString("neige"));
            niveauString= res.getString(R.string.niveauString)+" "+jo.getString("neige");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}



