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

public class POSTService extends IntentService {

    public static final String POST_SERVICE_FINISHED = "com.mireal.POST_SERVICE_FINISHED";

    public POSTService(String name) {
        super(name);
    }

    @SuppressWarnings("unused")
    public POSTService() {
        super("POSTService");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        String pseudo = intent.getStringExtra("pseudo");
        Log.e("JSON", pseudo);
        String mail = intent.getStringExtra("mail");
        String contenu = intent.getStringExtra("contenu");
        postMessageToServer(pseudo,mail,contenu);
        // chargement terminé
        intent.setAction(POST_SERVICE_FINISHED);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getApplicationContext().sendBroadcast(intent);
    }

    public static void launch(Context context, String pseudo, String mail, String contenu) {
        Log.v(MainActivity.TAG, "POSTService launched");
        Intent serviceIntent = new Intent(context, POSTService.class);
        Log.e("JSON", pseudo);
        serviceIntent.putExtra("pseudo", pseudo);
        serviceIntent.putExtra("mail", mail);
        serviceIntent.putExtra("contenu", contenu);
        context.startService(serviceIntent);
    }

    public static void postMessageToServer(String pseudoMessage, String mailMessage, String contenu){

        try {

            URL url = new URL("http://172.16.26.41:8080/MirealWS/api/message/");

            JSONObject message = new JSONObject();

            //Log.e("JSON", pseudoMessage.toString());

            message.put("pseudomessage", pseudoMessage);
            message.put("mailmessage", mailMessage);
            message.put("contenu", contenu);

            Log.e("JSON", message.toString());

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);

            connection.setRequestMethod("POST");

            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setFixedLengthStreamingMode(message.toString().getBytes().length);

            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            connection.connect();

            OutputStream os = new BufferedOutputStream(connection.getOutputStream());
            os.write(message.toString().getBytes());

            os.flush();

        }catch (MalformedURLException e){

        }catch (JSONException e){

        }catch (IOException e){
            e.printStackTrace();

        }

    }

}
