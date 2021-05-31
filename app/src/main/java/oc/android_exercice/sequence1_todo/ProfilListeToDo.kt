package oc.android_exercice.sequence1_todo

@Serializable
class ProfilListeToDo(var login:  String = "", var listes: MutableList<ListeToDo> = mutableListOf<ListeToDo>()) {
    fun ajouteListe(uneListe: ListeToDo) {
        listes.add(uneListe)
    }
}