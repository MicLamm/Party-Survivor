package uqac.dim.partysurvivor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
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
        var buttonIngredient = findViewById<Button>(R.id.ButtonIngredient)
        var buttonRecette = findViewById<Button>(R.id.ButtonRecette)
        var buttonAddFavori =  findViewById<Button>(R.id.addFavoris)
        var buttonRemoveFavoris = findViewById<Button>(R.id.RemoveFavoris)
        var viewIngredient = findViewById<ScrollView>(R.id.ViewIngredient)
        var viewRecette = findViewById<ScrollView>(R.id.ViewRecette)

        var listData:List<Coktail>
        buttonRecette.setOnClickListener {
            viewIngredient.visibility = View.GONE;
            viewRecette.visibility = View.VISIBLE;
        }
        buttonIngredient.setOnClickListener {
            viewIngredient.visibility = View.VISIBLE;
            viewRecette.visibility = View.GONE;
        }
        val position: Int = intent.getIntExtra("position", -1)
        val data:Coktail = intent.getSerializableExtra("data") as Coktail

        if(position != -1){
            var image: ImageView = findViewById(R.id.imageView)
            Glide.with(this).load(data.imageUrl).into(image)

            (findViewById<TextView>(R.id.TitleCocktail)).text = data.coktailName

            var ingredient: TextView = viewIngredient.getChildAt(0) as TextView
            ingredient.text = data.ingredient
            var recette: TextView = viewRecette.getChildAt(0) as TextView
            recette.text = data.recette

            buttonAddFavori.setOnClickListener {
                addFavoris(data)
                buttonRemoveFavoris.visibility = View.VISIBLE
                buttonAddFavori.visibility = View.GONE
            }
            buttonRemoveFavoris.setOnClickListener {
                removeFavoris(data)
                buttonRemoveFavoris.visibility = View.GONE
                buttonAddFavori.visibility = View.VISIBLE
            }
            isFavoris(data,buttonAddFavori,buttonRemoveFavoris)
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
                    val a = Intent(this@CocktailPreview, ChoixJeu::class.java)
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
                    val b = Intent(this@CocktailPreview, TestAddImage::class.java)
                    startActivity(b)
                }
            }
            false
        }
    }

    fun addFavoris(coktail: Coktail){
        System.out.println("J OBTIENT CA  : "+coktail)
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser?.uid
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("favoris")
        System.out.println("LE UID EST : "+currentUser.toString())
        var favorisMap: HashMap<String, Coktail> = HashMap()
        favorisMap.put(coktail.coktailName, coktail)
        ref.child(currentUser.toString()).updateChildren(favorisMap as Map<String, Any>)

    }
    private fun removeFavoris(coktail: Coktail){
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser?.uid
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("favoris/"+currentUser.toString())

        ref.child(coktail.coktailName).removeValue()
    }

    fun isFavoris(coktail: Coktail, buttonAddFavori: Button, buttonRemoveFavoris: Button){
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

            var isFavori: Boolean = false
            for(item in coktails){
                System.out.println(item.coktailName)
                if(item.coktailName == coktail.coktailName)
                    isFavori=true
            }
            if(isFavori){
                buttonRemoveFavoris.visibility = View.VISIBLE
                buttonAddFavori.visibility = View.GONE
            }
            else{
                buttonRemoveFavoris.visibility = View.GONE
                buttonAddFavori.visibility = View.VISIBLE
            }
        }

    }
}