package com.example.booker_kotlin

import android.content.Context
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class NetworkUtils {
    companion object{
        var body: String? = null
    }

    var bodyi: String? = null
    var books: List<Book> = listOf()

    fun getBooksFromApi(url: String, callback : OnRequestCompleteListener) {
            val request = Request.Builder()
                .url(url)
                .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    else{
                        bodyi = response.body?.string()
//                        Log.d("TAG", "body from else$bodyi") // gives correct response

                        books = getBooksfromJSON(bodyi)
                        callback.onSuccess(books)

//                        Log.d("TAG", "Books are $books") //gives correct response
                    }
                }
            })
//            Log.d("TAG", "body is ${NetworkUtils.body}")              //null

//            var books = getBooksfromJSON(NetworkUtils.body)
//            Log.d("TAG", "outside books are $books")    //null

        }


        fun getBooksfromJSON(jsonResponse: String?): List<Book> {
            var jsonObject = JSONObject(jsonResponse)
            var items: JSONArray = jsonObject.getJSONArray("items")

            var books: MutableList<Book> = mutableListOf<Book>()

            for (i in 0..items.length()-1) {
                val itemsobj = items.getJSONObject(i)
                val volinfo = itemsobj.getJSONObject("volumeInfo")
                var authors1: JSONArray?
                val title = volinfo.getString("title")
                var rating: Double
                try{
                    rating = volinfo.getDouble("averageRating")
                    authors1 = volinfo.getJSONArray("authors")
                }
                catch(e : JSONException){
                    rating = -1.0
                    authors1 = null
                }
//                Log.d("TAG","$rating")

                var totalAuthors = ""
                if(authors1 != null) {
                    for (j in 0 until authors1
                        .length()) {
                        totalAuthors += authors1.getString(j) + " "
                    }
                }
                val book = Book(totalAuthors, rating, title)
                books.add(book)

            }
            return books
        }
    }

interface OnRequestCompleteListener{
    fun onSuccess(books :List<Book>)
    fun onError()
}



