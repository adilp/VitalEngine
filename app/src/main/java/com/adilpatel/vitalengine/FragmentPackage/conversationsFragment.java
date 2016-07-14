package com.adilpatel.vitalengine.FragmentPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.adilpatel.vitalengine.ListAdapters.CustomAdapterConversations;
import com.adilpatel.vitalengine.MessageData;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;


public class conversationsFragment extends Fragment {
    private String currentUserId;
    private ArrayAdapter namesArrayAdapter;
    //private ArrayList<String> names;
    private ListView usersListView;
    String names[] = {"Anant Kharod, MD", "Mustafa Ahmed, MD"};
    String msg[] = {"What time do you want to get started adding more stuff go over the line", "Presentation is tomorrow"};
    boolean readUnread [] = {false,false};
    public static int [] images={R.drawable.msgone,R.drawable.msgtwo};


    public conversationsFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_conversations, container, false);
        usersListView = (ListView) rootView.findViewById(R.id.conversationsListView);


        ArrayList<MessageData> arrMessageData = new ArrayList<MessageData>();
//
        MessageData msg1 = new MessageData();
        msg1.setName("Group 1");
        msg1.setMessage("Mustafa Ahmed: What time do you want to get started adding more stuff go over the line");
        msg1.setImage(R.drawable.group);
        msg1.setRead(true);
        //msg1.setSubject("Test Subject 1");

        MessageData msg2 = new MessageData();
        msg2.setName("Group 2");
        msg2.setMessage("Anant Kharod: Presentation is tomorrow");
        msg2.setImage(R.drawable.group);
        //msg2.setSubject("Subject 2");


        arrMessageData.add(msg1);
        arrMessageData.add(msg2);

        CustomAdapterConversations adapter = new CustomAdapterConversations(getActivity().getBaseContext(), arrMessageData);
        usersListView.setAdapter(adapter);


        return rootView;

    }







}
