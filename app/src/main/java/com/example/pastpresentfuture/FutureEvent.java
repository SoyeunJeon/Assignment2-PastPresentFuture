package com.example.pastpresentfuture;

import android.app.Dialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class FutureEvent extends AppCompatActivity {

    ArrayList<String> eventArrayList;
    ArrayAdapter<String> adapter;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_event);

        // Initialize variables
        final ListView eventListView = (ListView) findViewById(R.id.eventList);
        eventArrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(FutureEvent.this, R.layout.list_item_event, R.id.txtTitle, eventArrayList);
        eventListView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Event");

        // this button will open dialog to get data from users
        Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputBox();
            }
        });

        // When a user click an item from list view, this will bring a user to detail page
        // and also bring the data from the list
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FutureEvent.this, EventDetail.class);
                String eventDetail = eventArrayList.get(position);
                intent.putExtra("eventDetail", eventDetail);
                startActivity(intent);
            }
        });

        // When a user long click on an item from list view, the item will be deleted in database
        eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = eventArrayList.get(position);
                int slash = clickedItem.indexOf("/");
                char[] eachChar = clickedItem.toCharArray();
                String queryString = "";
                for (int i = 0; i < slash - 3; i++) { queryString += eachChar[i]; }

                final Query deleteItem =  databaseReference.orderByChild("title").startAt(queryString);
                deleteItem.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot deleteSnapshot : dataSnapshot.getChildren()) {
                            deleteSnapshot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

                Toast.makeText(getApplicationContext(), "Event has been deleted.", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        // This will adjust the changes like add and delete to the list view.
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                EventStore eventStore = dataSnapshot.getValue(EventStore.class);
                eventArrayList.add(eventStore.getTitle() + "\n" + eventStore.getDate());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                EventStore deleted = dataSnapshot.getValue(EventStore.class);
                eventArrayList.remove(deleted.getTitle() + "\n" + deleted.getDate());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseReference.addChildEventListener(childEventListener);
    }

    // Pop up a dialog on click of add button and gather data from user
    public void showInputBox(){
        final Dialog dialog = new Dialog(FutureEvent.this);
        dialog.setContentView(R.layout.input_box);

        final EditText titleInput = (EditText) dialog.findViewById(R.id.titleInput);
        final Button eventAdd = (Button) dialog.findViewById(R.id.eventAdd);
        eventAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker2);
                String year = String.valueOf(datePicker.getYear());
                String month = String.valueOf(datePicker.getMonth()+1);
                String day = String.valueOf(datePicker.getDayOfMonth());
                String eventDate = day+"/"+month+"/"+year;

                if (!titleInput.getText().toString().equals("")) {
                    EventStore eventStore = new EventStore(titleInput.getText().toString(), eventDate, "", "");
                    databaseReference.push().setValue(eventStore);

                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter something!", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

    // Logs
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("event list tag", "now running onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("event list tag", "now running onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("event list tag", "now running onPause");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("event list tag", "now running onRestart");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("event list tag", "now running onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("event list tag", "now running onDestroy");
    }
}
