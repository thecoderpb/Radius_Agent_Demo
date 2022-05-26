package com.assignment.radiusagentdemo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FacilityItems::class,ExclusionItems::class], version = 1)
abstract class FacilityDatabase : RoomDatabase() {

    abstract fun getFacilityDao(): FacilityDAO
    abstract fun getExclusionsDao(): ExclusionDAO

    companion object {
        @Volatile
        private var instance: FacilityDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, FacilityDatabase::class.java, "FacilityDatabase.db").build()
    }
}
