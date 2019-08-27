package com.example.yunihafsari.fypversion3.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.apps_model.Contact;
import com.example.yunihafsari.fypversion3.sqlite.adapter.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by yunihafsari on 02/05/2017.
 */

public class DuplicateNameAdapter extends RecyclerView.Adapter<DuplicateNameAdapter.Holder>{

    private ArrayList<Contact> contacts;
    private ArrayList<Contact> checkedContact = new ArrayList<>();

    public DuplicateNameAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.duplicate_name_model, parent, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.duplicate_name.setText(contacts.get(position).getName());
        holder.duplicate_phone_number.setText(contacts.get(position).getPhone_number());

       holder.setItemClickListener(new ItemClickListener() {
           @Override
           public void onItemClickListener(View v, int pos) {
                CheckBox checkBox = (CheckBox)v;

               if(checkBox.isChecked()){
                    checkedContact.add(contacts.get(pos));
               }else if(!(checkBox.isChecked())){
                    checkedContact.remove(contacts.get(pos));
               }
           }
       });
    }

    public ArrayList<Contact> getCheckedContact() {
        return checkedContact;
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView duplicate_name;
        private TextView duplicate_phone_number;
        private CheckBox checkBox;
        ItemClickListener itemClickListener;

        public Holder(View itemView) {
            super(itemView);

            duplicate_name = (TextView) itemView.findViewById(R.id.model_name);
            duplicate_phone_number = (TextView) itemView.findViewById(R.id.model_number);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);

            checkBox.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClickListener(v, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener=itemClickListener;
        }


    }
}
