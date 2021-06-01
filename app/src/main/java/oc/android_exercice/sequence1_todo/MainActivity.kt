package oc.android_exercice.sequence1_todo

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat.startActivity

class MainActivity : AppCompatActivity() {
    //Initialisation des variables
    private var buttonOK: Button? = null
    private var pseudo: EditText? = null
    var sp: SharedPreferences? = null
    private var sp_editor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp_editor = sp?.edit()
        //Récupération des éléments graphiques du layout de l'activité dans le code
        buttonOK = findViewById(R.id.buttonOK)
        pseudo= findViewById(R.id.editTextPseudo)

        //Appel à la méthode gérant les clicks sur le buttonOK
        onClickFun()
    }

    override fun onStart() {
        super.onStart()
        val s: String? = sp?.getString("pseudo", "login inconnu")
        pseudo?.setText(s)

    }

    private fun onClickFun() {
        buttonOK!!.setOnClickListener {

            val nom: String = pseudo?.text.toString()

            // Stockage du pseudo pour une prochaine connexion
            sp_editor?.putString("login", nom)
            sp_editor?.commit()

            //Lancement de l'activité ChoixListActivity en passant la valeur du pseudo
            val intentVersChoixListActivity: Intent = Intent(this, ChoixListActivity::class.java).apply {
                putExtra("pseudo", nom)
        }
           startActivity(intentVersChoixListActivity)

        }
    }


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
}

