package com.example.socialapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

public class login_page_fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_page_fragment, null);
    }

    public String getUserEmail(){
        EditText email = getView().findViewById(R.id.email);
        return email.getText().toString();
    }
    public String getUserPassword(){
        EditText password = getView().findViewById(R.id.password);
        return password.getText().toString();
    }
}
