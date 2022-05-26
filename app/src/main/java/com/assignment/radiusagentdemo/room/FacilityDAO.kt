package com.assignment.radiusagentdemo.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FacilityDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FacilityItems)

    @Delete
    suspend fun deleteItem(item: FacilityItems)

    @Query("DELETE FROM facility_items")
    suspend fun nukeTable()

    @Query("SELECT * FROM facility_items")
    fun getAllFacilityItems(): LiveData<List<FacilityItems>>

    @Query("SELECT COUNT(*) FROM facility_items")
    fun getAllFacilityItemsCount(): LiveData<Int>

    @Query("UPDATE facility_items SET facility_name=:facilityName, options_name=:optionsName, options_icon=:optionsIcon, options_id=:optionsId WHERE facility_id=:facilityId")
    fun updateFacilityEntries(facilityName:String, optionsName:String, optionsIcon:String, optionsId: String, facilityId:String )
}

