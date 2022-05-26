package com.assignment.radiusagentdemo.room

class FacilityRepository (private val db: FacilityDatabase) {

    suspend fun insert(item: FacilityItems) = db.getFacilityDao().insert(item)
    suspend fun delete(item: FacilityItems) = db.getFacilityDao().deleteItem(item)
    suspend fun nukeFacilityTable() = db.getFacilityDao().nukeTable()

    fun allFacilityItems() = db.getFacilityDao().getAllFacilityItems()
    fun allFacilityItemsCount() = db.getFacilityDao().getAllFacilityItemsCount()
    fun updateFacilityEntries(facilityName:String,
                              optionsName:String,
                              optionsIcon:String,
                              optionsId: String,
                              facilityId:String)
    = db.getFacilityDao().updateFacilityEntries(facilityName,
        optionsName,
        optionsIcon,
        optionsId,
        facilityId)

    suspend fun insert(item: ExclusionItems) = db.getExclusionsDao().insert(item)
    suspend fun delete(item: ExclusionItems) = db.getExclusionsDao().deleteItem(item)
    suspend fun nukeExclusionTable() = db.getExclusionsDao().nukeTable()

    fun allExclusionItems() = db.getExclusionsDao().getAllExclusionItems()
    fun allExclusionItemsCount() = db.getExclusionsDao().getAllExclusionItemsCount()

}