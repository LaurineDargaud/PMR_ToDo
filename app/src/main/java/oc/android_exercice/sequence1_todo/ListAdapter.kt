package oc.android_exercice.sequence1_todo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(var listes: MutableList<ListeToDo>) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

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
        val currentItem= listes[position]
        holder.itemView.apply {
            val textViewItem : TextView = findViewById(R.id.textViewListName)
            textViewItem.text = currentItem.titreListeToDo
        }
    }

    override fun getItemCount(): Int = listes.size

    class ListViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView)

    //interface OnListListener {
    //    fun onListClick(position: Int)
    //}

    interface ActionListener {
        fun onItemClicked(position: Int)
    }
}