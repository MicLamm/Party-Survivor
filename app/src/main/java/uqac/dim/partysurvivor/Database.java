package uqac.dim.partysurvivor;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.Query;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Database {

    private StorageReference mStorageRef;
    public ArrayList<Alcool> alcools;

    public Database(ArrayList<Alcool> alcools) {
        this.alcools = alcools;
    }

    public void reinitialise(){
        alcools = new ArrayList<Alcool>();
    }

    public ArrayList<Alcool> getAlcools() {
        return alcools;
    }

    public void setAlcools(ArrayList<Alcool> values) {
        System.out.println("####################### j'ajoute des données la je crois bien");
        this.alcools = values;
        //System.out.println("resultats : " +alcools);
        for(int i = 0; i<alcools.size(); i++){
            //System.out.println("les noms : "+alcools.get(i).getNom());
        }
    }

    public void readAlcool(String type){
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference refAlcool = database.getReference("alcool/"+type);
        //System.out.println("la référence de la database : "+refAlcool);
        /*ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setAlcools(new ArrayList<Alcool>());
                ArrayList<Alcool> alcool = new ArrayList();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        AlcoolImage alcoolImage = snapshot.getValue(AlcoolImage.class);
                        //System.out.println("nom : "+snapshot.getKey() +" "+"url de l'image :"+alcoolImage.imageUrl);

                        Alcool alcool1 = new Alcool(type, snapshot.getKey(), alcoolImage.imageUrl);
                        //alcool.add(alcool1);
                        alcools.add(alcool1);

                        //setAlcools(alcool1);
                    }
                }
                //System.out.println("########### alcools : "+alcools);
                setAlcools(alcool);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };


        refAlcool.addValueEventListener(postListener);*/

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refAlcool = database.getReference("alcool/"+type);
        refAlcool.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    System.out.println("firebase"+ "Error getting data"+ task.getException());
                }
                else {
                    DataSnapshot snapshotResult = task.getResult();
                    for(DataSnapshot snapshot : snapshotResult.getChildren()){
                        AlcoolImage alcoolImage = snapshot.getValue(AlcoolImage.class);
                        //System.out.println("nom : "+snapshot.getKey() +" "+"url de l'image :"+alcoolImage.imageUrl);

                        Alcool alcool1 = new Alcool(type, snapshot.getKey(), alcoolImage.imageUrl);
                        //alcool.add(alcool1);
                        alcools.add(alcool1);

                        //setAlcools(alcool1);
                    }
                }
            }
        });

    }


}
