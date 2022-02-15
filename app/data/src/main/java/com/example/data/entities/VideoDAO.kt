package com.example.data.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Videos>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Videos)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}