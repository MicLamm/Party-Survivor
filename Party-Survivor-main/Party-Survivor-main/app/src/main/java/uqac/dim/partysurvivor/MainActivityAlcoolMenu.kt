package uqac.dim.partysurvivor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivityAlcoolMenu: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu_alcool)

        supportActionBar?.setTitle("Help' and Shake")



        var buttonScotchAndWhisky = findViewById<Button>(R.id.scotchAndWhisky)
        var buttonLiqueurAndCream = findViewById<Button>(R.id.liqueurAndCream)
        var buttonGin = findViewById<Button>(R.id.gin)
        var buttonRum = findViewById<Button>(R.id.rum)


        val monIntent1: Intent = Intent(this, MainActivity::class.java)


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

    }
}