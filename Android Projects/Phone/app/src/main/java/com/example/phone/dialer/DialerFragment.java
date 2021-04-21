package com.example.phone.dialer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phone.R;

import static android.content.ContentValues.TAG;

public class DialerFragment extends Fragment implements View.OnClickListener{

    EditText callNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialer_fragment, null);
        Log.d(TAG, "onCreateView: hello");
        Button button0 = view.findViewById(R.id.button0);
        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);
        Button button3 = view.findViewById(R.id.button3);
        Button button4 = view.findViewById(R.id.button4);
        Button button5 = view.findViewById(R.id.button5);
        Button button6 = view.findViewById(R.id.button6);
        Button button7 = view.findViewById(R.id.button7);
        Button button8 = view.findViewById(R.id.button8);
        Button button9 = view.findViewById(R.id.button9);
        ImageButton buttonClear = view.findViewById(R.id.button_clear);

        callNumber = view.findViewById(R.id.call_number);
        callNumber.setShowSoftInputOnFocus(false);


        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = callNumber.getText().toString();
                if(str.length() > 0){
                    str = str.substring(0,str.length()-1);
                    callNumber.setText(str);
            }
        }
        });
        return view;
    }



    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        callNumber.setText(callNumber.getText().toString() + button.getText());
    }
}
