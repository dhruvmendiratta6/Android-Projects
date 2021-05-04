package com.example.booker_kotlin
import com.squareup.moshi.Json

data class BaseModel(
    val items: List<Item>? = null,
    val totalItems: Int
)

data class Item(
    val volumeInfo: Book
)

data class Book(
    val title: String,
    val authors: List<String> = listOf(""),
    @Json(name = "averageRating") val rating: Double = -1.0,
    val imageLinks: ImageLinks? = null
)

data class ImageLinks(
    val smallThumbnail: String?
)