package oc.android_exercice.sequence1_todo

class ListeToDo(var titreListeToDo: String = "", var items: MutableList<ItemToDo> = mutableListOf<ItemToDo>()){
    fun rechercherItems(descriptionItem: String): ItemToDo? {
        val itemTrouve: ItemToDo? = items.firstOrNull{ it.description == descriptionItem}
        return itemTrouve
    }
}