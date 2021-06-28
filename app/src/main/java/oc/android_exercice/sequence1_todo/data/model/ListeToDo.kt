package oc.android_exercice.sequence1_todo.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ListeToDo(
    @PrimaryKey
    val id : Int,
    val titreListeToDo : String,
    var idUser: Int? = null
    // val items : MutableList<ItemToDo> = mutableListOf<ItemToDo>()
)