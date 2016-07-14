package com.adilpatel.vitalengine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * A placeholder fragment containing a simple view.
 */
public class newRefMainActivityFragment extends Fragment {

    private nextScreenCallback callback;
    public interface nextScreenCallback{
        public void nextButtonClicked(int flag);
    }
    ImageView allImage;
    Button next;

    public newRefMainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_ref_main, container, false);

        allImage = (ImageView) view.findViewById(R.id.newRefPatientPic);

        allImage.setImageResource(R.drawable.msgone);

        next = (Button) view.findViewById(R.id.btn_next);

        next.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                callback.nextButtonClicked(1);
            }

        });

        return view;



    }



}
