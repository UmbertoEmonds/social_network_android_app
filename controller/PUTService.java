package com.mireal.admin.mireal7.controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mireal.admin.mireal7.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by admin on 06/01/2017.
 */

public class PUTService extends IntentService {

    public static final String PUT_SERVICE_FINISHED = "com.mireal.PUT_SERVICE_FINISHED";

    public PUTService(String name) {
        super(name);
    }

    @SuppressWarnings("unused")
    public PUTService() {
        super("PUTService");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        int id = intent.getIntExtra("nbAime", -1);
        Log.e("JSON", String.valueOf(id));
        putMessageToServer(id);

        // chargement terminé
        intent.setAction(PUT_SERVICE_FINISHED);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getApplicationContext().sendBroadcast(intent);
    }

    public static void launch(Context context, int id) {
        Log.v(MainActivity.TAG, "PUTService launched");
        Intent serviceIntent = new Intent(context, PUTService.class);
        Log.e("JSON", String.valueOf(id));
        serviceIntent.putExtra("nbAime", id);
        context.startService(serviceIntent);
    }

    public static void putMessageToServer(int id){

        try {

            URL url = new URL("http://172.16.26.41:8080/MirealWS/api/message/" + id);

            JSONObject message = new JSONObject();

            message.put("id", String.valueOf(id));

            Log.e("JSON", message.toString());

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);

            connection.setRequestMethod("PUT");

            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setFixedLengthStreamingMode(message.toString().getBytes().length);

            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            connection.connect();

            OutputStream os = new BufferedOutputStream(connection.getOutputStream());
            os.write(message.toString().getBytes());

            os.flush();

        }catch (MalformedURLException e){

        }catch (IOException e){
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
