package com.example.harshal.hackholyoke;

import android.support.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;
import java.util.List;

public class FireBaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private List<users>  usersList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<users> usersList, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FireBaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("users");
    }

    public void readUsers(final DataStatus dataStatus){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    users user = keyNode.getValue(users.class);
                    usersList.add(user);
                }
                dataStatus.DataIsLoaded(usersList, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
