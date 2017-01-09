package com.mireal.admin.mireal7.vue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mireal.admin.mireal7.R;

/**
 * Created by admin on 04/01/2017.
 */

public class MonCompte_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        return inflater.inflate(R.layout.mon_compte_fragment, container, false);
    }
}
