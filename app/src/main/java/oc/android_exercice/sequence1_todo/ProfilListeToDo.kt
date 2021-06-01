package oc.android_exercice.sequence1_todo

class ProfilListeToDo(var login:  String = "login inconnu", var listes: MutableList<ListeToDo> = mutableListOf<ListeToDo>()) {
    fun ajouteListe(uneListe: ListeToDo) {
        listes.add(uneListe)
    }

}