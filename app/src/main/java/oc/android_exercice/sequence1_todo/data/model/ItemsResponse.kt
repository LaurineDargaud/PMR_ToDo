package oc.android_exercice.sequence1_todo.data.model

import com.google.gson.annotations.SerializedName
import oc.android_exercice.sequence1_todo.data.model.ItemToDo
import oc.android_exercice.sequence1_todo.data.source.remote.api.ItemResponse

data class ItemsResponse(
    @SerializedName("items")
    val items: List<ItemResponse>
)