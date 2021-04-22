package com.example.booker_kotlin

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class BookViewModel: ViewModel() {
    val _bookList = MutableLiveData<List<Book>>()

    val bookList: LiveData<List<Book>>
    get() {return _bookList}

    fun getBooks(url: String){
//        BookApi.retrofitService.getBooksResponse(url).enqueue(object: Callback<BaseModel> {
//            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
////                jsonResponse.value = response.body().toString()
////                var bookLis: List<Book>? = response.body()
////                Log.e("viewModel", "books are ${response.body().toString()}")
//
//                var baseModel: BaseModel? = response.body()
//                var books: MutableList<Book> = mutableListOf()
//                if(baseModel != null) {
//                    for (item in baseModel.items) {
////                        var book: Book = item.volumeInfo
////                        Log.e("VIEWMODEL", "Book is actually $book")
//                        books.add(item.volumeInfo)
//                    }
//                }
//                Log.e("VIEWMODEL", "BookList is actually $books")
//
//            }
//
//            override fun onFailure(call: Call<BaseModel>, t: Throwable) {
//                t.printStackTrace()
//                Log.e("ViewModel", "Failure occured ${t.message} ")
//
//            }
//        })

        viewModelScope.launch(Dispatchers.Main) {
            try{
//                var deferredList: Deferred<BaseModel> = BookApi.retrofitService.getBooksResponse(url)
                var baseModel: BaseModel? = BookApi.retrofitService.getBooksResponse(url)
//                Log.e("VOEMOD", "BaseModel is: $baseModel")

                //BookApi.retrofitService.getBooksResponse(url)

                var books: MutableList<Book> = mutableListOf()

                if(baseModel != null) {
                    if(baseModel.items != null) {
                        for (item in baseModel.items!!) {
//                        var book: Book = item.volumeInfo
//                        Log.e("VIEWMODEL", "Book is actually $book")
                            books.add(item.volumeInfo)
                            Log.e("ViewModel", "Image Links: ${item.volumeInfo.imageLinks.smallThumbnail}")
                        }
                        _bookList.value = books
                    }
                    else{
                        _bookList.value = null
                    }
                }

            } catch (e: Exception) {
                  Log.e("BookViewModel","Failure occured: $e.message")
            }
        }
    }

}