package oc.android_exercice.sequence1_todo

import android.util.Log
import com.google.gson.annotations.SerializedName

class ProfilListeToDo(
        @SerializedName("login")
        var login: String = "login inconnu",
        @SerializedName("listes")
        var listes: MutableList<ListeToDo> = mutableListOf<ListeToDo>()
) {
    fun ajouteListe(uneListe: ListeToDo) {
        listes.add(uneListe)
        Log.d("ProfilListToDo","Ajout liste ${uneListe.titreListeToDo}")
    }

    override fun toString(): String {
        return "ProfilListeToDo(login='$login',listes='$listes)"
    }

}