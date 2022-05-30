package com.assignment.radiusagentdemo.room

class Repository (private val db: FacilityDatabase) {

    suspend fun insert(item: FacilityItems) = db.getFacilityDao().insert(item)
    suspend fun delete(item: FacilityItems) = db.getFacilityDao().deleteItem(item)
    suspend fun nukeFacilityTable() = db.getFacilityDao().nukeTable()

    fun allFacilityItems() = db.getFacilityDao().getAllFacilityItems()
    fun allFacilityItemsCount() = db.getFacilityDao().getAllFacilityItemsCount()
    fun getItemsCount() = db.getFacilityDao().getItemsCount()
    fun getOptionsCountFromFacility(facilityName: String) = db.getFacilityDao().getOptionsCountFromFacility(facilityName)
    fun getFacilityNameFromId(facilityId: String) = db.getFacilityDao().getFacilityNameFromId(facilityId)
    fun getFacilitiesFromId(facilityId: String) = db.getFacilityDao().getFacilitiesFromId(facilityId)
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