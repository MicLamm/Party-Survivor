package uqac.dim.partysurvivor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase

class ChoixFavoris : AppCompatActivity() {

    lateinit var adapterG: RecyclerGame
    lateinit var adapterD: RecyclerCoktail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_favoris)

        val database = FirebaseDatabase.getInstance()
        val refAlcool = database.getReference("coktail")
        refAlcool.get().addOnCompleteListener { task ->
            var coktails : ArrayList<Coktail> = ArrayList()
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
            var recyclerView: RecyclerView = findViewById(R.id.recyclerViewDrinkFavori)
            var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
            adapterD = RecyclerCoktail(coktail_details)
            recyclerView.adapter = adapterD
        }

        refAlcool.get().addOnCompleteListener { task ->
            var games: ArrayList<Game> = ArrayList()
            if (!task.isSuccessful) {

                println("firebase" + "Error getting data" + task.exception)

            } else {

                val snapshotResult = task.result
                for (snapshot in snapshotResult!!.children) {
                    var game = snapshot.getValue(Game::class.java)
                    if (game != null) {
                        games.add(game)
                    }
                }
            }

            val game_details: List<Game> = games;
            var recyclerView: RecyclerView = findViewById(R.id.recyclerViewGameFavori)
            var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
            System.out.println("ON EST DANS CHOIX TYPE JEU")
            adapterG = RecyclerGame(game_details)
            recyclerView.setAdapter(adapterG)
        }

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.selectedItemId = R.id.ic_1
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_1 -> {
                    val a = Intent(this@ChoixFavoris, ChoixCategorie::class.java)
                    startActivity(a)
                }
                R.id.ic_2 -> {
                    val a = Intent(this@ChoixFavoris, ChoixTypeJeu::class.java)
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
                    val b = Intent(this@ChoixFavoris, ChoixCategorie::class.java)
                    startActivity(b)
                }
            }
            false
        }
    }


}