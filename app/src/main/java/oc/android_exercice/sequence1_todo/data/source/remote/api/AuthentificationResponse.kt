package oc.android_exercice.sequence1_todo.data.source.remote.api

import com.google.gson.annotations.SerializedName

data class AuthentificationResponse(
    @SerializedName("hash")
    val hash: String
)