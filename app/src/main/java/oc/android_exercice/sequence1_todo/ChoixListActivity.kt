package oc.android_exercice.sequence1_todo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import oc.android_exercice.sequence1_todo.data.DataProvider


class ChoixListActivity : AppCompatActivity(), ListAdapter.ActionListener {

    private val activityScope = CoroutineScope(
            SupervisorJob() +
                    Dispatchers.Main
    )
    var job : Job? = null

    var hash : String? = null

    private lateinit var listAdapter: ListAdapter

    var sp: SharedPreferences? = null
    private var sp_editor: SharedPreferences.Editor? = null

    lateinit var lists : List<ListeToDo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_list)

        sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp_editor = sp?.edit()

        hash = sp?.getString("hash","")

        // on récupère pseudo de l'user via le bundle de l'intent
        var bundlePseudo = this.intent.extras
        var pseudo = bundlePseudo?.getString("pseudo")
        Log.d("ChoixListActivity", "Pseudo de l'user = $pseudo")

        setupRecyclerView()
        loadAndDisplayLists()

        // configuration du bouton ADD pour l'ajout de liste
        var btnAddList: Button = findViewById(R.id.buttonAddList)
        btnAddList.setOnClickListener {
            val etTitre: EditText = findViewById(R.id.editTextNewList)
            val titre = etTitre.text.toString()
            if (titre.isNotEmpty()) {
                activityScope.launch {
                    try {
                        // on crée une liste de label "titre"
                        var addedList = DataProvider.addListFromApi(hash.toString(),titre)
                        Log.d("ChoixListActivity", "Ajout de la liste $titre")
                        etTitre.text.clear()
                        // on l'ajoute à la recycle view pour l'affichage
                        listAdapter.show(listOf(addedList))

                    } catch (e: Exception) {
                        Log.d("ChoixListActivity", "Erreur à l'ajout de liste  = ${e}")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityScope.cancel()
    }

    private fun showProgress(show: Boolean) {
        val progress = findViewById<View>(R.id.progress)
        val list = findViewById<View>(R.id.rvListes)
        progress.isVisible = show
        list.isVisible = !show
    }

    override fun onItemClicked(position: Int) {
        // au clic sur un item, on redirige vers la ShowListActivity de cet item en passant l'id en Intent
        val id : Int = lists[position!!.toInt()].id
        Log.d("ChoixListActivity", "Clic sur la liste en position $position d'id $id")
        val intentVersShowListActivity: Intent = Intent(this, ShowListActivity::class.java)
                .apply {
                    putExtra("id", id.toString())
                }
        Log.d("ChoixListActivity", "${intentVersShowListActivity}")
        startActivity(intentVersShowListActivity)
    }

    private fun setupRecyclerView() {
        val rvListes: RecyclerView = findViewById(R.id.rvListes)
        listAdapter = ListAdapter(this)
        rvListes.adapter = listAdapter
        rvListes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun loadAndDisplayLists(){
        activityScope.launch {
            showProgress(true)
            try {
                lists = DataProvider.getListsFromApi(hash.toString())
                listAdapter.show(lists)
                Log.d("ChoixListActivity","lists = ${lists}")

            } catch (e: Exception) {
                Log.d("ChoixListActivity","Erreur de chargement = ${e}")
            }
            showProgress(false)
        }
    }
}