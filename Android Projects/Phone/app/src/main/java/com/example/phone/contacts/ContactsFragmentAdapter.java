package com.example.phone.contacts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phone.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    ContactModel contacts;
    ArrayList<ContactModel> list = new ArrayList<>();
    ArrayList<ContactModel> exampleList = new ArrayList<>();

    JSONArray contactJson;
    Gson gson = new Gson();
    Context context;


    Intent callIntent = new Intent(Intent.ACTION_CALL);


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    public void setData(ArrayList<ContactModel> list) throws JSONException {
        this.list = list;
        exampleList = new ArrayList<>(list);
//        Log.d("TAG", "setData: "+contactJson.toString());

    }

//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//
//        if(viewType==SHOW_MENU){
//            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_menu, parent, false);
//            return new MenuViewHolder(v);
//        }else{
//            context = parent.getContext();
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list, parent, false);
//            ViewHolder viewHolder = new ViewHolder(view);
//            viewHolder.name = view.findViewById(R.id.name);
//            viewHolder.phoneNumber = view.findViewById(R.id.phoneNumber);
//            viewHolder.callButton = view.findViewById(R.id.callButton);
//            viewHolder.contactImage = view.findViewById(R.id.contactImage);
//            viewHolder.contactOptions = view.findViewById(R.id.contactOptions);
//            viewHolder.contactList = view.findViewById(R.id.contactList);
//            return viewHolder;
//        }
//
////        Log.d("TAG", "onCreateViewHolder: data received");
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list, parent, false);
            context = parent.getContext();
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.name = view.findViewById(R.id.name);
            viewHolder.phoneNumber = view.findViewById(R.id.phoneNumber);
            viewHolder.contactImage = view.findViewById(R.id.contactImage);
            viewHolder.contactOptions = view.findViewById(R.id.contactOptions);
            viewHolder.contactList = view.findViewById(R.id.contactList);
            return viewHolder;
        }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof ViewHolder){
            contacts = list.get(position);

            ((ViewHolder) holder).name.setText(contacts.name);
            ((ViewHolder) holder).phoneNumber.setText((contacts.mobileNumber));

            if (contacts.photoURI != null) {
                Picasso.with(context).load(contacts.photoURI).into(((ViewHolder) holder).contactImage);
            }
            ((ViewHolder) holder).contactImage.setClipToOutline(true);
            if (contacts.isShow()) {
                ((ViewHolder) holder).contactOptions.setVisibility(View.VISIBLE);
            } else {
                ((ViewHolder) holder).contactOptions.setVisibility(View.GONE);

            }
            ((ViewHolder) holder).contactList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        changeVisibility(position);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });



        }
    }



    public void changeVisibility(int position) throws JSONException {
        for (int i = 0; i < list.size(); i++) {
            contacts = list.get(i);
            if (i == position) {
                if (contacts.isShow()) {
                    contacts.setShow(false);
                } else {
                    contacts.setShow(true);

                }
                Log.d("TAG", "changeVisibility: " + contacts.isShow());

            } else {
                contacts.setShow(false);
            }

        }
        setData(list);
        notifyDataSetChanged();

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout contactOptions;
        LinearLayout contactList;
        TextView name, phoneNumber;
        ImageView contactImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{
        Button button;
        public MenuViewHolder(View view){
            super(view);
            button = view.findViewById(R.id.swipeCall);
        }
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ContactModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (int i = 0; i < exampleList.size(); i++) {
                    if (exampleList.get(i).getName().toLowerCase().contains(filterPattern) || exampleList.get(i).getNumber().toLowerCase().contains(filterPattern)) {
                        filteredList.add(exampleList.get(i));
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }

    };




    public void callAction(int position) {

        callIntent.setData(Uri.parse("tel:" + list.get(position).mobileNumber));
        context.startActivity(callIntent);
        ;

    }


    public boolean isMenuShown() {
        for(int i=0; i<list.size(); i++){
            if(list.get(i).isShowMenu()){
                return true;
            }
        }
        return false;
    }

    public void closeMenu() {
        for(int i=0; i<list.size(); i++){
            list.get(i).setShowMenu(false);
        }
        notifyDataSetChanged();
    }
}
