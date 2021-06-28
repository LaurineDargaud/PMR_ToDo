package oc.android_exercice.sequence1_todo.data.source.remote.api

import com.google.gson.annotations.SerializedName

data class ItemResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("label")
    var description: String = "Nouvel Item",
    @SerializedName("checked")
    var fait_intValue: Int = 0
)