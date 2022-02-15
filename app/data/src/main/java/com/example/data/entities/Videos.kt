package com.example.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
class Videos(@PrimaryKey @ColumnInfo(name = "word") val word: String)