package oc.android_exercice.sequence1_todo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ShowListActivity : AppCompatActivity(), ItemAdapter.ActionListener {

    private lateinit var itemAdapter: ItemAdapter

    var sp: SharedPreferences? = null
    private var sp_editor: SharedPreferences.Editor? = null

    private lateinit var profilsList : MutableList<ProfilListeToDo>
    private lateinit var profil: ProfilListeToDo
    private lateinit var selected_list: ListeToDo

    var updatedData : String = "{}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)

        sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp_editor = sp?.edit()

        // on récupère pseudo et postion de l'user via le bundle de l'intent
        var bundle = this.intent.extras
        Log.d("ShowListActivity", "Bundle transmis ${bundle}")
        var pseudo = sp?.getString("login", "")
        var position : String? = bundle?.getString("position")
        Log.d("ShowListActivity", "Pseudo de l'user = $pseudo")
        Log.d("ShowListActivity", "Position de la liste = ${position}")

        // on dé-sérialise les profils depuis les shared preferences
        val data: String? = sp?.getString("dataJSON","[]")
        Log.d("ShowListActivity", "Data recuperees depuis SP = $data")
        val listOfProfilsToDo: Type = object : TypeToken<MutableList<ProfilListeToDo?>?>() {}.type
        profilsList = Gson().fromJson(data, listOfProfilsToDo)
        Log.d("ShowListActivity", "Les profils: ${profilsList}")

        // on recherche le profil dans la liste des profils
        profil = ProfilListeToDo("$pseudo")
        for (unProfil in profilsList){
            if (unProfil.login == pseudo){
                profil = unProfil
            }
        }

        // on récupère la liste d'items concernée
        selected_list = profil.listes.get(position!!.toInt())
        Log.d("ShowListActivity", "Selected list: ${selected_list}")

        // on affiche les items de la liste rendue

        itemAdapter = ItemAdapter(this, selected_list.items)
        // itemAdapter = ItemAdapter(this, mutableListOf<ItemToDo>())

        //Initilialisation du recyclerview affichant la liste des items de la todo
        var rvTodoList: RecyclerView = findViewById(R.id.rvTodoList)
        rvTodoList.adapter = itemAdapter
        rvTodoList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //Implémentation de l'ajout d'un item à la todo
        var btnAddToDoItem: Button = findViewById(R.id.buttonAddToDoItem)

        btnAddToDoItem.setOnClickListener {
            var toDoDescription : EditText  = findViewById(R.id.editTextAddToDoItem)
            var toDoTitle : String = toDoDescription.text.toString()
            if(toDoTitle.isNotEmpty()){
                // ajout de l'item dans la liste
                selected_list.ajouterItem(toDoTitle)

                updateJSON()
                toDoDescription.text.clear()
            }
        }
    }

    override fun onItemClicked(position: Int) {
        // au clic sur un item, on change le "fait"
        Log.d("ShowListActivity", "Clic sur l'item position $position")
    }

    fun updateJSON(){
        updatedData = Gson().toJson(profilsList)
        Log.d("ChoixListActivity", "New data: ${updatedData}")

        sp_editor?.putString("dataJSON", updatedData)
        sp_editor?.commit()
    }

}