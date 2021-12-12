package uqac.dim.partysurvivor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList
import com.google.android.material.bottomnavigation.BottomNavigationView
import uqac.dim.partysurvivor.addCoktailToBdd.TestAddImage

class ActivityListAlcool : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alcool)

        renameActionBar()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //val jameson = findViewById<ImageView>(android.R.id.jameson.png)
        //jameson.setImageResource(android.R.drawable.jameson.png)
        //val image1: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.jameson, null)
        //var alcool1 = Alcool("Whisky", "Jameson", image1)


        //var db : Database = Database(ArrayList<Alcool>());


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


        //Lecture des données des alcools et envoie de la liste à CustomGrid pour affichage
        var type = getIdButton();

        val database = FirebaseDatabase.getInstance()
        val refAlcool = database.getReference("alcool/$type")
        refAlcool.get().addOnCompleteListener { task ->
            var alcools: ArrayList<Alcool> = ArrayList()
            if (!task.isSuccessful) {

                println("firebase" + "Error getting data" + task.exception)

            } else {

                val snapshotResult = task.result
                for (snapshot in snapshotResult!!.children) {
                    val alcoolImage = snapshot.getValue(AlcoolImage::class.java)
                    val alcool1 = Alcool(type, snapshot.key!!, alcoolImage!!.imageUrl)
                    alcools.add(alcool1)
                }
                System.out.println("RESULTAT DE LA LISTE D ALCOOL : "+alcools)
            }

            val image_details: List<Alcool> = alcools;
            val alcoolMenu_details: List<AlcoolMenu> = Arrays.asList()
            val gridView = findViewById<View>(R.id.gridView) as GridView
            gridView.adapter = CustomGridAdapterMenuAlcool(this, image_details, alcoolMenu_details, "alcool")

            //quand l'user click sur un gridItem
            gridView.onItemClickListener =
                AdapterView.OnItemClickListener { a, v, position, id ->
                    val o = gridView.getItemAtPosition(position)
                    val alcool: Alcool = o as Alcool
                    /*Toast.makeText(
                        this@MainActivity, ""
                                , Toast.LENGTH_LONG
                    ).show()*/
                }

        }

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.selectedItemId = R.id.ic_3
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_1 -> {
                    val a = Intent(this@ActivityListAlcool, ChoixCategorie::class.java)
                    startActivity(a)
                }
                R.id.ic_2 -> {
                    val a = Intent(this@ActivityListAlcool, ChoixTypeJeu::class.java)
                    startActivity(a)
                }
                R.id.ic_3 -> {
                    val b = Intent(this@ActivityListAlcool, FeaturedDrink::class.java)
                    startActivity(b)
                }
                R.id.ic_4 -> {
                    val b = Intent(this@ActivityListAlcool, MainActivityAlcoolMenu::class.java)
                    startActivity(b)
                }
                R.id.ic_5 -> {
                    val b = Intent(this@ActivityListAlcool, TestAddImage::class.java)
                    startActivity(b)
                }
            }
            false
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun renameActionBar(){
        intent = getIntent()
        when(getIdButton()){
            "scotchAndWhisky" -> supportActionBar?.setTitle("Scotch and Whisky")
            "liqueurAndCream" -> supportActionBar?.setTitle("Liqueur and Cream")
            "gin" -> supportActionBar?.setTitle("Gin")
            "rum" -> supportActionBar?.setTitle("Rum")
            "vodka" -> supportActionBar?.setTitle("Vodka")
            "cognacAndBrandy" -> supportActionBar?.setTitle("Cognac and Brandy")
            "aniseFlavouredSpirit" -> supportActionBar?.setTitle("Anise Flavoured Spirit")
            "absinth" -> supportActionBar?.setTitle("Absinth")
        }
    }

    fun getIdButton():String{
        intent = getIntent()
        var idButton = ""
        if(intent != null){
            if(intent.hasExtra("idButton")){
                idButton = intent.getStringExtra("idButton").toString()
            }
        }
        return idButton
    }



    /*fun getListData(idButton:String): List<Alcool>{

        var database = Database();

        database.readAlcool("rum");

        var list = ArrayList<Alcool>()
        var finalList = ArrayList<Alcool>()
        var ballantine = Alcool("scotchAndWhisky", "Ballantine", "ballantine")
        var crownRoyal = Alcool("scotchAndWhisky", "Crown Royal", "crown_royale")
        var grantsFamily = Alcool("scotchAndWhisky", "Grant's Family", "grants_family")
        var jameson = Alcool("scotchAndWhisky", "Jameson", "jameson")
        var johnnieWalker = Alcool("scotchAndWhisky", "Johnnnie Walker", "johnnie_walker")
        var bacardi = Alcool("rum", "Bacardi", "bacardi")
        var captainMorgan = Alcool("rum", "Captain Morgan", "captain_morgan")
        var sourPuss = Alcool("liqueurAndCream", "Sour Puss", "sour_puss")
        var sortilege = Alcool("liqueurAndCream", "Sortilège", "sortilege")
        var beefeater = Alcool("gin", "Beefeater", "beefeater")
        var bombay = Alcool("gin", "Bombay", "bombay")
        var meaghers = Alcool("liqueurAndCream", "Meaghers", "meaghers")



        list.add(ballantine)
        list.add(crownRoyal)
        list.add(grantsFamily)
        list.add(jameson)
        list.add(johnnieWalker)
        list.add(bacardi)
        list.add(captainMorgan)
        list.add(sourPuss)
        list.add(sortilege)
        list.add(beefeater)
        list.add(bombay)
        list.add(meaghers)



        for(alcool in list){
            if(alcool.type == idButton){
                finalList.add(alcool)
            }
        }

        return finalList

    }*/
}