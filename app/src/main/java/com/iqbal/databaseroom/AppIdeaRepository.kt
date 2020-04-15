package com.iqbal.databaseroom

import androidx.lifecycle.LiveData

class AppIdeaRepository(private val appIdeaDao: AppIdeaDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allIdeas: LiveData<List<AppIdea>> = appIdeaDao.fetchAll()

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.
    suspend fun insert(arg: AppIdea) {
        appIdeaDao.insert(arg)
    }

    suspend fun update(arg: AppIdea) {
        appIdeaDao.update(arg)
    }

    suspend fun delete(arg: AppIdea) {
        appIdeaDao.delete(arg)
    }
}