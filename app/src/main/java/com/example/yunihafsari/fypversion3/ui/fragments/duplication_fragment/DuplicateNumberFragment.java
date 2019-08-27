package com.example.yunihafsari.fypversion3.ui.fragments.duplication_fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.apps_model.Contact;
import com.example.yunihafsari.fypversion3.sqlite.database.DBAdapter;
import com.example.yunihafsari.fypversion3.ui.adapters.DuplicateNameAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class DuplicateNumberFragment extends Fragment {

    private ArrayList<Contact> contacts = new ArrayList<>();
    private ArrayList<Contact> duplication_result = new ArrayList<>();

    private RecyclerView recyclerView;
    private DuplicateNameAdapter duplicateNameAdapter;
    private TextView textView;
    private FloatingActionButton floatingActionButton;

    public DuplicateNumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_duplicate_number, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.duplicate_number_recyler_view);
        textView = (TextView) v.findViewById(R.id.no_duplication_name);
        floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fab_number);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // set properties of recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        retrieveFromDatabase();

        DuplicateNumberAlgorithm();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete to database
                for(Contact contact : duplicateNameAdapter.getCheckedContact()){
                    Log.i("COBAAAA","duplicate id "+contact.getId());
                    delete(contact.getId());

                    Toast.makeText(getContext(),"Successfully deleted duplicate number",Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private void retrieveFromDatabase() {
        DBAdapter db = new DBAdapter(getActivity());

        // open
        db.openDB();

        contacts.clear();

        // get friends from db
        Cursor c = db.getAllFriends();

        // loop thru data then adding to arraylist
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            String email = c.getString(2);
            String phone_number = c.getString(3);
            String relation = c.getString(4);
            String address = c.getString(5);
            String twitter_name = c.getString(6);
            String instagram_name = c.getString(7);
            byte[] image = c.getBlob(9);


            // create contact
            Contact contact = new Contact(id, name, phone_number, email, relation, address, twitter_name, instagram_name, image);

            // add to array list
            contacts.add(contact);


        }
    }

    private void DuplicateNumberAlgorithm(){

        // step 1 : (make duplicate array list)
        ArrayList<Contact> duplicate_number = new ArrayList<>();
        for(Contact contact : contacts){
            duplicate_number.add(contact);
            Log.i("testing","name "+contact.getName());
        }

        // step 2 : (duplication algorithm)
        ArrayList<Contact> result_again = new ArrayList<>();
        Set<String> names = new HashSet<String>();
        for(Contact user : contacts){
            if(names.add(user.getPhone_number())){
                result_again.add(user);
            }
        }

        for(int i=0; i<result_again.size(); i++){
            int duplication = 0;
            for(int j=0; j<duplicate_number.size(); j++){


                if(result_again.get(i).getPhone_number().equals(duplicate_number.get(j).getPhone_number())){
                    duplication++;

                    if(duplication>=2){
                        // public Contact(int id, String name, String phone_number, String email

                        Contact object_result = new Contact(duplicate_number.get(j).getId(), duplicate_number.get(j).getName(), duplicate_number.get(j).getPhone_number(), duplicate_number.get(j).getEmail());
                        duplication_result.add(object_result);

                    }
                }
            }
        }

        for(Contact user : result_again){
            if(checkDuplication(user.getPhone_number())){
                duplication_result.add(user);
            }
        }

        // this is for testing purpose
        for(int i=0; i<duplication_result.size(); i++){
            Log.i("duplicate number","name "+duplication_result.get(i).getName());
            Log.i("duplicate number","phone number "+duplication_result.get(i).getPhone_number());
            Log.i("duplicate number","id "+duplication_result.get(i).getId());
        }

        final ArrayList<Contact> deleted_one = new ArrayList<>();
        // step 3 : (send the result to UI)
        duplicateNameAdapter = new DuplicateNameAdapter(duplication_result);

        if(!(duplication_result.size()<1)){
            recyclerView.setAdapter(duplicateNameAdapter);
        }else{
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void delete(int id){
        DBAdapter db = new DBAdapter(getContext());
        db.openDB();
        long result = db.delete(id);

        db.closeDB();
    }

    private boolean checkDuplication(String number){
        int duplication = 0;
        for(Contact user : contacts){
            if(user.getPhone_number().equals(number)) {
                duplication++;
            }


        }

        if(duplication>=2){
            return true;
        }else{
            return false;
        }
    }
}
