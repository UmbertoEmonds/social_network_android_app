package com.mireal.admin.mireal7.vue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mireal.admin.mireal7.R;
import com.mireal.admin.mireal7.controller.BackgroundService;
import com.mireal.admin.mireal7.controller.MesssageAdapter;
import com.mireal.admin.mireal7.controller.POSTService;
import com.mireal.admin.mireal7.model.Message;
import com.mireal.admin.mireal7.model.Utilisateur;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by admin on 04/01/2017.
 */

public class QuoiDeNeuf_Fragment extends Fragment {

    private TextView pseudoTV;
    private EditText messageET;
    private ImageView envoyerIV;
    private ListView listeMessageLV;
    public MesssageAdapter adapter;
    private ArrayList<Message> messages;
    private Utilisateur user;
    private TextView nbAimeTV;
    private int idMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quoi_de_neuf_fragment, container, false);

        Bundle args = getArguments();

        messages = (ArrayList<Message>)args.getSerializable("messages");

        user = (Utilisateur)args.getSerializable("utilisateur");

        pseudoTV = (TextView)view.findViewById(R.id.pseudoQuoiDeNeuf);
        messageET = (EditText)view.findViewById(R.id.messageET);
        envoyerIV = (ImageView)view.findViewById(R.id.envoyerMessage);
        listeMessageLV = (ListView)view.findViewById(R.id.listeMessage);
        nbAimeTV = (TextView)view.findViewById(R.id.nbAime);

        final Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade);

        adapter = new MesssageAdapter(getActivity(),this);

        addToAdapter(messages);

        listeMessageLV.setAdapter(adapter);

        messageET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(b == true){
                    messageET.setText("");
                }else{
                    messageET.setText("écrivez ici...");
                }

            }
        });

        listeMessageLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.v("AZERTY", "ID LISTE: " + i);

                setIdMessage(messages.get(i).getIdt_message());

                adapter.notifyDataSetChanged();

                return false;
            }
        });

        envoyerIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!messageET.getText().toString().trim().isEmpty()){

                    if (messageET.getText().toString().equals("écrivez ici...")){

                    }else{
                        envoyerIV.startAnimation(fadeInAnimation);
                        String pseudo = user.getPseudo();
                        String mail = user.getMail_utilisateur();
                        String message = messageET.getText().toString();
                        POSTService.launch(getContext(),pseudo,mail,message);
                        messageET.setText("");
                        adapter.clear();
                        BackgroundService.launch(getContext());
                        listeMessageLV.startAnimation(fadeInAnimation);
                        addToAdapter(messages);
                    }

                }

            }
        });

        pseudoTV.setText(user.getPseudo());

        return view;
    }

    public void addToAdapter(ArrayList<Message> messages){
        Collections.sort(messages);
        for (int i = 0; i<messages.size(); i++){
            adapter.add(messages.get(i));
        }

    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

}
