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

class ActivityListAlcool : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alcool)

        renameActionBar()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


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
                        val b = Intent(this@ActivityListAlcool, ChoixCategorie::class.java)
                        startActivity(b)
                    }
                }
                false
            }
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
}