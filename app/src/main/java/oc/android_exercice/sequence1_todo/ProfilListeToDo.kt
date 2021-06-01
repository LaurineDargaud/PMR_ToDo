package oc.android_exercice.sequence1_todo

import com.google.gson.annotations.SerializedName

class ProfilListeToDo(
        @SerializedName("login")
        var login: String = "login inconnu",
        @SerializedName("listes")
        var listes: MutableList<ListeToDo> = mutableListOf<ListeToDo>()
) {
    fun ajouteListe(uneListe: ListeToDo) {
        listes.add(uneListe)
    }

    override fun toString(): String {
        return "ProfilListeToDo(login='$login',listes='$listes)"
    }

}