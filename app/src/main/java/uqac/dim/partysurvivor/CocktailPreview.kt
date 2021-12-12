package uqac.dim.partysurvivor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import java.util.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlin.collections.HashMap


class CocktailPreview() : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_preview)

        var buttonAddFavori: Button? = findViewById(R.id.addFavoris)


        var viewIngredient = findViewById<TextView>(R.id.ingredientView)
        var viewRecette = findViewById<TextView>(R.id.recetteView)

        viewIngredient.setMovementMethod(ScrollingMovementMethod())

        viewRecette.setVisibility(View.INVISIBLE)

        var listData:List<Coktail>


        val position: Int = intent.getIntExtra("position", -1)
        val jeu: Int = intent.getIntExtra("Jeu", -1)
        val type: String = intent.getStringExtra("type") as String

        if(type.equals("coktail")){
            val data:Coktail = intent.getSerializableExtra("data") as Coktail

            if(position != -1){
                var image: ImageView = findViewById(R.id.imageView)
                Glide.with(this).load(data.imageUrl).into(image)
                //image.setImageResource(images[position])
                var title: TextView = findViewById(R.id.TitleCocktail)
                title.setText(data.coktailName)

                var ingredient: TextView = findViewById(R.id.ingredientView)
                ingredient.setText(data.ingredient)
                var recette: TextView = findViewById(R.id.recetteView)
                recette.setText(data.recette)



                buttonAddFavori?.setOnClickListener({
                    isFavoris(data, buttonAddFavori)
                    //addFavoris(data, buttonAddFavori)
                })
            }
        }
        else{
            val dataGame:Game = intent.getSerializableExtra("dataGame") as Game

            if(position != -1){
                var image: ImageView = findViewById(R.id.imageView)
                Glide.with(this).load(dataGame.imageUrl).into(image)
                //image.setImageResource(images[position])
                var title: TextView = findViewById(R.id.TitleCocktail)
                title.setText(dataGame.gameName)

                var buttonIngredient: Button = findViewById(R.id.ingredient)
                buttonIngredient.setText("regles")
                var buttonRecette: Button = findViewById(R.id.recette)
                buttonRecette.setText("gagner ?")

                var ingredient: TextView = findViewById(R.id.ingredientView)
                ingredient.setText(dataGame.regle)
                var recette: TextView = findViewById(R.id.recetteView)
                recette.setText(dataGame.gagner)
            }
        }

        if(jeu != -1){
            var image: ImageView = findViewById(R.id.imageView)
            image.visibility = View.GONE
            var title: TextView = findViewById(R.id.TitleCocktail)
            title.setText("Jeu")
        }

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.selectedItemId = R.id.ic_3
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_1 -> {
                    val a = Intent(this@CocktailPreview, ChoixFavoris::class.java)
                    startActivity(a)
                }
                R.id.ic_2 -> {
                    val a = Intent(this@CocktailPreview, ChoixTypeJeu::class.java)
                    startActivity(a)
                }
                R.id.ic_3 -> {
                    val b = Intent(this@CocktailPreview, FeaturedDrink::class.java)
                    startActivity(b)
                }
                R.id.ic_4 -> {
                    val b = Intent(this@CocktailPreview, MainActivityAlcoolMenu::class.java)
                    startActivity(b)
                }
                R.id.ic_5 -> {
                    val b = Intent(this@CocktailPreview, ChoixCategorie::class.java)
                    startActivity(b)
                }
            }
            false
        }

    }

    fun modif_state_button(button: Button, view:View){

    }

    //gérer la vue recette/ingredient
    fun hideRecette(view:View){

        var ingredient: TextView = findViewById(R.id.ingredientView)
        var recette: TextView = findViewById(R.id.recetteView)

        if(ingredient.getVisibility() == View.VISIBLE){
            ingredient.setVisibility(View.GONE)
            recette.setVisibility(View.VISIBLE)
        }
    }

    fun hideIngredient(view:View){
        var ingredient: TextView = findViewById(R.id.ingredientView)
        var recette: TextView = findViewById(R.id.recetteView)

        if(recette.getVisibility() == View.VISIBLE){
            recette.setVisibility(View.GONE)
            ingredient.setVisibility(View.VISIBLE)
        }

    }

    fun addFavoris(coktail: Coktail, buttonAddFavori: Button){
        System.out.println("J OBTIENT CA  : "+coktail)
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser?.uid

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("favoris")
        System.out.println("LE UID EST : "+currentUser.toString())

        var favorisMap: HashMap<String, Coktail> = HashMap()
        favorisMap.put(coktail.coktailName, coktail)
        ref.child(currentUser.toString()).updateChildren(favorisMap as Map<String, Any>)

        buttonAddFavori.setText("remove from your favoris ?")
    }

    fun isFavoris(coktail: Coktail, buttonAddFavori: Button){
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser?.uid
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("favoris/"+currentUser.toString())

        ref.get().addOnCompleteListener { task ->
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
            var isFavori: Boolean = false
            for(item in coktails){
                System.out.println("COKTAIL NAME : "+item.coktailName)
                if(coktail.coktailName.equals(item.coktailName)){
                    isFavori = true
                    System.out.println("LE COKTAIL EST UN FAVORIS ? true")
                    buttonAddFavori.setText("remove from your favoris ?")
                    //addFavoris(coktail, buttonAddFavori)
                }
            }
            if(isFavori){
                buttonAddFavori.setText("add to favoris ?")
                //remove
            }
            else{
                addFavoris(coktail, buttonAddFavori)
            }
        }

    }
}