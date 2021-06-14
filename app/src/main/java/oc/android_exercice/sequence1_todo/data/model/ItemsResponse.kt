package oc.android_exercice.sequence1_todo.data.source.remote.api

import com.google.gson.annotations.SerializedName
import oc.android_exercice.sequence1_todo.data.model.ItemToDo

data class ItemsResponse(
    @SerializedName("items")
    val items: List<ItemToDo>
)