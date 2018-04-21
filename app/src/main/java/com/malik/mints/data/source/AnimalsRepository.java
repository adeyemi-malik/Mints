package com.malik.mints.data.source;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AnimalsRepository {
    public static final String TAG = "AnimalsRepository";
    FirebaseFirestore database;
    FirebaseUser user;
    public AnimalsRepository()
    {
        database = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void getAnimals()
    {
        
    }

}
