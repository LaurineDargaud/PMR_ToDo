package oc.android_exercice.sequence1_todo.data.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import oc.android_exercice.sequence1_todo.data.model.ItemToDo
import oc.android_exercice.sequence1_todo.data.model.ModifItem

@Dao
interface ModifItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addModifItem(modifItem: ModifItem)

    @Query("SELECT * FROM ModifItem")
    suspend fun getAll():List<ModifItem>
}