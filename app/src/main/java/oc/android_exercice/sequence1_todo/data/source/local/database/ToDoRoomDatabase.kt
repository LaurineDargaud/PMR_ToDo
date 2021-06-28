package oc.android_exercice.sequence1_todo.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import oc.android_exercice.sequence1_todo.data.model.ListeToDo
import oc.android_exercice.sequence1_todo.data.model.ProfilListeToDo

@Database(
    entities = [
        ListeToDo::class,
        ProfilListeToDo::class
    ],
    version = 1
)
abstract class ToDoRoomDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun listDao(): ListDao
}