package uqac.dim.partysurvivor

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivityAlcoolMenu: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu_alcool)

        supportActionBar?.setTitle("Help' and Shake")



        var buttonScotchAndWhisky = findViewById<Button>(R.id.scotchAndWhisky)
        var buttonLiqueurAndCream = findViewById<Button>(R.id.liqueurAndCream)
        var buttonGin = findViewById<Button>(R.id.gin)
        var buttonRum = findViewById<Button>(R.id.rum)


        val monIntent1: Intent = Intent(this, ActivityListAlcool::class.java)


        buttonScotchAndWhisky.setOnClickListener{
            monIntent1.putExtra("idButton", "scotchAndWhisky")
            startActivity(monIntent1)
            //finish()

        }

        buttonLiqueurAndCream.setOnClickListener{
            monIntent1.putExtra("idButton", "liqueurAndCream")
            startActivity(monIntent1)
        }

        buttonGin.setOnClickListener{
            monIntent1.putExtra("idButton", "gin")
            startActivity(monIntent1)
        }

        buttonRum.setOnClickListener{
            monIntent1.putExtra("idButton", "rum")
            startActivity(monIntent1)
        }

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.selectedItemId = R.id.ic_4
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_1 -> {
                    val a = Intent(this@MainActivityAlcoolMenu, ChoixCategorie::class.java)
                    startActivity(a)
                }
                R.id.ic_2 -> {
                    val a = Intent(this@MainActivityAlcoolMenu, ChoixTypeJeu::class.java)
                    startActivity(a)
                }
                R.id.ic_3 -> {
                    val b = Intent(this@MainActivityAlcoolMenu, FeaturedDrink::class.java)
                    startActivity(b)
                }
                R.id.ic_4 -> {
                }
                R.id.ic_5 -> {
                    val b = Intent(this@MainActivityAlcoolMenu, ChoixCategorie::class.java)
                    startActivity(b)
                }
            }
            false
        }

    }
}