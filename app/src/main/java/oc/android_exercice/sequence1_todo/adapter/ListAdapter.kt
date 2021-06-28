package oc.android_exercice.sequence1_todo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import oc.android_exercice.sequence1_todo.data.model.ListeToDo
import oc.android_exercice.sequence1_todo.R
import oc.android_exercice.sequence1_todo.data.model.ItemToDo

class ListAdapter(
    private val actionListener: ActionListener,
    var listes: MutableList<ListeToDo> = mutableListOf()
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    fun show(listsToShow: List<ListeToDo>) {
        this.listes.addAll(listsToShow)
        notifyDataSetChanged()
    }

    fun add(list: ListeToDo){
        listes.add(list)
        notifyItemInserted(listes.size-1)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        Log.d("ListAdapter", "onCreateViewHolder")
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        Log.d("ListAdapter", "onBindViewHolder position $position")
        val currentItem = listes[position]
        holder.itemView.apply {
            val textViewItem: TextView = findViewById(R.id.textViewListName)
            textViewItem.text = currentItem.titreListeToDo
        }
    }

    override fun getItemCount(): Int = listes.size

    inner class ListViewHolder(
        listView: View
    ) : RecyclerView.ViewHolder(listView) {
        init {
            listView.setOnClickListener {
                val listPosition = adapterPosition
                if (listPosition != RecyclerView.NO_POSITION) {
                    actionListener.onItemClicked(listPosition)
                }
            }
        }
    }

    interface ActionListener {
        fun onItemClicked(position: Int)
    }
}