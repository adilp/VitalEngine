package com.adilpatel.vitalengine.FragmentPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.adilpatel.vitalengine.ListAdapters.CustomAdapterAll;
import com.adilpatel.vitalengine.MessageData;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;



public class allFragment extends Fragment {

    private String currentUserId;
    private ArrayAdapter namesArrayAdapter;
    //private ArrayList<String> names;
    private ListView usersListView;
    String names[] = {"Anant Kharod, MD", "Mustafa Ahmed, MD"};
    String msg[] = {"What time do you want to get started adding more stuff go over the line", "Presentation is tomorrow"};
    boolean readUnread [] = {false,false};
    public static int [] images={R.drawable.msgone,R.drawable.msgtwo};


    public allFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_all, container, false);
        usersListView = (ListView) rootView.findViewById(R.id.allListView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, names);





        ArrayList<MessageData> arrMessageData = new ArrayList<MessageData>();

        MessageData msg1 = new MessageData();
        msg1.setName("ALL");
        msg1.setMessage("Mustafa Ahmed: What time do you want to get started adding more stuff go over the line");
        msg1.setImage(R.drawable.msgone);
        msg1.setRead(true);
        msg1.setSubject("Test Subject");
        msg1.setType("referral");


        MessageData msg2 = new MessageData();
        msg2.setName("Group 1");
        msg2.setMessage("Adil Patel: Presentation is tomorrow adding");
        msg2.setImage(R.drawable.group);
        msg2.setType("conversation");
      // msg2.setSubject("Subject 2");

       MessageData msg3 = new MessageData();
        msg3.setName("Adil Patel");
        msg3.setMessage("Presentation is tomorrow");
        msg3.setImage(R.drawable.msgthree);
        msg3.setType("message");
        //msg2.setSubject("Subject 2");


        arrMessageData.add(msg1);
        arrMessageData.add(msg2);
        arrMessageData.add(msg3);

        CustomAdapterAll adapter = new CustomAdapterAll(getActivity().getBaseContext(), arrMessageData);
        usersListView.setAdapter(adapter);


        return rootView;

    }

}