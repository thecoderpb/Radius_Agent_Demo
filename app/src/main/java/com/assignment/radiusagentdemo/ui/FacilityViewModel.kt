package com.assignment.radiusagentdemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.radiusagentdemo.room.FacilityItems
import com.assignment.radiusagentdemo.room.Repository
import kotlinx.coroutines.launch

class FacilityViewModel(private val repository: Repository): ViewModel() {

    fun insert(item: FacilityItems) = viewModelScope.launch {
        repository.insert(item)
    }

    fun delete(item: FacilityItems) = viewModelScope.launch {
        repository.delete(item)
    }

    fun nukeTable() = viewModelScope.launch {
        repository.nukeFacilityTable()
    }

    fun getOptionsCountFromFacility(facilityName: String) = repository.getOptionsCountFromFacility(facilityName )

    fun getFacilitiesFromId(facilityId: String) = repository.getFacilitiesFromId(facilityId)

    fun getFacilityNameFromId(facilityId: String) = repository.getFacilityNameFromId(facilityId)

    fun allFacilityItemCount() = repository.allFacilityItemsCount()
    fun allFacilityItems() = repository.allFacilityItems()
    fun getItemCount() = repository.getItemsCount()
}