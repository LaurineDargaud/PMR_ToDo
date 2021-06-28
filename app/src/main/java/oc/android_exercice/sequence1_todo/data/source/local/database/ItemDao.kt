package oc.android_exercice.sequence1_todo.data.source.local.database

import androidx.room.*
import oc.android_exercice.sequence1_todo.data.model.ItemToDo

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(items: List<ItemToDo>)

    @Query("SELECT * FROM ItemToDo WHERE idList = :idList")
    suspend fun getItems(idList:String): List<ItemToDo>

    @Update
    suspend fun updateCheckItem(item:ItemToDo)

}