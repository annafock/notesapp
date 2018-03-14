package com.example.notesapp;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class OpenNoteActivity extends AppCompatActivity {
    private List<String> data;
    public static final String EXTRA_MESSAGE = "com.exanmple.notesapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_note);

        EditText editText = findViewById(R.id.editText);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        String input = editText.getText().toString();
        //editText.setText(input, TextView.BufferType.EDITABLE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = new ArrayList<String>();

    }

    @Override
    public void onBackPressed()  {
            saveText();
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                saveText();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //More work on making the text edit be saved to the list
    public void saveText(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putStringArrayListExtra("data", (ArrayList<String>) data);






//        Intent intent = new Intent(this, MainActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//        System.out.println("Sparar!");
    }

}
