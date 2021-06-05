package oc.android_exercice.sequence1_todo.data.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("pseudo")
    val pseudo: String,
    @SerializedName("pass")
    val motDePasse: String)