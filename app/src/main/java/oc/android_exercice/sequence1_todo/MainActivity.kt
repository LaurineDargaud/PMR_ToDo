package oc.android_exercice.sequence1_todo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import oc.android_exercice.sequence1_todo.data.DataProvider.authentificationFromApi
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    //Initialisation des variables
    private lateinit var buttonOK: Button
    private var pseudo: EditText? = null
    private var motDePasse: EditText? = null
    var sp: SharedPreferences? = null
    private var sp_editor: SharedPreferences.Editor? = null

    private val activityScope = CoroutineScope(
            SupervisorJob() +
                    Dispatchers.Main
    )
    var job : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp_editor = sp?.edit()
        //Récupération des éléments graphiques du layout de l'activité dans le code
        buttonOK = findViewById(R.id.buttonOK)
        pseudo = findViewById(R.id.editTextPseudo)
        motDePasse = findViewById(R.id.editTextPassword)

        //Appel à la méthode gérant les clicks sur le buttonOK
        onClickFun()
    }

    override fun onStart() {
        super.onStart()
        val s: String? = sp?.getString("login", "login inconnu")
        pseudo?.setText(s)
    }

    //ERREUR AVEC LA STATUS BAR MANAGER
    override fun onResume() {
        super.onResume()
        //Appel de la méthode vérifiant la connexion à Internet. Débloquage potentiel du bouton OK
        if (isConnectedToInternet()) {
            buttonOK.isEnabled = true
        } else {
            buttonOK.isEnabled=false
            val internetToast : Toast = Toast.makeText(this, "Pas d'accès à Internet. Configurer votre connexion et réouvrir l'app.", Toast.LENGTH_LONG)
            internetToast.show()
        }
    }

    private fun onClickFun() {
        buttonOK!!.setOnClickListener {

            // Stockage du pseudo pour une prochaine connexion
            val nom: String = pseudo?.text.toString()
            sp_editor?.putString("login", nom)
            sp_editor?.commit()

            //Authentification
            val mdp: String = motDePasse?.text.toString()
            //authentificationFromApi(nom, mdp)
            activityScope.launch{
                try{
                    val hash  = authentificationFromApi("pl", "pl")
                    Log.d("MainActivity","hash = $hash")
                } catch(e:Exception){
                    // erreur d'authentification
                }
            }


            //Lancement de l'activité ChoixListActivity en passant la valeur du pseudo
            val intentVersChoixListActivity: Intent = Intent(this, ChoixListActivity::class.java).apply {
                putExtra("pseudo", nom)
            }
            startActivity(intentVersChoixListActivity)

        }
    }

    //Fonction demandant l'authentification à l'API et retournant le hash du token d'identification
    //private fun authentification(nom: String, mdp: String) : Boolean {
        //Si l'authentification se passe bien, retourner vrai et enregistrer le token d'identification dans les préf
        //Si l'authentification échoue, renvoyer false et un toast pour informer du mauvais mdp ou pseudo

    //}


    // affiche le menu ActionBar si la méthode renvoie vrai
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        // R.menu.menu dénote le fichier  res/menu/menu.xml
        return true
    }

    // click sur un item du menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.menu_settings -> {
                //Permet de lancer l'activité SettingsActivity
                val intentVersSettingsActivity = Intent(this, SettingsActivity::class.java)
                startActivity(intentVersSettingsActivity)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    // Méthode testant la connexion à Internet.
    fun isConnectedToInternet () : Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }
}

