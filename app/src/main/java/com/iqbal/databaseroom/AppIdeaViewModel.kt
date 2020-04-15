package com.iqbal.databaseroom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class AppIdeaViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: AppIdeaRepository
    // LiveData gives us updated words when they change.
    val allIdeas: LiveData<List<AppIdea>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val appIdeaDao = AppIdeaDatabase.getDatabase(application).appIdeaDao()
        repository = AppIdeaRepository(appIdeaDao)
        allIdeas = repository.allIdeas
    }

    // The implementation of insert() is completely hidden from the UI.
    // We don't want insert to block the main thread, so we're launching a new
    // coroutine. ViewModels have a coroutine scope based on their lifecycle called
    // viewModelScope which we can use here.
    fun insert(arg: AppIdea) = viewModelScope.launch {
        repository.insert(arg)
    }

    fun update(arg: AppIdea) = viewModelScope.launch {
        repository.update(arg)
    }

    fun delete(arg: AppIdea) = viewModelScope.launch {
        repository.delete(arg)
    }
}
