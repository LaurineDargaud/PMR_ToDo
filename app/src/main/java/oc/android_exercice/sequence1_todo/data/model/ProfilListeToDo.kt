package oc.android_exercice.sequence1_todo.data.model

import android.util.Log
import com.google.gson.annotations.SerializedName
import oc.android_exercice.sequence1_todo.data.model.ListeToDo

class ProfilListeToDo(
    @SerializedName("login")
    var login: String = "login inconnu"
    @SerializedName("listes")
    // var listes: MutableList<ListeToDo> = mutableListOf<ListeToDo>()
) {

    override fun toString(): String {
        return "ProfilListeToDo(login='$login',listes='$listes)"
    }

}