package com.assignment.radiusagentdemo.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExclusionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ExclusionItems)

    @Delete
    suspend fun deleteItem(item: ExclusionItems)

    @Query("DELETE FROM exclusion_items")
    suspend fun nukeTable()

    @Query("SELECT * FROM exclusion_items")
    fun getAllExclusionItems(): LiveData<List<ExclusionItems>>

    @Query("SELECT COUNT(*) FROM facility_items")
    fun getAllExclusionItemsCount(): LiveData<Int>

}