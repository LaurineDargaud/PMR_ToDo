package oc.android_exercice.sequence1_todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ItemToDo(
    @PrimaryKey
    var id: Int,
    var description: String = "Nouvel Item",
    var fait_intValue: Int = 0,
    // var idUser: Int? = null,
    var idList: Int? = null
) {

    var fait: Boolean = false
        get() = (fait_intValue != 0)

    fun changeFait() {
        fait_intValue = if (fait_intValue == 0) 1 else 0
    }
}