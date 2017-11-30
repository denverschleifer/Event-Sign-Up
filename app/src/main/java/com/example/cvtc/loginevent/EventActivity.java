package com.example.cvtc.loginevent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Denver on 11/27/17.
 */

public class EventActivity extends AppCompatActivity {

    EventDBHelper eventDBHelper;
    ArrayAdapter<String> mAdapter;
    ListView listEvent;

    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        eventDBHelper = new EventDBHelper(this);

        listEvent = findViewById(R.id.listEvent);

        loadEventList();

    }

    private void loadEventList() {
        ArrayList<String> eventList = eventDBHelper.getEventList();
        if(mAdapter==null) {
            mAdapter = new ArrayAdapter<String>(this,R.layout.event_view,R.id.event_title,eventList);
            listEvent.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(eventList);
           // mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void logout(View view){
        SharedPreferences sharedPreferences = getSharedPreferences(EventActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        moveTaskToBack(true);
        EventActivity.this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_event:
                final EditText eventEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Event")
                        .setMessage("Add Event Information")
                        .setView(eventEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String event = String.valueOf(eventEditText.getText());
                                eventDBHelper.insertNewEvent(event);
                                loadEventList();
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .create();
                dialog.show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteEvent(View view){
        View parent = (View)view.getParent();
        TextView eventTextView = parent.findViewById(R.id.event_title);
        String event = String.valueOf(eventTextView.getText());
        eventDBHelper.deleteEvent(event);
        loadEventList();
    }

}
