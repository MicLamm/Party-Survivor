package uqac.dim.partysurvivor

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList
import com.google.android.material.bottomnavigation.BottomNavigationView
import uqac.dim.partysurvivor.addCoktailToBdd.TestAddImage

class MainActivityAlcoolMenu: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu_alcool)

        supportActionBar?.setTitle("Help' and Shake")

        val database = FirebaseDatabase.getInstance()
        val refAlcoolMenu = database.getReference("alcoolMenu")
        refAlcoolMenu.get().addOnCompleteListener { task ->
            var alcoolMenus : ArrayList<AlcoolMenu> = ArrayList()
            if (!task.isSuccessful) {

                println("firebase" + "Error getting data" + task.exception)

            } else {

                val snapshotResult = task.result
                for (snapshot in snapshotResult!!.children) {
                    val alcoolMenu = snapshot.getValue(AlcoolMenu::class.java)
                    if (alcoolMenu != null) {
                        alcoolMenus.add(alcoolMenu)
                    }
                }
            }

            val alcoolMenu_details: List<AlcoolMenu> = alcoolMenus;
            val image_details: List<Alcool> = Arrays.asList()
            val gridView = findViewById<View>(R.id.gridView) as GridView
            gridView.adapter = CustomGridAdapterMenuAlcool(this, image_details, alcoolMenu_details, "menu")

            //quand l'user click sur un gridItem
            gridView.onItemClickListener =
                AdapterView.OnItemClickListener { a, v, position, id ->
                    val o = gridView.getItemAtPosition(position)
                    var alcoolMenu : AlcoolMenu = o as AlcoolMenu
                    System.out.println("RESULTAT : "+alcoolMenu.categoryName)
                    val monIntent1: Intent = Intent(this, ActivityListAlcool::class.java)
                    monIntent1.putExtra("idButton", alcoolMenu.categoryName)
                    startActivity(monIntent1)
                }
        }



       /* var buttonScotchAndWhisky = findViewById<Button>(R.id.scotchAndWhisky)
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
        }*/

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.selectedItemId = R.id.ic_4
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_1 -> {
                    val a = Intent(this@MainActivityAlcoolMenu, ChoixFavoris::class.java)
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
                    val b = Intent(this@MainActivityAlcoolMenu, TestAddImage::class.java)
                    startActivity(b)
                }
            }
            false
        }

    }
}