package com.rajit.todoapp_cleanarchitecture.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name="is_pinned") val isPinned: Boolean
)
