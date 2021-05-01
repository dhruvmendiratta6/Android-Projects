package com.example.booker_kotlin.Database

import androidx.room.*

@Dao
interface BookDatabaseDao {
    @Insert
    suspend fun insertBooks(bookEntities: List<BookEntity>)

    @Update
    suspend fun update(book: List<BookEntity>)

    @Query("SELECT * FROM book_table")
    suspend fun getAllBooks(): List<BookEntity>

    @Query("DELETE FROM book_table")
    suspend fun deleteAll()

//    @Query("DELETE FROM book_table WHERE bookId= :id")
//    suspend fun delperti(id: Long)

    @Delete
    suspend fun deleteBooks(books: List<BookEntity>)

    @Transaction
    suspend fun insertUpdateDelete(bookEntities: List<BookEntity>, updatedBooks: List<BookEntity>, id: Long){

        deleteAll()

        insertBooks(bookEntities)
        var books = getAllBooks()
//        Log.e("bookdao", "after insertion: $books \n \n")

//        var ide = books[0].bookId
//        Log.e("bookdao", "$ide")
//
        books[0].title = "updatedTITTLELELE"
        books[1].title = "updatedTTTTTTTTTT"
        books[2].title = "updatedOJHIUBOIOI"

        update(books)
        books = getAllBooks()
//        Log.e("bookdao", "after updation: $books\n \n")

//        delperti(ide)

        var book: List<BookEntity> = listOf(books[0], books[1])

        deleteBooks(book)

        books = getAllBooks()
//        Log.e("bookdao", "after deletion: $books \n \n")


    }

}