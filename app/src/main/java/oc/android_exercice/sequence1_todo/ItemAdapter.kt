package oc.android_exercice.sequence1_todo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ItemAdapter(
        private val actionListener: ActionListener,
        var items: MutableList<ItemToDo>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        Log.d("ItemAdapter", "onCreateViewHolder")
        return ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_layout,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Log.d("ItemAdapter", "onBindViewHolder position $position")
        val currentItem = items[position]
        holder.itemView.apply {
            val textViewItem: TextView = findViewById(R.id.textViewItem)
            val checkBoxItem: CheckBox = findViewById(R.id.checkBoxItem)
            textViewItem.text = currentItem.description
            checkBoxItem.isChecked = currentItem.fait
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(
            itemView: View
    ) : RecyclerView.ViewHolder(itemView){
        init {
            val checkBoxItem = itemView.findViewById<CheckBox>(R.id.checkBoxItem)

            fun changerItem(){
                val itemPosition = adapterPosition
                if (itemPosition != RecyclerView.NO_POSITION) {
                    actionListener.onItemClicked(itemPosition)
                }
            }

            checkBoxItem.setOnClickListener{
                changerItem()
            }

            itemView.setOnClickListener {
                // on change l'affichage de la cb
                checkBoxItem.isChecked = (!checkBoxItem.isChecked)
                changerItem()
            }
        }
    }

    interface ActionListener {
        fun onItemClicked(position: Int)
    }
}
