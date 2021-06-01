package oc.android_exercice.sequence1_todo

class ListeToDo(var titreListeToDo: String = "Nouvelle Liste", var items: MutableList<ItemToDo> = mutableListOf<ItemToDo>()){
    override fun toString(): String {
        return "titreListeToDo='$titreListeToDo'"
    }

    fun rechercherItems(descriptionItem: String): ItemToDo? {
        val itemTrouve: ItemToDo? = items.firstOrNull{ it.description == descriptionItem}
        return itemTrouve
    }
}