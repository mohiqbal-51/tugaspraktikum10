package com.iqbal.databaseroom

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class AppIdea(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    var title: String,
    var language: String
) {
    override fun toString(): String {
        return "AppIdea(uid=$uid, title='$title', language='$language')"
    }
}

@Dao
public interface AppIdeaDao {

    @Query("select * from AppIdea")
    fun fetchAll(): LiveData<List<AppIdea>>

    @Insert
    suspend fun insert(arg: AppIdea)

    @Update
    suspend fun update(arg: AppIdea)

    @Delete
    suspend fun delete(arg: AppIdea)

}