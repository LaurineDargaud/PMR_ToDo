package oc.android_exercice.sequence1_todo

import com.google.gson.annotations.SerializedName

class ItemToDo(
        @SerializedName("id")
        var id: Int,
        @SerializedName("label")
        var description: String = "Nouvel Item",
        @SerializedName("checked")
        var fait: Boolean = false,
        @SerializedName("url")
        var url: String = "") {
    fun changeFait(){
        if (fait){
            fait = false
        }
        else{
            fait = true
        }
    }
    override fun toString(): String {
        return "description='$description'"
    }
}