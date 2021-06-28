package oc.android_exercice.sequence1_todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModifItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var idList: Int? = null,
    var idItem: Int? = null,
    var newCheckValue: Int? = null
)