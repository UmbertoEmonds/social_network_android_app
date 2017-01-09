package com.mireal.admin.mireal7.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mireal.admin.mireal7.MainActivity;
import com.mireal.admin.mireal7.model.Message;
import com.mireal.admin.mireal7.model.Utilisateur;

import java.util.ArrayList;

/**
 * Created by admin on 04/01/2017.
 */

public class MainActivityBroadcastReceiver extends BroadcastReceiver {

    private MainActivity mainActivity;

    public MainActivityBroadcastReceiver(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(BackgroundService.UTILISATEUR_SERVICE_FINISHED)) {

            Utilisateur utilisateur = (Utilisateur)intent.getSerializableExtra("utilisateurFromServer");
            ArrayList<Message> messagesFromServer = (ArrayList<Message>) intent.getSerializableExtra("messagesFromServer");

            mainActivity.onUtilisateurServiceFinished(utilisateur, messagesFromServer);

        }

        if (intent.getAction().equals(POSTService.POST_SERVICE_FINISHED)) {

            mainActivity.onMessagePOSTServiceFinished();
        }

        if(intent.getAction().equals(PUTService.PUT_SERVICE_FINISHED)){

            mainActivity.onPutLikeServiceFinished();

        }

    }
}
