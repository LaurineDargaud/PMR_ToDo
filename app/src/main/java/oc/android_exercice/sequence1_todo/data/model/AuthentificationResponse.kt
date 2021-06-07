package oc.android_exercice.sequence1_todo.data.model

import com.google.gson.annotations.SerializedName

data class AuthentificationResponse(
    @SerializedName("hash")
    val hash: String
)