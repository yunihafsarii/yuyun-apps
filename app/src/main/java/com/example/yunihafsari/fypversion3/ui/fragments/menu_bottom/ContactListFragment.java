package com.example.yunihafsari.fypversion3.ui.fragments.menu_bottom;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.apps_model.Contact;
import com.example.yunihafsari.fypversion3.sqlite.adapter.MyAdapter;
import com.example.yunihafsari.fypversion3.sqlite.database.DBAdapter;
import com.example.yunihafsari.fypversion3.ui.activities.ContactDetailActivity;
import com.example.yunihafsari.fypversion3.ui.adapters.ClickListener;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.SharedPrefUtil;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends Fragment {

    // in main activity
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<Contact> contacts = new ArrayList<>();



    public ContactListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_contact_list, container, false);

        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.myRecycler);


        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // set properties of recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                startActivity(new Intent(getContext(), ContactDetailActivity.class));

                // put the chosen list in sharedpreference
                SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getContext());
                sharedPrefUtil.saveInt(Constants.CONTACT_ID, contacts.get(position).getId());
                sharedPrefUtil.saveString(Constants.CONTACT_NAME, contacts.get(position).getName());
                sharedPrefUtil.saveString(Constants.CONTACT_PHONE_NUMBER, contacts.get(position).getPhone_number());
                sharedPrefUtil.saveString(Constants.CONTACT_EMAIL, contacts.get(position).getEmail());
                sharedPrefUtil.saveString(Constants.CONTACT_RELATION, contacts.get(position).getRelation());
                sharedPrefUtil.saveString(Constants.CONTACT_ADDRESS, contacts.get(position).getAddress());
                sharedPrefUtil.saveString(Constants.CONTACT_TWITTER_USERNAME, contacts.get(position).getTwitter_username());
                sharedPrefUtil.saveString(Constants.CONTACT_INSTAGRAM_USERNAME, contacts.get(position).getInstagram_username());
            }

            @Override
            public void onLongClick(View view, final int position) {

                DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                // delete the contact
                                delete(contacts.get(position).getId());
                                // set arraylist to adapter
                                myAdapter = new MyAdapter(getActivity(), contacts);

                                // set database result to arraylist
                                // set adapter to recyler view
                                retrieveFromDatabase();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                // do nothing
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure want to delete this contact?")
                        .setPositiveButton("Yes", dialogListener)
                        .setNegativeButton("No", dialogListener).show();
            }
        }));

        /*
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
        recyclerView, new ClickListener() {
    @Override
    public void onClick(View view, final int position) {
        //Values are passing to activity & to fragment as well
        Toast.makeText(MainActivity.this, "Single Click on position        :"+position,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick(View view, int position) {
        Toast.makeText(MainActivity.this, "Long press on position :"+position,
                Toast.LENGTH_LONG).show();
    }
}));
         */

        // set arraylist to adapter
        myAdapter = new MyAdapter(getActivity(), contacts);

        // set database result to arraylist
        // set adapter to recyler view
        retrieveFromDatabase();
    }

    private void retrieveFromDatabase(){
        DBAdapter db = new DBAdapter(getActivity());

        // open
        db.openDB();

        contacts.clear();

        // get friends from db
        Cursor c = db.getAllFriends();

        // loop thru data then adding to arraylist
        while(c.moveToNext()){
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
            Contact contact = new Contact(id, name, phone_number,email, relation,address,twitter_name,instagram_name, image );

            // add to array list
            contacts.add(contact);
        }

        //Log.i("TESTING", " phone number "+contacts.get(0).getPhone_number());
        //Log.i("TESTING", " address "+contacts.get(0).getAddress());

        // set adapter to recycler view
        if(!(contacts.size()<1)){
            recyclerView.setAdapter(myAdapter);
        }



        db.closeDB();
    }


    private void delete(int id){
        DBAdapter db = new DBAdapter(getContext());
        db.openDB();
        long result = db.delete(id);

        db.closeDB();
    }


    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
