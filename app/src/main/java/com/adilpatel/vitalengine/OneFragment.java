package com.adilpatel.vitalengine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class OneFragment extends Fragment{

    private String currentUserId;
    private ArrayAdapter namesArrayAdapter;
    //private ArrayList<String> names;
    private ListView usersListView;
    String names[] = {"Anant Kharod, MD", "Mustafa Ahmed, MD"};
    String msg[] = {"What time do you want to get started adding more stuff go over the line", "Presentation is tomorrow"};
    boolean readUnread [] = {false,false};
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

        ArrayList<MessageData> arrMessageData = new ArrayList<OneFragment.MessageData>();

        MessageData msg1 = new MessageData();
        msg1.setName("Anant Kharod, MD");
        msg1.setMessage("What time do you want to get started adding more stuff go over the line");
        msg1.setImage(R.drawable.msgone);

        MessageData msg2 = new MessageData();
        msg2.setName("Mustafa Ahmed, MD");
        msg2.setMessage("Presentation is tomorrow");
        msg2.setImage(R.drawable.msgtwo);
        msg2.setRead(true);

        arrMessageData.add(msg1);
        arrMessageData.add(msg2);

        CustomAdapterMessages adapter = new CustomAdapterMessages(getActivity().getBaseContext(), arrMessageData);
        usersListView.setAdapter(adapter);


        return rootView;

    }



        public class MessageData {

            String message = "";
            boolean isRead = false;
            int images;
            String name = "";

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public boolean isRead() {
                return isRead;
            }

            public void setRead(boolean isRead) {
                this.isRead = isRead;
            }

            public void setImage (int images){
                this.images = images;
            }

            public int getImage(){
                return images;
            }

            public String getName(){
                return name;
            }
            public void setName(String name){
                this.name = name;
            }
        }



}