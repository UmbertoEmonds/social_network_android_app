package com.mireal.admin.mireal7.controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mireal.admin.mireal7.MainActivity;
import com.mireal.admin.mireal7.model.Message;
import com.mireal.admin.mireal7.model.Utilisateur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by admin on 04/01/2017.
 */

public class BackgroundService extends IntentService {

    public static final String UTILISATEUR_SERVICE_FINISHED = "com.mireal.UTILISATEUR_SERVICE_FINISHED";

    @SuppressWarnings("unused")
    public BackgroundService() {
        super("BackgroundService");
    }

    public BackgroundService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Utilisateur utilisateur = getUserFromServer();
        ArrayList<Message> messagesFromServer = getMessagesFromServer();

        // chargement terminé
        intent = new Intent();
        intent.setAction(UTILISATEUR_SERVICE_FINISHED);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra("utilisateurFromServer", utilisateur);
        intent.putExtra("messagesFromServer", messagesFromServer);


        getApplicationContext().sendBroadcast(intent);

    }

    public static void launch(Context context) {
        Log.v(MainActivity.TAG, "BackgroundService launched");
        Intent serviceIntent = new Intent(context, BackgroundService.class);
        context.startService(serviceIntent);
    }


    // -- UTILISATEUR --

    public static Utilisateur getUserFromServer(){

        Utilisateur utilisateur = null;

        String myURL = "http://172.16.26.41:8080/MirealWS/api/utilisateur/bibi@hotmail.fr/"; //changer le @
        String result = "";

        try {

            URL url = new URL(myURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if(connection.getResponseCode()==200){

                InputStream inputStream = connection.getInputStream();

                result = InputStreamOperations.InputStreamToString(inputStream); // conversion du resultat en chaine de caractere

                JSONObject obj = new JSONObject(result);

                utilisateur = new Utilisateur(obj.getInt("idt"), obj.getString("mail"), obj.getString("pseudo"), obj.getString("description"));

            }

        }catch (MalformedURLException e){

            Log.e(MainActivity.TAG, "Erreur dans l'URL");
            e.getMessage();

        }catch (IOException e){

            Log.e(MainActivity.TAG, "Erreur lors de la connection au serveur de l'utilisateur\n" + e.getMessage());
            e.printStackTrace();


        } catch (JSONException e){

            Log.e(MainActivity.TAG, "Erreur dans le JSON");
            e.getMessage();

        }

        return utilisateur;

    }

    public static void putUtilisateurToServer(String pseudoMessage, String mailMessage, String contenu){

        try {

            URL url = new URL("http://172.16.23.80:8080/MirealWS/api/message/");

            JSONObject message = new JSONObject();

            message.put("pseudomessage", pseudoMessage);
            message.put("mailmessage", mailMessage);
            message.put("contenu", contenu);

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

        }

    }


    // -- MESSAGE --


    public static ArrayList<Message> getMessagesFromServer(){

        ArrayList<Message> messages = new ArrayList<>();

        String myURL = "http://172.16.26.41:8080/MirealWS/api/message/";
        String result = "";

        try {

            URL url = new URL(myURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if(connection.getResponseCode()==200){

                InputStream inputStream = connection.getInputStream();

                result = InputStreamOperations.InputStreamToString(inputStream); // conversion du resultat en chaine de caractere

                JSONArray array = new JSONArray(result); // root

                for (int i = 0; i < array.length(); i++) {

                    JSONObject obj = new JSONObject(array.getString(i));

                    Message message = new Message(obj.getInt("idt"), obj.getString("mailmessage"), obj.getString("pseudomessage"), obj.getString("contenu"), obj.getInt("nbaime"), obj.getString("date"));

                    messages.add(message);

                }

            }

        }catch (MalformedURLException e){

            Log.e(MainActivity.TAG, "Erreur dans l'URL");

        }catch (IOException e){

            Log.e(MainActivity.TAG, "Erreur lors de la connection au serveur des messages");
            e.printStackTrace();

        } catch (JSONException e){

            Log.e(MainActivity.TAG, "Erreur dans le JSON");

        }

        return messages;

    }

}
