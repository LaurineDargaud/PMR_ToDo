package oc.android_exercice.sequence1_todo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(
        private val actionListener: ActionListener,
        var listes: MutableList<ListeToDo> = mutableListOf()
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    fun show(posts: List<ListeToDo>) {
        this.listes.addAll(posts)
        notifyDataSetChanged()
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
                    val clickedList = listes[listPosition]
                    actionListener.onItemClicked(listPosition)
                }
            }
        }
    }

    interface ActionListener {
        fun onItemClicked(position: Int)
    }
}