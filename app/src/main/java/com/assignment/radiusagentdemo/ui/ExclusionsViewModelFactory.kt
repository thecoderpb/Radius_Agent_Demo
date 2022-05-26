package com.assignment.radiusagentdemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.radiusagentdemo.room.FacilityRepository

class ExclusionsViewModelFactory(private val repository: FacilityRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExclusionsViewModel(repository) as T
    }
}