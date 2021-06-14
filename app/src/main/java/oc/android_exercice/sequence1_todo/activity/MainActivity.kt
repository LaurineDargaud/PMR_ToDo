package oc.android_exercice.sequence1_todo.activity

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
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.*
import androidx.core.view.isVisible
import oc.android_exercice.sequence1_todo.R
import oc.android_exercice.sequence1_todo.data.source.remote.RemoteDataSource
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    //Déclaration des variables
    private lateinit var buttonOK: Button
    private lateinit var tvModeDegrade: TextView
    private var pseudo: EditText? = null
    private var motDePasse: EditText? = null
    var sp: SharedPreferences? = null
    private var sp_editor: SharedPreferences.Editor? = null
    private val activityScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var internetState: Boolean? = null
    private var logout: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Gestion des SP
        sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp_editor = sp?.edit()

        //Récupération des éléments graphiques du layout de l'activité dans le code
        buttonOK = findViewById(R.id.buttonOK)
        pseudo = findViewById(R.id.editTextPseudo)
        motDePasse = findViewById(R.id.editTextPassword)
        tvModeDegrade = findViewById(R.id.textViewModeDegrade)

        // On utilise un bundle pour détecter les déconnexions depuis les activités ChoixList et ShowList
        var bundle = this.intent.extras
        Log.d("bundle Main", "bundle : ${bundle}")
        logout =
            bundle?.getBoolean("logout") == true //test permettant de ne pas avoir de nullPointerException au démarrage
        Log.d("connexion auto", "logout state = $logout")

        //Appel à la méthode gérant les clicks sur le buttonOK
        onClickFun()
    }

    override fun onResume() {
        super.onResume()

        // Récupération de l'état de la connexion à Internet
        internetState = isConnectedToInternet()

        // Appel de appMode, fonctionnant modifiant l'UI pour signifier à l'utilisateur le mode d'utilisation de l'app
        appMode(internetState!!)

        //Pré-remplissage des champs pseudo et mot de passe (si on ne cherche pas à faire une déconnexion)
        val nom: String? = sp?.getString("login", "login inconnu")
        val mdp: String? = sp?.getString("mdp", "mdp inconnu")
        if (logout!!.not()) {
            pseudo?.setText(nom)
            motDePasse?.setText(mdp)
        }

        //Appel de la méthode gérant la connexion automatique
        if (nom != null && mdp != null) {
            autoLogin(nom, mdp, logout!!)
        }
    }

    //Méthode gérant le clic sur le bouton OK (authentification + enregistrement des identifiants dans les SP)
    private fun onClickFun() {
        buttonOK!!.setOnClickListener {

            if (internetState!!) {
                // Gestion de l'authentification à l'API dans une coroutine
                activityScope.launch {
                    try {
                        val nom: String = pseudo?.text.toString()
                        val mdp: String = motDePasse?.text.toString()

                        // En cas de succès, le hash du token d'identification est enregistré dans les SP
                        // et lancement de l'activité ChoixListActivity
                        val hash = RemoteDataSource.authentificationFromApi(nom, mdp)
                        Log.d("MainActivity login", "hash = ${hash}")
                        sp_editor?.putString("hash", hash)
                        sp_editor?.commit()

                        // Stockage du pseudo et du mdp pour une prochaine connexion
                        sp_editor?.putString("login", nom)
                        sp_editor?.putString("mdp", mdp)
                        sp_editor?.commit()

                        val intentVersChoixListActivity: Intent =
                            Intent(this@MainActivity, ChoixListActivity::class.java)
                                .apply { putExtra("internet", true) }
                        startActivity(intentVersChoixListActivity)
                    } catch (e: Exception) {
                        // L'échec de l'authentification se traduit pour l'utilisateur pour un Toast d'erreur
                        Log.d("MainActivity login", "erreur authentification = ${e}")
                        Toast.makeText(
                            this@MainActivity,
                            "Erreur d'authentification",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                // Stockage du pseudoHorsLigne et du mdpHorsLigne pour MAJ BDD
                val nomHL: String = pseudo?.text.toString()
                val mdpHL: String = motDePasse?.text.toString()
                sp_editor?.putString("loginHL", nomHL)
                sp_editor?.putString("mdpHL", mdpHL)
                sp_editor?.commit()

                val internetToast: Toast = Toast.makeText(
                    this,
                    "Lancement de l'app en mode dégradé",
                    Toast.LENGTH_LONG
                )
                internetToast.show()
                val intentVersChoixListActivity: Intent =
                    Intent(this@MainActivity, ChoixListActivity::class.java).apply {
                        putExtra("internet", false)
                    }
                startActivity(intentVersChoixListActivity)
            }
        }
    }

    // Fonction affichant le menu ActionBar (si la méthode renvoie vrai)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        //Instruction pour cacher l'item déconnexion de la MainActivity (l'id de l'item est 1, l'id 0 est pour l'item "Préférences")
        menu.getItem(1).setVisible(false)
        return true
    }

    // Fonction gérant le clic sur un item du menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            // Gestion du clic l'item "Préférences"
            R.id.menu_settings -> {
                //Permet de lancer l'activité SettingsActivity
                val intentVersSettingsActivity = Intent(this, SettingsActivity::class.java)
                startActivity(intentVersSettingsActivity)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Fonction testant la connexion à Internet.
    fun isConnectedToInternet(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    //Fonction informant l'utilisateur du mode d'utilisation de l'app
    fun appMode(internetState: Boolean) {
        if (internetState) {
            buttonOK.text = "OK"
            tvModeDegrade.isVisible = false
        } else {
            buttonOK.text = "Mode dégradé"
            tvModeDegrade.text = "Avertissement : Pas d'accès à Internet\n\n" +
                    "Le lancement de l'application se fera en mode dégradé : \n" +
                    "- l'ajout de nouvelle liste ou de nouvel item est impossible\n " +
                    "- vos modifications seront enregistrées en local\n\n" +
                    "Vérifiez la validité de vos identifiants"
            tvModeDegrade.isVisible = true
        }
    }

    fun autoLogin(nom: String, mdp: String, logout: Boolean) {
        //Connexion automatique
        if (nom != "login inconnu" && mdp != "mdp inconnu" && logout.not() && internetState!!) {
            Log.d("connexion auto", "nom : ${nom} + mdp : ${mdp}")
            buttonOK.performClick()
        }
    }
}

