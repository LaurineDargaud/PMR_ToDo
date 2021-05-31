package oc.android_exercice.sequence1_todo

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject

class ChoixListActivity : AppCompatActivity() {
    var sp: SharedPreferences? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_list)
        // on récupère les shared preferences
        sp = PreferenceManager.getDefaultSharedPreferences(this)
        // Affiche la liste des listes de l’utilisateur dont le pseudo a été saisi
        // on va récupérer le pseudo/login dans les shared preferences
        var login: String? = sp?.getString("login","")
        // on génère l'objet ProfileListeToDo de l'utilisateur





    }

}