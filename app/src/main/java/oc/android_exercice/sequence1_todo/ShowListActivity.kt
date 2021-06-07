package oc.android_exercice.sequence1_todo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import oc.android_exercice.sequence1_todo.data.DataProvider

class ShowListActivity : AppCompatActivity(), ItemAdapter.ActionListener {

    private val activityScope = CoroutineScope(
        SupervisorJob() +
                Dispatchers.Main
    )
    var job: Job? = null

    var hash: String? = null
    var idList: String? = null

    lateinit var clickedItem: ItemToDo

    private lateinit var itemAdapter: ItemAdapter

    var sp: SharedPreferences? = null
    private var sp_editor: SharedPreferences.Editor? = null

    lateinit var items: MutableList<ItemToDo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)

        sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp_editor = sp?.edit()

        hash = sp?.getString("hash", "")

        // on récupère l'id de la liste via le bundle de l'intent
        var bundle = this.intent.extras
        Log.d("ShowListActivity", "Bundle transmis ${bundle}")
        idList = bundle?.getString("id")
        Log.d("ShowListActivity", "idList = $idList")

        setupRecyclerView()
        loadAndDisplayItems()

        //Implémentation de l'ajout d'un item à la todo
        var btnAddToDoItem: Button = findViewById(R.id.buttonAddToDoItem)

        btnAddToDoItem.setOnClickListener {
            var toDoDescription: EditText = findViewById(R.id.editTextAddToDoItem)
            var toDoTitle: String = toDoDescription.text.toString()
            if (toDoTitle.isNotEmpty()) {
                activityScope.launch {
                    try {
                        // ajouter item dans la liste
                        var addedItem =
                            DataProvider.addItemFromApi(hash.toString(), idList!!, toDoTitle)
                        Log.d("ShowListActivity", "Ajout de l'item' $toDoTitle")
                        toDoDescription.text.clear()
                        // ajouter item à la recycle view pour l'affichage
                        items.add(addedItem)
                        itemAdapter.update(items)
                    } catch (e: Exception) {
                        Log.d("ShowListActivity", "Erreur à l'ajout d'item : ${e}")
                    }
                }

            }
        }
    }

    override fun onItemClicked(position: Int) {
        // au clic sur un item, on change le "fait"
        clickedItem = items[position.toInt()]
        Log.d(
            "ShowListActivity",
            "Clic sur l'item $clickedItem position $position de la liste $idList"
        )
        activityScope.launch {
            try {
                DataProvider.updateCheckItemFromApi(
                    hash.toString(),
                    idList!!,
                    clickedItem.id.toString(),
                    clickedItem.fait_intValue.toString()
                )
                clickedItem.changeFait()
            } catch (e: Exception) {
                Log.e("ShowListActivity", "Erreur de changement d'état d'item : ${e}")
            }
        }
    }

    private fun setupRecyclerView() {
        //Initilialisation du recyclerview affichant la liste des items de la todo
        var rvTodoList: RecyclerView = findViewById(R.id.rvTodoList)
        itemAdapter = ItemAdapter(this)
        rvTodoList.adapter = itemAdapter
        rvTodoList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun showProgress(show: Boolean) {
        val progress = findViewById<View>(R.id.progress)
        val list = findViewById<View>(R.id.rvTodoList)
        progress.isVisible = show
        list.isVisible = !show
    }

    private fun loadAndDisplayItems() {
        activityScope.launch {
            showProgress(true)
            try {
                // on récupère les items de la liste concernée et on les ajouté à la RecycleView
                items = DataProvider.getItemsFromApi(hash.toString(), idList.toString())
                itemAdapter.show(items)
                Log.d("ShowListActivity", "items = ${items}")

            } catch (e: Exception) {
                Log.d("ShowListActivity", "Erreur de chargement = ${e}")
            }
            showProgress(false)
        }
    }

    // affiche le menu ActionBar si la méthode renvoie vrai
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        // R.menu.menu dénote le fichier  res/menu/menu.xml
        return true
    }

    // Gestion du clic sur un item du menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.menu_settings -> {
                //Permet de lancer l'activité SettingsActivity
                val intentVersSettingsActivity = Intent(this, SettingsActivity::class.java)
                startActivity(intentVersSettingsActivity)
            }
            R.id.menu_logout -> {
                //Retour à l'activité Main
                val intentVersMainActivity = Intent(this, MainActivity::class.java)
                startActivity(intentVersMainActivity)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}