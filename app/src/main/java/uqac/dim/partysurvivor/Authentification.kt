package uqac.dim.partysurvivor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.Nullable
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.firebase.ui.auth.AuthUI.IdpConfig.EmailBuilder
import java.util.*
import android.content.Intent
import android.os.UserManager
import android.text.Layout
import android.view.View
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar


class Authentification : AppCompatActivity() {

    val RC_SIGN_IN = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification)
        setupListener()

    }

    fun setupListener(){

        val loginButton:Button = findViewById(R.id.logIn)
        loginButton.setOnClickListener { view -> startSignIn() }
    }

    fun startSignIn(){
        val providers: List<IdpConfig> = Collections.singletonList(EmailBuilder().build())


        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false, true)
                .build(),
            RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intent1: Intent = Intent(this, MainActivity::class.java)
        this.handleResponseAfterSignIn(requestCode, resultCode, data, intent1)
    }

    fun showNotification(message : String){
        val mainLayout: View? = findViewById(R.id.container)
        val authentificationLayout: View? = findViewById(R.id.authentificationLayout)
        if (mainLayout != null) {
            Snackbar.make(mainLayout, message, Snackbar.LENGTH_SHORT).show()
        }
        if (authentificationLayout != null) {
            Snackbar.make(authentificationLayout, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    fun handleResponseAfterSignIn(requestCode: Int, resultCode: Int, data: Intent?, intent1: Intent){
        var response = IdpResponse.fromResultIntent(data)

        if(requestCode==RC_SIGN_IN){
            if(resultCode== RESULT_OK){

                //UserManager.createUserCreationIntent()
                showNotification("Connexion réussi !")
                startActivity(intent1)

            }
            else{
                if(response==null){
                    showNotification("connexion refusée")
                }
                else if(response.error!=null){
                    if(response.error!!.errorCode==ErrorCodes.NO_NETWORK){
                        showNotification("Connexion internet perdue !")
                    }
                    else if(response.error!!.errorCode==ErrorCodes.UNKNOWN_ERROR){
                        showNotification("Erreur inconnue '^^")
                    }
                }
            }
        }

    }
}