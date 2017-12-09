package com.example.pastpresentfuture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.ArrayList;

public class ToDoToday extends AppCompatActivity {

    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    EditText txtInput;
    ListView listView;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_today);

        // Initialize database and list view
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("todo");

        listView = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(ToDoToday.this, R.layout.list_item_todotoday, R.id.txtItem, arrayList);
        listView.setAdapter(adapter);

        // when the button is clicked, data from text input will be stored in database
        txtInput = (EditText) findViewById(R.id.txtInput);
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = txtInput.getText().toString();
                if (!txtInput.getText().toString().equals("")) {
                    txtInput.setText("");
                    TodoMessage todoMessage = new TodoMessage(newItem);
                    mDatabaseReference.push().setValue(todoMessage);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter something!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // long click on list item will delete item from database
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Query deleteItem = mDatabaseReference.orderByChild("text").equalTo(arrayList.get(position));
                deleteItem.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot deleteSnapshot: dataSnapshot.getChildren()) {
                            deleteSnapshot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

                Toast.makeText(getApplicationContext(), arrayList.get(position)+" has been completed!", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        // show on device based on the changed on database
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TodoMessage todoMessage = dataSnapshot.getValue(TodoMessage.class);
                arrayList.add(todoMessage.getText());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                TodoMessage deleted = dataSnapshot.getValue(TodoMessage.class);
                arrayList.remove(deleted.getText());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("todo list tag", "now running onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("todo list tag", "now running onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("todo list tag", "now running onPause");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("todo list tag", "now running onRestart");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("todo list tag", "now running onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("todo list tag", "now running onDestroy");
    }
}
