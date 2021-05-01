package com.example.booker_kotlin.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.booker_kotlin.BaseModel
import com.example.booker_kotlin.BookApi
import com.example.booker_kotlin.Repository.BookRepository
import com.example.booker_kotlin.Database.BookDatabaseDao
import com.example.booker_kotlin.Database.BookEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(var bookDatabaseDao: BookDatabaseDao, application: Application): ViewModel() {
    val _bookList = MutableLiveData<List<BookEntity>>()

    val bookRepository: BookRepository = BookRepository((bookDatabaseDao))

    val bookList: LiveData<List<BookEntity>>
    get() {return _bookList}

    fun testFunc() {

        val book1 = BookEntity(title = "book1", authors = "author1", rating = 1.0, thumbnailImage = "idontknow1")
        val book2 = BookEntity(title = "book2", authors = "author2", rating = 1.0, thumbnailImage = "idontknow2")
        val book3 = BookEntity(title = "book3", authors = "author3", rating = 1.0, thumbnailImage = "idontknow3")

        val bookEntityList = listOf<BookEntity>(book1, book2, book3)
//
        var updatedBooks: List<BookEntity> = bookEntityList.map { it.copy() }

        updatedBooks[0].title = "updated Book Title 1 twicw !"
        updatedBooks[1].title = "updated Book Title 2 twicw!"
        updatedBooks[2].title = "updated Book Title 3 twicw!"

        viewModelScope.launch(Dispatchers.Main){
//            bookRepository.insertUpdateDelete(bookEntityList, updatedBooks, bookEntityList[0].bookId)
            bookRepository.deleteAllBooks()
            bookRepository.insertBooks(bookEntityList)
            var books: List<BookEntity> = bookRepository.getAllBooks()

            Log.d("viewmodel 222222"," \n\n $books \n \n  \n")

            books[0].title = "ipdated ti e"
            books[1].title = "updated it"
            books[2].title = "updaets it"

            bookRepository.updateBooks(books)
            books = bookRepository.getAllBooks()
            Log.d("viewmodel 222222"," \n\n $books \n \n  \n")



            _bookList.value = books
            Log.d("ViewModel", "Books from viewmodel: $books")
        }
    }

    fun getBooks(url: String){

        var books: MutableList<BookEntity>
        viewModelScope.launch(Dispatchers.Main) {
            books = getBooksFromRetrofit(url)

            var bookDBList: List<BookEntity> = bookRepository.getAllBooks()

            var bookSize: Int = books.size
            var bookDBSize: Int = bookDBList.size

            if(bookSize < bookDBSize){
                for(i in 0..(bookSize-1)){
                    bookDBList[i].title = books[i].title
                    bookDBList[i].authors = books[i].authors
                    bookDBList[i].rating = books[i].rating
                    bookDBList[i].thumbnailImage = books[i].thumbnailImage
                }
                var delBooks: MutableList<BookEntity> = mutableListOf()
                for(i in bookSize..(bookDBSize-1)){
                    delBooks.add(bookDBList[i])
                }
                bookRepository.updateBooks(bookDBList)
                bookRepository.deleteBooks(delBooks)
            }
            else if(bookSize == bookDBSize){
                for(i in 0..(bookSize-1)){
                    bookDBList[i].title = books[i].title
                    bookDBList[i].authors = books[i].authors
                    bookDBList[i].rating = books[i].rating
                    bookDBList[i].thumbnailImage = books[i].thumbnailImage
                }
                bookRepository.updateBooks(bookDBList)
            }
            else {
                for(i in 0..(bookDBSize-1)){
                    bookDBList[i].title = books[i].title
                    bookDBList[i].authors = books[i].authors
                    bookDBList[i].rating = books[i].rating
                    bookDBList[i].thumbnailImage = books[i].thumbnailImage
                }
                bookRepository.updateBooks(bookDBList)

                var insertBooks: MutableList<BookEntity> = mutableListOf()
                for(i in bookDBSize..(bookSize-1)){
                    insertBooks.add(books[i])
                }
                bookRepository.insertBooks(insertBooks)
            }
            _bookList.value = bookRepository.getAllBooks()
        }
    }

    suspend fun getBooksFromRetrofit(url: String): MutableList<BookEntity>{

        var bookEntityList: MutableList<BookEntity> = mutableListOf()

            try{
                var baseModel: BaseModel? = BookApi.retrofitService.getBooksResponse(url)
//                Log.d("ViewModel", "URL = $url")

                if(baseModel != null) {
                    if(baseModel.items != null) {
                        for (item in baseModel.items!!) {
//                        var book: Book = item.volumeInfo
//                            books.add(item.volumeInfo)
                            var title: String = item.volumeInfo.title
                            var authorsList = item.volumeInfo.authors
                            var authors = ""
                            for(author in authorsList){
                                authors += author + "\n"
                            }
                            var rating: Double = item.volumeInfo.rating
                            var imageThumbnailUrl:String? = item.volumeInfo.imageLinks?.smallThumbnail

                            var bookEntity = BookEntity(title = title, authors = authors, rating = rating, thumbnailImage = imageThumbnailUrl)
                            bookEntityList.add(bookEntity)
                        }
                    }
                    else{
                        _bookList.value = null
                    }
                }

            } catch (e: Exception) {
                Log.e("BookViewModel","Failure occured: $e.message")
            }

            var booksLength: Int = bookEntityList.size
            Log.d("viewmodel", "\n\n bookentitylist length = $booksLength \n\n")

        return bookEntityList
    }

    override fun onCleared() {
        Log.d("viewmodel", "cleared")
        super.onCleared()
    }
}