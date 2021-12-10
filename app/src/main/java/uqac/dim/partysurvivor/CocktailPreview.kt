package uqac.dim.partysurvivor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.*

class CocktailPreview() : AppCompatActivity() {
    private var titles = arrayOf("Cocktail One", "Cocktail Two", "Cocktail Three", "Cocktail Four", "Cocktail Five","Cocktail Six" )

    private var details = arrayOf("Detail of cocktail 1", "Detail of cocktail 2", "Detail of cocktail 3", "Detail of cocktail 4", "Detail of cocktail 5", "Detail of cocktail 6")

    private var images = intArrayOf(R.drawable.blue_lagoon, R.drawable.gin_tonic, R.drawable.margarita, R.drawable.rhum_cola, R.drawable.sex_on_the_beach, R.drawable.mojito)

    private var ingredient_Visible : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_preview)

        var buttonIngredient = findViewById<Button>(R.id.ingredient)
        var buttonRecette = findViewById<Button>(R.id.recette)

        var viewIngredient = findViewById<TextView>(R.id.ingredientView)
        var viewRecette = findViewById<TextView>(R.id.recetteView)

        viewIngredient.setMovementMethod(ScrollingMovementMethod())

        viewRecette.setVisibility(View.INVISIBLE)

        /*buttonIngredient.setOnClickListener({
            ingredient_Visible = true
        })

        buttonRecette.setOnClickListener({
            ingredient_Visible = false
        })*/

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





}