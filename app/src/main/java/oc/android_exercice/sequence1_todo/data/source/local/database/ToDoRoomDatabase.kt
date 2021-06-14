package oc.android_exercice.sequence1_todo.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import oc.android_exercice.sequence1_todo.data.model.ListeToDo

@Database(
    entities = [
        ListeToDo::class
    ],
    version = 1
)
abstract class ToDoRoomDatabase : RoomDatabase() {
    abstract fun listDao(): ListDao
}