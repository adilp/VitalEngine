package com.adilpatel.vitalengine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class OneFragment extends Fragment{

    private String currentUserId;
    private ArrayAdapter namesArrayAdapter;
    //private ArrayList<String> names;
    private ListView usersListView;
    String names[] = {"Anant Kharod, MD", "Mustafa Ahmed, MD"};
    String msg[] = {"What time do you want to get started adding more stuff go over the line", "Presentation is tomorrow"};
    boolean readUnread [] = {true,false};
    public static int [] images={R.drawable.msgone,R.drawable.msgtwo};


    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_one, container, false);
        usersListView = (ListView) rootView.findViewById(R.id.usersListView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, names);
        CustomAdapterMessages adapter = new CustomAdapterMessages(getActivity().getBaseContext(), images, names, msg);
        
        usersListView.setAdapter(adapter);


        return rootView;

    }

}