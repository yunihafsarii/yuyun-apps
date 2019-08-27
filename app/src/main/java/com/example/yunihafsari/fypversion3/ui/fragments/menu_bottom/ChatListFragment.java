package com.example.yunihafsari.fypversion3.ui.fragments.menu_bottom;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.firebase.users.get.GetUserContract;
import com.example.yunihafsari.fypversion3.firebase.users.get.GetUsersPresenter;
import com.example.yunihafsari.fypversion3.model.apps_model.User;
import com.example.yunihafsari.fypversion3.sqlite.database.DBAdapter;
import com.example.yunihafsari.fypversion3.ui.activities.ChatActivity;
import com.example.yunihafsari.fypversion3.ui.adapters.UserListingRecyclerAdapter;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.ItemClickSupport;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment implements GetUserContract.View, SwipeRefreshLayout.OnRefreshListener, ItemClickSupport.OnItemClickListener{

    public static final String ARGS_TYPE = "type";
    public static final String TYPE_ALL = "type_all";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private UserListingRecyclerAdapter userListingRecyclerAdapter;

    private GetUsersPresenter getUsersPresenter;

    private ArrayList<User> filtered_users = new ArrayList<>();



    public static ChatListFragment newInstance(String type){
        Bundle args = new Bundle();
        args.putString(ARGS_TYPE, type);
        ChatListFragment usersFragment = new ChatListFragment();
        usersFragment.setArguments(args);
        return usersFragment;
    }

    public ChatListFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_chat_list, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_view_all_user_listing);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getUsersPresenter = new GetUsersPresenter(this);
        getUsers();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        getUsers();
    }

    private void getUsers(){

        if(TextUtils.equals(getArguments().getString(ARGS_TYPE), TYPE_ALL)){
            getUsersPresenter.getAllUsers();
        }

        //getUsersPresenter.getAllUsers();
    }

    @Override
    public void onGetAllUsersSuccess(final List<User> users) {
        Log.i("hakimmmmmmmm","this is from view.success");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });




        checkFriendWhoInstalledTheApps(users); // here is where i get filtered_users


        // start identify key people

        final ArrayList<User> sorted_users = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Constants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Map<String, Long> map = new Hashtable<String, Long>();


                for(User user : filtered_users){
                    if(dataSnapshot.hasChild( FirebaseAuth.getInstance().getCurrentUser().getUid()+"_"+user.uid) ){
                        //dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+"_"+user.uid).getChildrenCount();
                        Log.i("DEBUGGING_KEY_PEOPLE", user.email+" get this much chat "+dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+"_"+user.uid).getChildrenCount());
                        map.put(user.email, dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+"_"+user.uid).getChildrenCount());
                    }
                }


                List<Map.Entry<String, Long>> list = new LinkedList<Map.Entry<String, Long>>(map.entrySet());
                Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
                    @Override
                    public int compare(Map.Entry<String, Long> lhs, Map.Entry<String, Long> rhs) {
                        return rhs.getValue().compareTo(lhs.getValue());
                    }
                });



                for(Map.Entry<String, Long> item : list){
                    Log.i("DEBUGGING_SORTED",""+item.getKey()+" "+item.getValue());
                }


                for(Map.Entry<String, Long> item : list){
                    for(User user : filtered_users){
                        if(item.getKey().equals(user.email)){
                            sorted_users.add(user);
                        }
                    }
                }

                // for testing purpose
                for(User user : sorted_users){
                    Log.i("DEBUGGING_SORTED_FINAL",""+user.email);
                }


                userListingRecyclerAdapter = new UserListingRecyclerAdapter(sorted_users);
                recyclerView.setAdapter(userListingRecyclerAdapter);
                userListingRecyclerAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Log.i("DEBUGGING_MOTIVATION","hallo you didnt execute me????????");




    }


    private void checkFriendWhoInstalledTheApps(List<User> users){
        DBAdapter db = new DBAdapter(getActivity());

        // open
        db.openDB();

        // get friends from db
        Cursor c = db.getAllFriends();

        // loop thru data then adding to arraylist
        while(c.moveToNext()){
            for(User user : users){
                if(c.getString(2).equals(user.email)){
                    filtered_users.add(user);
                }
            }
        }

        db.closeDB();
    }



    @Override
    public void onGetAllUsersFailre(String message) {
        Log.i("hakimmmmmmmm","this is from view.failed");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        Toast.makeText(getActivity(), "Error "+message, Toast.LENGTH_SHORT);
    }


    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {


        ChatActivity.startActivity(getActivity(), userListingRecyclerAdapter.getUser(position).email,
                userListingRecyclerAdapter.getUser(position).uid,
                userListingRecyclerAdapter.getUser(position).firebaseToken);



    }


}
