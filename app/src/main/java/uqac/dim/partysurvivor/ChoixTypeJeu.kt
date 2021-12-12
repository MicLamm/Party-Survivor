package uqac.dim.partysurvivor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView

class ChoixTypeJeu : AppCompatActivity() {
    lateinit var dice: View
    lateinit var card: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_type_jeu)
        dice = findViewById(R.id.DiceBtn)
        card = findViewById(R.id.CardBtn)

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_1 -> {
                    val a = Intent(this@ChoixTypeJeu, ChoixCategorie::class.java)
                    startActivity(a)
                }
                R.id.ic_2 -> {
                    false
                }
                R.id.ic_3 -> {
                    val b = Intent(this@ChoixTypeJeu, FeaturedDrink::class.java)
                    startActivity(b)
                }
                R.id.ic_4 -> {
                    val b = Intent(this@ChoixTypeJeu, MainActivityAlcoolMenu::class.java)
                    startActivity(b)
                }
                R.id.ic_5 -> {
                    val b = Intent(this@ChoixTypeJeu, ChoixCategorie::class.java)
                    startActivity(b)
                }
            }
            false
        }
    }

    fun onClick(view: View) {
        //val intent = Intent(this, ChoixJeu::class.java)
        val intent = Intent(this, ChoixJeu::class.java)
        when(view){
            dice -> intent.putExtra("Type","diceGame")
            card -> intent.putExtra("Type","cardGame")
        }
        startActivity(intent)
    }
}