package com.mireal.admin.mireal7.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mireal.admin.mireal7.R;
import com.mireal.admin.mireal7.model.Message;
import com.mireal.admin.mireal7.vue.QuoiDeNeuf_Fragment;

/**
 * Created by admin on 04/01/2017.
 */

public class MesssageAdapter extends ArrayAdapter<Message> {

    private QuoiDeNeuf_Fragment quoiDeNeuf_fragment;
    private LinearLayout likeLL;
    private LinearLayout deleteLL;
    private int idMessage;

    public MesssageAdapter(Context context, QuoiDeNeuf_Fragment quoiDeNeuf_fragment) {
        super(context, 0);
        this.quoiDeNeuf_fragment = quoiDeNeuf_fragment;
    }

    FrameLayout optionsFL;

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v==null){

            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.message_line_layout, null);

        }

        Message message = getItem(position);

        final Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade);
        final Animation fadeOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout);

        TextView pseudo_tv = (TextView)v.findViewById(R.id.pseudo_line);
        TextView message_tv = (TextView)v.findViewById(R.id.message_line);
        TextView date_tv = (TextView)v.findViewById(R.id.date);
        TextView nbAime_tv = (TextView)v.findViewById(R.id.nbAime);
        optionsFL = (FrameLayout)v.findViewById(R.id.options_msg);
        likeLL = (LinearLayout)v.findViewById(R.id.Like);

        pseudo_tv.setText(message.getPseudo_message());
        message_tv.setText(message.getContenu());
        date_tv.setText(message.getDate());
        nbAime_tv.setText(String.valueOf(message.getNbAime()));

        likeLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                optionsFL.setVisibility(View.GONE);
                likeLL.startAnimation(fadeInAnimation);
                idMessage = quoiDeNeuf_fragment.getIdMessage();
                PUTService.launch(getContext(),idMessage);
                BackgroundService.launch(getContext());

            }
        });

        if (message.getIdt_message() == quoiDeNeuf_fragment.getIdMessage())
        {
            optionsFL.setVisibility(View.VISIBLE);
        }
        else
        {
            optionsFL.setVisibility(View.GONE);
        }

        return v;
    }

}
