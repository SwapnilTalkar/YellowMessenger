package com.yellowmessenger.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yellowmessenger.assignment.dataSource.Repository
import javax.inject.Inject

class MainSharedViewModelFactory @Inject constructor(
    private val repository: Repository
) : ViewModelProvider.Factory{

    lateinit var mainSharedViewModel: MainSharedViewModel

    override fun <ViewModelClass : ViewModel> create(
        modelClass: Class<ViewModelClass>
    ): ViewModelClass {
        if (modelClass.isAssignableFrom(MainSharedViewModel::class.java)) {
            if (!this::mainSharedViewModel.isInitialized) {
                mainSharedViewModel = MainSharedViewModel(
                    repository = repository
                )
            }
            return mainSharedViewModel as ViewModelClass
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}