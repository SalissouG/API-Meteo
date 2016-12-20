package com.example.sgdjibo.hello;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sgdjibo on 09/11/15.
 */
public class RequestHttp extends  AsyncTask<URL,Integer,String>{

    MainActivity mainActivity;
    ProgressDialog progress;
public  RequestHttp(MainActivity mainActivity)
{
   this.mainActivity=mainActivity;
    progress=mainActivity.getProgress();
}


    @Override
    protected String doInBackground(URL... params) {

        String res=null;
        try {
            HttpURLConnection urlConnection = (HttpURLConnection)params[0].openConnection();
            InputStream in= new BufferedInputStream(urlConnection.getInputStream());
            res=readStream(in);
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }
    protected void onPreExecute() {

        progress = new ProgressDialog(mainActivity);
        progress.setMessage("Connexion...");
        progress.show();
    }

    protected void onPostExecute(String res)
    {

        mainActivity.updateWidgets(res);
        if (progress.isShowing()) {
            progress.dismiss();
        }

        if(mainActivity.getMySwitch().isChecked()) {

        Vibrator vib=(Vibrator)mainActivity.getSystemService(Context.VIBRATOR_SERVICE);
            vib.vibrate(1000);
            Log.i("vibre","vibration");
        }



    }




    public static String readStream(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }


}