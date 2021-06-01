package oc.android_exercice.sequence1_todo

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShowListActivity : AppCompatActivity() {

    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)
        itemAdapter = ItemAdapter(mutableListOf<ItemToDo>())

        //Initilialisation du recyclerview affichant la liste des items de la todo
        var rvTodoList: RecyclerView = findViewById(R.id.rvTodoList)
        rvTodoList.adapter = itemAdapter
        rvTodoList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //Implémentation de l'ajout d'un item à la todo
        var btnAddToDoItem: Button = findViewById(R.id.buttonAddToDoItem)

        //Pb: Ne permet d'ajouter qu'une tâche
        btnAddToDoItem.setOnClickListener {
            var toDoDescription: EditText = findViewById(R.id.editTextAddToDoItem)
            var toDoTitle: String = toDoDescription.text.toString()
            if (toDoTitle.isNotEmpty()) {
                var item = ItemToDo(toDoTitle)
                itemAdapter.addTodo(item)
                toDoDescription.text.clear()
            }
        }


    }
}