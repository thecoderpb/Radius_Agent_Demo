package com.assignment.radiusagentdemo.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.assignment.radiusagentdemo.ui.Event

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

    @Query("SELECT COUNT(*) FROM facility_items")
    fun getItemsCount(): Int

    @Query("SELECT COUNT(*) FROM facility_items WHERE facility_name=:facilityName")
    fun getOptionsCountFromFacility(facilityName: String) : Int

    @Query("SELECT facility_name FROM facility_items WHERE facility_id=:facilityId")
    fun getFacilityNameFromId(facilityId: String) : LiveData<String>

    @Query("SELECT * FROM facility_items WHERE facility_id=:facilityId")
    fun getFacilitiesFromId(facilityId: String) : LiveData<List<FacilityItems>>

    @Query("UPDATE facility_items SET facility_name=:facilityName, options_name=:optionsName, options_icon=:optionsIcon, options_id=:optionsId WHERE facility_id=:facilityId")
    fun updateFacilityEntries(facilityName:String, optionsName:String, optionsIcon:String, optionsId: String, facilityId:String )
}

