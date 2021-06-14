package oc.android_exercice.sequence1_todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ListeToDo(
    @PrimaryKey
    val id : Int,
    val titreListeToDo : String,
    val items : MutableList<ItemToDo> = mutableListOf<ItemToDo>()
)