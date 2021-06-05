package oc.android_exercice.sequence1_todo

import com.google.gson.annotations.SerializedName

class ListeToDo(
        @SerializedName("label")
        var titreListeToDo: String = "Nouvelle Liste",
        @SerializedName("items")
        var items: MutableList<ItemToDo> = mutableListOf<ItemToDo>(),
        @SerializedName("id")
        var id: Int
) {
    override fun toString(): String {
        return "titreListeToDo='$titreListeToDo'"
    }

    fun rechercherItems(descriptionItem: String): ItemToDo? {
        val itemTrouve: ItemToDo? = items.firstOrNull { it.description == descriptionItem }
        return itemTrouve
    }

    fun ajouterItem(descriptionItem: String){
        items.add(ItemToDo(descriptionItem))
    }
}