package uqac.dim.partysurvivor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import uqac.dim.partysurvivor.addCoktailToBdd.TestAddImage
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var Game: Button
    lateinit var Drink: Button
    lateinit var Featured: Button
    lateinit var AddCoktail: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_temp)
        //val intent = Intent(this, ChoixTypeJeu::class.java)
        //startActivity(intent)
        Game = findViewById(R.id.Games)
        Featured = findViewById(R.id.Cocktails)
        Drink = findViewById(R.id.HelpShake)
        AddCoktail = findViewById(R.id.addCoktail)

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.selectedItemId = R.id.ic_1
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_1 -> {
                    val a = Intent(this@MainActivity, ChoixCategorie::class.java)
                    startActivity(a)
                }
                R.id.ic_2 -> {
                    val a = Intent(this@MainActivity, ChoixTypeJeu::class.java)
                    startActivity(a)
                }
                R.id.ic_3 -> {
                    val b = Intent(this@MainActivity, FeaturedDrink::class.java)
                    startActivity(b)
                }
                R.id.ic_4 -> {
                    val b = Intent(this@MainActivity, MainActivityAlcoolMenu::class.java)
                    startActivity(b)
                }
                R.id.ic_5 -> {
                    val b = Intent(this@MainActivity, ChoixCategorie::class.java)
                    startActivity(b)
                }
            }
            false
        }
    }

    fun onClick(view: View){
        var intent:Intent

        intent = Intent(this, MainActivity::class.java)
        when(view){
            Featured -> intent = Intent(this, FeaturedDrink::class.java)
            Game -> intent = Intent(this, ChoixTypeJeu::class.java)
            Drink -> intent = Intent(this, MainActivityAlcoolMenu::class.java)
            AddCoktail -> intent = Intent(this, TestAddImage::class.java)
        }
        startActivity(intent)
    }

}