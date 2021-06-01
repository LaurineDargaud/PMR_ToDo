package oc.android_exercice.sequence1_todo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import oc.android_exercice.sequence1_todo.utils.ItemClickSupport




class ChoixListActivity : AppCompatActivity(), ListAdapter.OnListListener {

    private lateinit var listAdapter : ListAdapter
    private var listes = ListeToDo()

    var sp: SharedPreferences? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_list)

        listAdapter = ListAdapter(mutableListOf())

        var rvListes : RecyclerView = findViewById(R.id.rvListes)
        rvListes.adapter=listAdapter
        rvListes.layoutManager=LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        var btnAddList : Button = findViewById(R.id.buttonAddList)
        btnAddList.setOnClickListener {
            val etTitre : EditText = findViewById(R.id.editTextNewList)
            val titre = etTitre.text.toString()
            if (titre.isNotEmpty()){
                val liste = ListeToDo(titre)
                listAdapter.addList(liste)
                etTitre.text.clear()
            }
        }

        //implémetentation de la méthode OnListClick
        @Override
        fun onListClick(position : Int) {
            var intentVersShowListActivity : Intent = Intent(this, ChoixListActivity::class.java)
            intentVersShowListActivity.putExtra("selected_list", listes.items[position].toString())
            startActivity(intent)
        }


        //On récupère pseudo de l'user via le bundle de l'intent
        var bundlePseudo = this.intent.extras
        var pseudo= bundlePseudo?.getString("pseudo")

        // on récupère les shared preferences
        sp = PreferenceManager.getDefaultSharedPreferences(this)

        // Affiche la liste des listes de l’utilisateur dont le pseudo a été saisi


        // on génère l'objet ProfileListeToDo de l'utilisateur





    }

}