package com.example.booker_kotlin.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class BookEntity(

    @PrimaryKey(autoGenerate = true)
    var bookId: Long = 0,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name="authors")
    var authors: String,

    @ColumnInfo(name="rating")
    var rating: Double = -1.0,

    @ColumnInfo(name="thumbnail_image")
    var thumbnailImage: String? = null

)