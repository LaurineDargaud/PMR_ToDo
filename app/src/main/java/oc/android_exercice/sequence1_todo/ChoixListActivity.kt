package oc.android_exercice.sequence1_todo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class ChoixListActivity : AppCompatActivity(), ListAdapter.ActionListener {

    private lateinit var listAdapter : ListAdapter
    private var listes = ListeToDo()
    private lateinit var profil: ProfilListeToDo

    var sp: SharedPreferences? = null
    private var sp_editor: SharedPreferences.Editor? = null

    var updatedData : String = "{}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_list)

        sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp_editor = sp?.edit()

        // on récupère pseudo de l'user via le bundle de l'intent
        var bundlePseudo = this.intent.extras
        var pseudo= bundlePseudo?.getString("pseudo")
        Log.d("ChoixListActivity", "Pseudo de l'user = $pseudo")

        // on dé-sérialise les profils depuis les shared preferences
        sp = PreferenceManager.getDefaultSharedPreferences(this)
        val data: String? = sp?.getString("dataJSON", "{}")
        Log.d("ChoixListActivity", "Data recuperees depuis SP = $data")
        val listOfProfilsToDo: Type = object : TypeToken<MutableList<ProfilListeToDo?>?>() {}.type
        val profilsList: MutableList<ProfilListeToDo> = Gson().fromJson(data, listOfProfilsToDo)
        Log.d("ChoixListActivity", "Les profils: ${profilsList}")

        // on recherche le profil dans la liste des profils
        profil = ProfilListeToDo("$pseudo")
        var isNewProfil : Boolean = true
        for (unProfil in profilsList){
            if (unProfil.login == pseudo){
                profil = unProfil
                isNewProfil = false
                Log.d("ChoixListActivity", "Profil existant : $profil")
            }
        }
        // dans le cas d'un nouveau profil, on update le JSON dans shared preferences
        if (isNewProfil){
            profilsList.add(profil)
            updateJSON(profilsList)
        }

        // on affiche la liste des listes de l’utilisateur concerné en RecycleView

        listAdapter = ListAdapter(profil.listes)

        var rvListes : RecyclerView = findViewById(R.id.rvListes)
        rvListes.adapter=listAdapter
        rvListes.layoutManager=LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        var btnAddList : Button = findViewById(R.id.buttonAddList)
        btnAddList.setOnClickListener {
            val etTitre : EditText = findViewById(R.id.editTextNewList)
            val titre = etTitre.text.toString()
            if (titre.isNotEmpty()){
                val liste = ListeToDo(titre)
                profil.ajouteListe(liste)
                updateJSON(profilsList)
                etTitre.text.clear()
            }
        }

        // Implémentation de la méthode OnListClick
        @Override
        fun onListClick(position : Int) {
            var intentVersShowListActivity : Intent = Intent(this, ChoixListActivity::class.java)
            intentVersShowListActivity.putExtra("selected_list", listes.items[position].toString())
            startActivity(intent)
        }
    }

    override fun onItemClicked(position: Int) {
        Log.d("MainActivity", "onItemClicked $position")
        Toast.makeText(this,position, Toast.LENGTH_LONG).show()
    }

    fun updateJSON(a_list:MutableList<ProfilListeToDo>){
        updatedData = Gson().toJson(a_list)
        Log.d("ChoixListActivity", "New data: ${updatedData}")

        sp_editor?.putString("dataJSON", updatedData)
        sp_editor?.commit()
    }
}