package com.example.phone.contacts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phone.GetContacts;
import com.example.phone.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactsFragment extends Fragment {

    SharedPreferences preferences;
    Gson gson = new Gson();
    ArrayList<ContactModel> list = new ArrayList<>();
    JSONArray contactJson = new JSONArray();
    ContactModel contacts;
    Bitmap icon;
    View itemView ;
    Paint paint = new Paint();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.contact_fragment, null);
        preferences = getContext().getSharedPreferences(Constants.CONTACT_PREFERENCE, Context.MODE_PRIVATE);
        icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.call);
        Log.d("TAG", "onCreateView: contacts");
        if(preferences.getString(Constants.CONTACTS, null) == null){
            GetContacts obj = new GetContacts(getContext());
            list = obj.getContacts();
            contactJson = new JSONArray(list);
//            Log.d("TAG", "onCreateView: if "+contactJson.toString());
        }else {
            try {
                contactJson = new JSONArray(preferences.getString(Constants.CONTACTS, null));
//                Log.d("TAG", "onCreateView: else "+contactJson.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        final ContactsFragmentAdapter adapter = new ContactsFragmentAdapter();
        try {
            for(int i=0; i<contactJson.length(); i++){
                JSONObject obj = (JSONObject) contactJson.get(i);
//                Log.d("TAG", "onCreateView: "+obj.toString());
                contacts = gson.fromJson(obj.toString(), ContactModel.class);
                list.add(contacts);
            }
            adapter.setData(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = view.findViewById(R.id.contacts_listview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

//            private final ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.colorAccent));
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.d("TAG", "onSwiped: "+direction);
                if(direction == ItemTouchHelper.RIGHT){
                    adapter.callAction(viewHolder.getAdapterPosition());
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());

                }
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                itemView = viewHolder.itemView;


                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.CYAN);

                if (dX > 0) {
                    c.drawRect(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX/3), itemView.getBottom(), paint);
                    c.drawBitmap(icon,
                            (float) itemView.getLeft() + 100,
                            (float) itemView.getTop() + ((float) itemView.getBottom() - (float) itemView.getTop() - icon.getHeight())/2,
                            paint);

                } else if (dX < 0) {
                    c.drawRect(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom(), paint);
//                    background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else {
                    c.drawRect(0,0,0,0, paint);
//                    background.setBounds(0, 0, 0, 0);
                }

//                c.drawText("call", itemView.getLeft(), itemView.getTop() + itemView.getHeight() / 2, paint);


//                Rect imageBounds = c.getClipBounds();
//                mCustomImage.setBounds(itemView.getLeft()+ ((int) 100), itemView.getTop(), itemView.getLeft() + ((int) 200), itemView.getBottom());

//                background.draw(c);
//                mCustomImage.draw(c);



                super.onChildDraw(c, recyclerView, viewHolder, dX/3, dY, actionState, isCurrentlyActive);

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        SearchView searchContact = view.findViewById(R.id.search_contact);
        searchContact.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });




        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



}
