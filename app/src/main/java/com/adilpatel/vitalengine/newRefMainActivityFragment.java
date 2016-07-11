package com.adilpatel.vitalengine;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A placeholder fragment containing a simple view.
 */
public class newRefMainActivityFragment extends Fragment {

    ImageView allImage;

    public newRefMainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_ref_main, container, false);

        allImage = (ImageView) view.findViewById(R.id.newRefPatientPic);

        allImage.setImageResource(R.drawable.msgone);

        return view;



    }



}
