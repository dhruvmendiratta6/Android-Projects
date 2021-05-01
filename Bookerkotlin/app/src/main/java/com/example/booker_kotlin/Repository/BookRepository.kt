package com.example.booker_kotlin.Repository

import com.example.booker_kotlin.Database.BookDatabaseDao
import com.example.booker_kotlin.Database.BookEntity

class BookRepository(bookDatabaseDao: BookDatabaseDao) {

    var bookDao: BookDatabaseDao

    init{
        this.bookDao = bookDatabaseDao
    }

    suspend fun insertBooks(bookEntitylist: List<BookEntity>){
        bookDao.insertBooks(bookEntitylist)
    }

    suspend fun getAllBooks(): List<BookEntity> {
        return bookDao.getAllBooks()
    }

    suspend fun updateBooks(bookEntityList: List<BookEntity>){
        bookDao.update(bookEntityList)
    }

    suspend fun deleteBooks(bookEntitylist: List<BookEntity>){
        bookDao.deleteBooks(bookEntitylist)
    }

    suspend fun deleteAllBooks(){
        bookDao.deleteAll()
    }

    suspend fun insertUpdateDelete(bookEntitylist: List<BookEntity>, updatedBooks: List<BookEntity>, id: Long){
        bookDao.insertUpdateDelete(bookEntitylist, updatedBooks, id)

    }

//    suspend fun delperti(id: Long){
//        bookDao.delperti(id)
//    }

}