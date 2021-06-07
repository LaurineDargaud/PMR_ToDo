package oc.android_exercice.sequence1_todo

import com.google.gson.annotations.SerializedName

class ItemToDo(
    @SerializedName("id")
    var id: Int,
    @SerializedName("label")
    var description: String = "Nouvel Item",
    @SerializedName("checked")
    var fait_intValue: Int = 0,
    @SerializedName("url")
    var url: String = ""
) {

    var fait: Boolean = false
        get() = (fait_intValue != 0)

    fun changeFait() {
        fait_intValue = if (fait_intValue == 0) 1 else 0
    }

    override fun toString(): String {
        return "[ id = $id, description='$description', fait = ${fait}, fait_intValue = ${fait_intValue}, url = $url ]"
    }
}