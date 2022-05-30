package com.assignment.radiusagentdemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.radiusagentdemo.room.ExclusionItems
import com.assignment.radiusagentdemo.room.Repository
import kotlinx.coroutines.launch

class ExclusionsViewModel(private val repository: Repository): ViewModel() {
    fun insert(item: ExclusionItems) = viewModelScope.launch {
        repository.insert(item)
    }

    fun delete(item: ExclusionItems) = viewModelScope.launch {
        repository.delete(item)
    }

    fun nukeTable() = viewModelScope.launch {
        repository.nukeExclusionTable()
    }

    fun allExclusionItemsCount() = repository.allExclusionItemsCount()
    fun allExclusionItems() = repository.allExclusionItems()
}