package uqac.dim.partysurvivor

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.lang.UCharacter
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.util.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import kotlin.collections.HashMap
import android.widget.Toast
import androidx.core.view.children
import com.google.android.material.bottomnavigation.BottomNavigationView
import uqac.dim.partysurvivor.*
import kotlin.reflect.typeOf


class TestAddImage : AppCompatActivity() {

    //permission user
    val RC_IMAGE_PERMS = 100
    val RC_CHOOSE_PHOTO = 200
    val REQUEST_IMAGE_CAPTURE = 123
    val REQUEST_ID_READ_WRITE_PERMISSION = 99
    val PERMS:String = Manifest.permission.READ_EXTERNAL_STORAGE


    //lateinit var imageView: ImageView
    lateinit var buttonTakePicture: Button
    lateinit var addIngredient: Button
    lateinit var submit: Button
    lateinit var editName: EditText
    lateinit var editRecette: EditText
    lateinit var editDetails: EditText
    var uploadImageTerminate: Boolean = false


    var btnSelect: Button? = null
    var btnUpload: Button? = null

    // view for image view
    private var imageView: ImageView? = null
    private var imageLink: String? = null

    // Uri indicates, where the image will be picked from
    private var filePath: Uri? = null

    private var cheminImageName: String? = null

    // request code
    private val PICK_IMAGE_REQUEST = 22

    // instance for firebase storage and StorageReference
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_add_image)

        supportActionBar?.setTitle(R.string.AddDrink)



        val actionBar: ActionBar?
        actionBar = supportActionBar
        val colorDrawable = ColorDrawable(
            Color.parseColor("#0F9D58")
        )
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(colorDrawable)
        }

        checkPermissionAccessGallery()

        // initialise views
        btnSelect = findViewById(R.id.btnChoose)

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        // on pressing btnSelect SelectImage() is called
        btnSelect?.setOnClickListener(View.OnClickListener {
            //askPermission()
            SelectImage() })

        // on pressing btnUpload uploadImage() is called
        btnUpload?.setOnClickListener(View.OnClickListener {  })


        //formulaire
        imageView = findViewById(R.id.imageView2)



        //Formulaire d'ajout de coktail
        submit = findViewById(R.id.addCoktail)
        addIngredient = findViewById(R.id.addIngredient)
        editName = findViewById(R.id.editName)
        editRecette = findViewById(R.id.editRecette)
        editDetails = findViewById(R.id.editDetails)
        var ingredientContainer: LinearLayout = findViewById(R.id.editTextContainer)

        var listEditIngredient: ArrayList<EditText> = ArrayList()

        addIngredient.setOnClickListener({
            var edit = EditText(this)
            ingredientContainer.addView(edit)
        })


        submit.setOnClickListener {
            //r??cup??ration et validation des champs du formulaire
            var name: String = editName.getText().toString()
            var recette: String = editRecette.getText().toString()
            var details: String = editDetails.getText().toString()

            var listIngredient: String = ""
            var ingredientbool = false

            for (index in 0 until (ingredientContainer as ViewGroup).childCount) {
                val nextChild = (ingredientContainer as ViewGroup).getChildAt(index) as EditText
                if(nextChild.getText().toString() != "" && nextChild.getText().toString() != null){
                    listIngredient += nextChild.getText().toString()+"\n"
                    ingredientbool =true
                }
                System.out.println(" voici l'ingredient : " + nextChild.getText().toString())
            }

            System.out.println(name)
            System.out.println(recette)
            System.out.println(details)
            System.out.println(listEditIngredient.size)
            if((name == null || name == "") || (recette == "" || recette == null) || !ingredientbool || (details =="" || details == null)){
                Toast
                    .makeText(
                        this,
                        "Attention il faut remplir tout les champs du formulaire avec au moins 1 ingr??dient !",
                        Toast.LENGTH_LONG
                    )
                    .show()
                System.out.println("il manque quelque chose")
            }
            else{
                uploadImage()
                if(uploadImageTerminate){
                    writeNewCoktail(name, details, recette, listIngredient , "url")
                    uploadImageTerminate = false;
                }
            }
            reinitialisation(
                editName,
                editDetails,
                editRecette,
                listEditIngredient,
                ingredientContainer
            )
        }

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.selectedItemId = R.id.ic_5
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_1 -> {
                    val a = Intent(this@TestAddImage, ChoixFavoris::class.java)
                    startActivity(a)
                }
                R.id.ic_2 -> {
                    val a = Intent(this@TestAddImage, ChoixJeu::class.java)
                    startActivity(a)
                }
                R.id.ic_3 -> {
                    val b = Intent(this@TestAddImage, FeaturedDrink::class.java)
                    startActivity(b)
                }
                R.id.ic_4 -> {
                    val b = Intent(this@TestAddImage, MainActivityAlcoolMenu::class.java)
                    startActivity(b)
                }
                R.id.ic_5 -> {
                }
            }
            false
        }

    }

    //demande de permission ?? l'user pour r??cup??rer une image de sa galerie
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    fun checkPermissionAccessGallery(){
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            btnSelect?.setEnabled(false)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                0
            )
        }
    }




    fun askPermission(){
        var readPermission:Int =  ActivityCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)
        var writePermission:Int =  ActivityCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(writePermission!=PackageManager.PERMISSION_GRANTED || readPermission!=PackageManager.PERMISSION_GRANTED){
            this.requestPermissions(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQUEST_ID_READ_WRITE_PERMISSION
            )
            return
        }

    }




    fun writeNewCoktail(name: String, details: String, recette: String, listIngredient: String, imageUrl: String){

        //r??cup??ration de l'objet coktail
        var newCoktail: Coktail = Coktail()

        newCoktail.recette = recette
        newCoktail.ingredient = listIngredient
        newCoktail.detailsCoktail = details
        newCoktail.coktailName = name


        //ajout du coktail ?? la bdd


        val database = FirebaseDatabase.getInstance()
        val ref1 = database.getReference("coktail")

        //recup??ration de l'url de l'image et ajout ?? la bdd
        val ref = storageReference
            ?.child(
                "imageCoktail/"
                        + cheminImageName
            )

        if (ref != null) {
            if(filePath!=null){
                ref.putFile(filePath!!)
                    .addOnSuccessListener {
                        val result = it.metadata!!.reference!!.downloadUrl;
                        result.addOnSuccessListener {
                            imageLink = it.toString()
                            newCoktail.imageUrl = imageLink

                            var coktailMap: HashMap<String, Coktail> = HashMap<String, Coktail>()
                            coktailMap.put(UUID.randomUUID().toString(), newCoktail)
                            ref1.updateChildren(coktailMap as Map<String, Any>)
                        }
                    }
            }
            else{
                Toast
                    .makeText(
                        this,
                        "Attention il faut uploader une image pour votre coktail",
                        Toast.LENGTH_LONG
                    )
                    .show()
            }
        }

    }

    //r??initialise les champs du formulaire
    fun reinitialisation(name: EditText, details: EditText, recette: EditText, listEditIngredient: ArrayList<EditText>, layout: LinearLayout){
        name.setText("")
        details.setText("")
        recette.setText("")
        listEditIngredient.clear()
        for(edit in listEditIngredient){
            layout.removeView(edit)
        }
    }



    // Select Image method
    private fun SelectImage() {


        var dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setTitle(R.string.Access)

        dialog.setPositiveButton(R.string.Yes,
            DialogInterface.OnClickListener { dialog, which -> // Write your code here to execute after dialog
                // Defining Implicit Intent to mobile gallery
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST)

            })

        dialog.setNegativeButton(R.string.No, DialogInterface.OnClickListener { dialog, which ->
            var intent: Intent = Intent(this, FeaturedDrink::class.java)
            startActivity(intent)
            Toast.makeText(getApplicationContext(),
                "You cant add a new coktail if you don t take a picture", Toast.LENGTH_SHORT)
                .show();
        })
        dialog.show()

    }

    // Override onActivityResult method
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )
        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {

            // Get the Uri of data
            filePath = data.data
            try {
                // Setting image on image view using Bitmap
                val bitmap = MediaStore.Images.Media
                    .getBitmap(
                        contentResolver,
                        filePath
                    )
                imageView!!.setImageBitmap(bitmap)
                imageView!!.getLayoutParams().height = 500;
            } catch (e: IOException) {
                // Log the exception
                e.printStackTrace()
            }

        }
    }

    // UploadImage method
    private fun uploadImage() {
        System.out.println("Upload en cours")
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            cheminImageName = UUID.randomUUID().toString()

            // Defining the child of storageReference
            val ref = storageReference
                ?.child(
                    "imageCoktail/"
                            + cheminImageName
                )

            // adding listeners on upload
            // or failure of image
            if (ref != null) {
                ref.putFile(filePath!!)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast
                            .makeText(
                                this@TestAddImage,
                                "Image Uploaded!!",
                                Toast.LENGTH_SHORT
                            )
                            .show()

                    }
                    .addOnFailureListener { e -> // Error, Image not uploaded
                        progressDialog.dismiss()
                        Toast
                            .makeText(
                                this@TestAddImage,
                                "Failed " + e.message,
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                    .addOnProgressListener { taskSnapshot ->

                        // Progress Listener for loading
                        // percentage on the dialog box
                        val progress = ((100.0
                                * taskSnapshot.bytesTransferred
                                / taskSnapshot.totalByteCount))
                        progressDialog.setMessage(
                            ("Uploaded "
                                    + progress.toInt() + "%")
                        )
                    }
                uploadImageTerminate = true
            }
        }
    }
}