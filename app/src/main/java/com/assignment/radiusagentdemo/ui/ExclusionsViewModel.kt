package com.assignment.radiusagentdemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.radiusagentdemo.room.ExclusionItems
import com.assignment.radiusagentdemo.room.FacilityItems
import com.assignment.radiusagentdemo.room.FacilityRepository
import kotlinx.coroutines.launch

class ExclusionsViewModel(private val repository: FacilityRepository): ViewModel() {
    fun insert(item: ExclusionItems) = viewModelScope.launch {
        repository.insert(item)
    }

    fun delete(item: ExclusionItems) = viewModelScope.launch {
        repository.delete(item)
    }

    fun nukeTable() = viewModelScope.launch {
        repository.nukeExclusionTable()
    }
}