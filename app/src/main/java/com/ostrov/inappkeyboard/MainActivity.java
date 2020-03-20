package com.ostrov.inappkeyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean isShowKeyboard = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextSoft = (EditText) findViewById(R.id.editTextSoft);
        // prevent system keyboard from appearing when EditText is tapped
        editTextSoft.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editTextSoft.setTextIsSelectable(true);

        keyboard = (MyKeyboard) findViewById(R.id.keyboard);
        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = editTextSoft.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);
        keyboard.setVisibility(View.INVISIBLE);

        editTextSoft.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    keyboard.setVisibility(View.VISIBLE);
                else
                    keyboard.setVisibility(View.INVISIBLE);
            }
        });
        editTextSoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboard.setVisibility(View.VISIBLE);
            }
        });

        EditText editTextSystem = (EditText) findViewById(R.id.editTextSystem);
        editTextSystem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    showSystemKeyboard(v);
                else
                    hideSystemKeyboard(v);
            }
        });

        EditText editTextTemp = (EditText) findViewById(R.id.editTextTemp);
        editTextTemp.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editTextTemp.setTextIsSelectable(true);

        RelativeLayout layout = findViewById(R.id.layout_main);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSystemKeyboard(v);
                keyboard.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void showSystemKeyboard(View view){
        if(view.requestFocus()){
            InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void hideSystemKeyboard(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
