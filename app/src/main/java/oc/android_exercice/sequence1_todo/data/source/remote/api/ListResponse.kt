package oc.android_exercice.sequence1_todo.data.source.remote.api

import com.google.gson.annotations.SerializedName
import oc.android_exercice.sequence1_todo.data.model.ItemToDo

data class ListResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("label")
    val titreListeToDo: String = "Nouvelle Liste",
    @SerializedName("items")
    val items: MutableList<ItemToDo> = mutableListOf<ItemToDo>()
)