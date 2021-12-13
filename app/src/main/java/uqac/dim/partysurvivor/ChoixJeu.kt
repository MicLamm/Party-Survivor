package uqac.dim.partysurvivor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList
import com.google.android.material.bottomnavigation.BottomNavigationView

class ChoixJeu : AppCompatActivity() {
    private var layoutManager : RecyclerView.LayoutManager? = null
    lateinit var adapter: RecyclerGame
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_jeu)

        //recycler view avec bdd
        val database = FirebaseDatabase.getInstance()
        val refGame = database.getReference("game")
        refGame.get().addOnCompleteListener { task ->
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
            recyclerView = findViewById(R.id.recyclerViewGame)
            layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
            System.out.println("ON EST DANS CHOIX TYPE JEU")
            adapter = RecyclerGame(game_details)
            recyclerView.setAdapter(adapter)





        }
        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.selectedItemId = R.id.ic_2
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_1 -> {
                    val a = Intent(this@ChoixJeu, ChoixFavoris::class.java)
                    startActivity(a)
                }
                R.id.ic_2 -> {
                    val a = Intent(this@ChoixJeu, ChoixJeu::class.java)
                    startActivity(a)
                }
                R.id.ic_3 -> {
                    val b = Intent(this@ChoixJeu, FeaturedDrink::class.java)
                    startActivity(b)
                }
                R.id.ic_4 -> {
                    val b = Intent(this@ChoixJeu, MainActivityAlcoolMenu::class.java)
                    startActivity(b)
                }
                R.id.ic_5 -> {
                    val b = Intent(this@ChoixJeu, TestAddImage::class.java)
                    startActivity(b)
                }
            }
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)

        val search: MenuItem? = menu?.findItem(R.id.nav_search)
        val searchView: SearchView = search?.actionView as SearchView
        searchView.queryHint = "Search something"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter().filter(newText)
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }
}