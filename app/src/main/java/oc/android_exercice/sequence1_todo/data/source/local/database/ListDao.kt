package oc.android_exercice.sequence1_todo.data.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import oc.android_exercice.sequence1_todo.data.model.ListeToDo

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(lists: List<ListeToDo>)

    @Query("SELECT * FROM LISTETODO")
    suspend fun getLists(): List<ListeToDo>
}