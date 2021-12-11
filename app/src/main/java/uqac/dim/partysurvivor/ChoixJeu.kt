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

class ChoixJeu : AppCompatActivity() {
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerCoktail.ViewHolder>? = null
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_jeu)

        val Type: String = intent.getStringExtra("Type") as String


        //recycler view avec bdd
        val database = FirebaseDatabase.getInstance()
        val refAlcool = database.getReference("game/"+Type)
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
            val coktail_details: List<Coktail> = Arrays.asList()
            recyclerView = findViewById(R.id.recyclerViewGame)
            layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
            System.out.println("ON EST DANS CHOIX TYPE JEU")
            //adapter = RecyclerGame(game_details)
            adapter = RecyclerCoktail(coktail_details, "game", game_details)
            recyclerView.adapter = adapter


        }













        /*var view = findViewById<LinearLayout>(R.id.MainLayout)
        val lInflater:LayoutInflater = LayoutInflater.from(applicationContext)
        for(i in 1..5){
            val row: View = lInflater.inflate(R.layout.row,view,false)
            val rowbtn: Button = row.findViewById(R.id.GameBtn)
            val rowIndex: TextView = row.findViewById(R.id.Index)
            rowIndex.text = "1"
            rowbtn.setOnClickListener {
                val intent = Intent(this, CocktailPreview::class.java)
                var view:LinearLayout = findViewById(R.id.MainLayout)
                for (childView in view.children){
                    val btn: Button = childView.findViewById(R.id.GameBtn)
                    intent.putExtra("Jeu",1)

                }
                startActivity(intent)
            }
            rowbtn.text = "Pige dans le lac"

            view.addView(row,0)
        }
        view = findViewById<LinearLayout>(R.id.OptionalLayout)
        for(i in 1..5){
            val row: View = lInflater.inflate(R.layout.row,view,false)
            val text: Button = row.findViewById(R.id.GameBtn)
            text.text = "Pige dans le lac"

            view.addView(row,0)
        }*/


    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)

        val search: MenuItem? = menu?.findItem(R.id.nav_search)
        val searchView: SearchView = search?.actionView as SearchView
        searchView.queryHint = "Search something"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var view:LinearLayout = findViewById(R.id.MainLayout)
                for (childView in view.children){
                    val btn: Button = childView.findViewById(R.id.GameBtn)
                    if (!btn.text.startsWith(newText.toString())){
                        btn.visibility =View.GONE
                    }
                    else{
                        btn.visibility =View.VISIBLE
                    }

                }
                view = findViewById(R.id.OptionalLayout)
                for (childView in view.children){
                    val btn: Button = childView.findViewById(R.id.GameBtn)
                    if (!btn.text.startsWith(newText.toString())){
                        btn.visibility =View.GONE
                    }
                    else{
                        btn.visibility =View.VISIBLE
                    }

                }
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }*/
}