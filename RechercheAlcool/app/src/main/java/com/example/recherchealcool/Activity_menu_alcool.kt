package com.example.recherchealcool

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.Type

class Activity_menu_alcool: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_alcool)


        var buttonScotchAndWhisky = findViewById<Button>(R.id.scotchAndWhisky)
        var buttonLiqueurAndCream = findViewById<Button>(R.id.liqueurAndCream)


        val monIntent1: Intent = Intent(this, MainActivity::class.java)


        buttonScotchAndWhisky.setOnClickListener{
            monIntent1.putExtra("idButton", "scotchAndWhisky")
            startActivity(monIntent1)
        }

        buttonLiqueurAndCream.setOnClickListener{
            monIntent1.putExtra("idButton", "liqueurAndCream")
            startActivity(monIntent1)
        }

    }


}