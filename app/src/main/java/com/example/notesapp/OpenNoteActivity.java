package com.example.notesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class OpenNoteActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.exanmple.notesapp.MESSAGE";
    EditText editText;
    String fileName;
    Boolean reOpenFile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_note);

        editText = findViewById(R.id.editText);
        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setSingleLine(false);


        Intent intent = getIntent();

        if (intent.getExtras()!=null) {
            reOpenFile = true;

            fileName = intent.getStringExtra(MainActivity.OPEN_NOTE_MESSAGE);
            Toast.makeText(this, "opened ", Toast.LENGTH_LONG);
            String content = openFile(fileName);
            editText.setText(content);
        }

        //Nav arrow to go to parent activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /** Called when the user taps the system back button */
    @Override
    public void onBackPressed()  {
        saveNewOrReopenedFile();

    }

    /** Called when the user taps the nav bar back button */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                saveNewOrReopenedFile();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveNewOrReopenedFile(){
        //Get the first line of the text
        String[] lines = editText.getText().toString().split(System.getProperty("line.separator"));


        //Is it a new file or a reopened file?
        //New file: create file name and save file
        if(!reOpenFile){
            if (lines[0]!=null){
                if(lines[0].length()>25) {
                    fileName = lines[0].substring(0,25); //Save first rows first 25 chars

                }else{
                    fileName = lines[0];
                }
                saveToFile(fileName);
                //TODO find first line and save it to file name
            }}
        //If the file is old and has been changed, save new content to the file
        else if(!editText.getText().toString().equals(openFile(fileName))){
            //TODO if first line has changed, change file name
            saveToFile(fileName);
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_MESSAGE, fileName);
        setResult(Activity.RESULT_OK, intent);
        finish();

    }

    public void saveToFile(String fileName) {
        FileOutputStream out;

        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), "filenametest.txt");
            out = new FileOutputStream(file);
            out.write(editText.getText().toString().getBytes());
            out.flush();
            out.close();
            Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public String openFile(String fileName) {
        String content = "";
        if (fileExists(fileName)) {
            try {
                InputStream in = openFileInput(fileName);
                if ( in != null) {
                    InputStreamReader tmp = new InputStreamReader( in );
                    BufferedReader reader = new BufferedReader(tmp);
                    String str;
                    StringBuilder buf = new StringBuilder();
                    while ((str = reader.readLine()) != null) {
                        buf.append(str + "\n");
                    } in .close();
                    content = buf.toString();
                }
            } catch (java.io.FileNotFoundException e) {} catch (Throwable t) {
                Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return content;
    }

    public boolean fileExists(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

}
