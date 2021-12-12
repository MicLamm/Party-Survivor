package uqac.dim.partysurvivor

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import uqac.dim.partysurvivor.addCoktailToBdd.TestAddImage

class ChoixCategorie : AppCompatActivity() {
    lateinit var Game:Button
    lateinit var Drink:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_categorie)
        Drink = findViewById(R.id.DrinkBtn)
        Game = findViewById(R.id.GameBtn)

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.selectedItemId = R.id.ic_1
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_3 -> {
                    val a = Intent(this@ChoixCategorie, ChoixFavoris::class.java)
                    startActivity(a)
                }
                R.id.ic_2 -> {
                    val a = Intent(this@ChoixCategorie, ChoixTypeJeu::class.java)
                    startActivity(a)
                }
                R.id.ic_3 -> {
                    val b = Intent(this@ChoixCategorie, FeaturedDrink::class.java)
                    startActivity(b)
                }
                R.id.ic_4 -> {
                    val b = Intent(this@ChoixCategorie, MainActivityAlcoolMenu::class.java)
                    startActivity(b)
                }
                R.id.ic_5 -> {
                    val b = Intent(this@ChoixCategorie, TestAddImage::class.java)
                    startActivity(b)
                }
            }
            false
        }
    }

    fun onClick(view: View){
        val intent = Intent()
        when(view){
            Drink -> intent.putExtra("Type",2)
            Game -> intent.putExtra("Type",1)
        }
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

}