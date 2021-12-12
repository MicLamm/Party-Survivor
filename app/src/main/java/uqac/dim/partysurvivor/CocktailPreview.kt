package uqac.dim.partysurvivor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class CocktailPreview() : AppCompatActivity() {
    //private var titles = arrayOf("Cocktail One", "Cocktail Two", "Cocktail Three", "Cocktail Four", "Cocktail Five","Cocktail Six" )
    //private var ingredient_Visible : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_preview)
        var buttonIngredient = findViewById<Button>(R.id.ButtonIngredient)
        var buttonRecette = findViewById<Button>(R.id.ButtonRecette)

        var viewIngredient = findViewById<TextView>(R.id.ViewIngredient)
        var viewRecette = findViewById<TextView>(R.id.ViewRecette)

        //à retirer quand deplacement dans GamePreview
        viewIngredient.setMovementMethod(ScrollingMovementMethod())

        buttonIngredient.setOnClickListener {
            viewRecette.visibility = View.GONE;
            viewIngredient.visibility = View.VISIBLE;
        }
        buttonRecette.setOnClickListener {
            viewIngredient.visibility = View.GONE;
            viewRecette.visibility = View.VISIBLE;
        }


        val position: Int = intent.getIntExtra("position", -1)
        val data:Coktail = intent.getSerializableExtra("data") as Coktail

        if(position != -1){
            var image: ImageView = findViewById(R.id.imageView)
            Glide.with(this).load(data.imageUrl).into(image)

            var title: TextView = findViewById(R.id.TitleCocktail)
            title.setText(data.coktailName)

            var ingredient: TextView = findViewById(R.id.ViewIngredient)
            ingredient.setText(data.ingredient)
            var recette: TextView = findViewById(R.id.ViewRecette)
            recette.setText(data.recette)
        }

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.selectedItemId = R.id.ic_3
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_1 -> {
                    val a = Intent(this@CocktailPreview, ChoixCategorie::class.java)
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

    //gérer la vue recette/ingredient
    fun hideRecette(view:View){

        var ingredient: TextView = findViewById(R.id.ViewIngredient)
        var recette: TextView = findViewById(R.id.ViewRecette)

        if(ingredient.getVisibility() == View.VISIBLE){
            ingredient.setVisibility(View.GONE)
            recette.setVisibility(View.VISIBLE)
        }
    }

    fun hideIngredient(view:View){
        var ingredient: TextView = findViewById(R.id.ViewIngredient)
        var recette: TextView = findViewById(R.id.ViewRecette)

        if(recette.getVisibility() == View.VISIBLE){
            recette.setVisibility(View.GONE)
            ingredient.setVisibility(View.VISIBLE)
        }
    }
}