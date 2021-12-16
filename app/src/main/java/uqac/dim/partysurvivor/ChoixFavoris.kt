package uqac.dim.partysurvivor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ChoixFavoris : AppCompatActivity() {
    private var layoutManager : RecyclerView.LayoutManager? = null
    lateinit var adapter: RecyclerCoktail
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_favoris)

        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser?.uid

        val database = FirebaseDatabase.getInstance()
        val refAlcool = database.getReference("favoris/"+currentUser.toString())
        refAlcool.get().addOnCompleteListener { task ->
            var coktails: ArrayList<Coktail> = ArrayList()
            if (!task.isSuccessful) {

                println("firebase" + "Error getting data" + task.exception)

            } else {
                val snapshotResult = task.result
                for (snapshot in snapshotResult!!.children) {
                    var coktail = snapshot.getValue(Coktail::class.java)
                    if (coktail != null) {
                        coktails.add(coktail)
                    }
                }
            }

            val coktail_details: List<Coktail> = coktails;
            for(item in coktails){
                System.out.println("DANS CHOIx FAVORIS : "+item.coktailName )
            }
            recyclerView = findViewById(R.id.recyclerView)
            layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
            adapter = RecyclerCoktail(coktail_details)
            recyclerView.adapter = adapter


        }


        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.selectedItemId = R.id.ic_1
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_1 -> {
                    false
                }
                R.id.ic_2 -> {
                    val a = Intent(this@ChoixFavoris, ChoixJeu::class.java)
                    startActivity(a)
                }
                R.id.ic_3 -> {
                    val b = Intent(this@ChoixFavoris, FeaturedDrink::class.java)
                    startActivity(b)
                }
                R.id.ic_4 -> {
                    val b = Intent(this@ChoixFavoris, MainActivityAlcoolMenu::class.java)
                    startActivity(b)
                }
                R.id.ic_5 -> {
                    val b = Intent(this@ChoixFavoris, TestAddImage::class.java)
                    startActivity(b)
                }
            }
            false
        }
    }


}