package com.example.pastpresentfuture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EventDetail extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // Initialize variables
        final TextView eventName = (TextView) findViewById(R.id.eventName);
        final TextView eventDate = (TextView) findViewById(R.id.eventDate);
        final TextView eventMemo = (TextView) findViewById(R.id.eventMemo);
        final TextView eventLocation = (TextView) findViewById(R.id.eventLocation);
        final Button update = (Button) findViewById(R.id.update);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Event");

        // Get data from the previous activity and display
        Bundle bundle = getIntent().getExtras();
        String detail = bundle.getString("eventDetail");

        int slash = detail.indexOf("/");
        char[] eachChar = detail.toCharArray();
        String title = "";
        String date = "";
        for (int i = 0; i < slash -3; i++) { title += eachChar[i]; }
        for (int i = slash -2; i < eachChar.length; i++) { date += eachChar[i]; }
        eventName.setText(title);
        eventDate.setText(date);

        // When an update button was clicked, data from user will be stored in database
        // by finding the corresponding place using the title of an event.
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = eventName.getText().toString();
                String date = eventDate.getText().toString();
                String memo = eventMemo.getText().toString();
                String location = eventLocation.getText().toString();

                final EventStore eventStore = new EventStore(name, date, memo, location);
                database = FirebaseDatabase.getInstance();
                databaseReference = database.getReference().child("Event");

                final Query updateItem = databaseReference.orderByChild("title").equalTo(name);
                updateItem.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot updateSnapshot : dataSnapshot.getChildren()) {
                            String key = updateSnapshot.getKey();
                            databaseReference.child(key).setValue(eventStore);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

                Intent intent = new Intent(EventDetail.this, FutureEvent.class);
                startActivity(intent);
            }
        });

        // Display memo and location corresponding to the title
        final String finalTitle = title;
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                EventStore es = dataSnapshot.getValue(EventStore.class);
                if (es.getTitle().equals(finalTitle)){
                    eventMemo.setText(es.getMemo());
                    eventLocation.setText(es.getLocation());
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseReference.addChildEventListener(childEventListener);
    }

    // Show map button will bring the user to map to show the event location.
    // Therefore, bring the location detail that the user typed with intent here.
    public void onClickButtonToMap (View view) {
        Intent intent = new Intent(this, EventLocation.class);
        TextView eventLocation = (TextView) findViewById(R.id.eventLocation);
        String location = eventLocation.getText().toString();
        intent.putExtra("location", location);
        startActivity(intent);
    }

    // Logs
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("event detail tag", "now running onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("event detail tag", "now running onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("event detail tag", "now running onPause");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("event detail tag", "now running onRestart");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("event detail tag", "now running onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("event detail tag", "now running onDestroy");
    }
}
