package com.malik.mints.data.source;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.malik.mints.models.animal.Animal;
import com.malik.mints.models.map.Location;

import java.util.ArrayList;
import java.util.List;


public class MapsRepository
{
    private static MapsRepository INSTANCE = null;

    public static final String TAG = "MapsRepository";
     FirebaseFirestore database;
     FirebaseUser user;
    public MapsRepository()
    {
        database = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public static MapsRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MapsRepository();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public List<Animal> getAnimalLocations()
    {
        List locations = new ArrayList<Location>();
        String id = user.getEmail();
        database.collection("animals")
                .whereEqualTo("userid", id)
                .get()
                .addOnCompleteListener(
                        task ->
                        {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    if(document.exists())
                                    {
                                        Animal animal = document.toObject(Animal.class);
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        database.collection("locations")
                                                .whereEqualTo("deviceid", animal.deviceid)
                                                .orderBy("deviceid")
                                                .orderBy("date", Query.Direction.DESCENDING)
                                                .limit(1)
                                                .get()
                                                .addOnSuccessListener(documentSnapshots -> {
                                                    for (DocumentSnapshot document1 : documentSnapshots.getDocuments() )
                                                    {
                                                        if(document1.exists())
                                                        {
                                                            Location location = document1.toObject(Location.class);
                                                            animal.setLastLocation(location);
                                                            locations.add(animal);
                                                        }
                                                        else
                                                        {
                                                            locations.add(animal);
                                                        }
                                                    }
                                                })
                                            .addOnFailureListener(e -> locations.add(animal));
                                    }
                                }
                            }
                            else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                );
        return locations;
    }
}
