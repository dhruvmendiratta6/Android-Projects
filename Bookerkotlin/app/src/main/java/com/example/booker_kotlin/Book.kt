package com.example.booker_kotlin
import com.squareup.moshi.Json

data class BaseModel(
//    val authors: String,
//    @Json(name = "averageRating") val rating: Double,
//    val title: String
    val items: List<Item>? = null,
    val totalItems: Int)

data class Item(
    val volumeInfo: Book
)

data class Book(
    val title: String,
    val authors: List<String>,
    @Json(name = "averageRating") val rating: Double = -1.0,
    val imageLinks: ImageLinks
)

data class ImageLinks(
    val smallThumbnail: String?
)