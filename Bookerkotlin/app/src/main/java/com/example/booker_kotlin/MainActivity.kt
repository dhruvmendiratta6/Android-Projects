package com.example.booker_kotlin

import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.HttpUrl
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.booker_kotlin.ViewModel.BookViewModel
import com.example.booker_kotlin.ViewModel.BookViewModelFactory
import com.example.booker_kotlin.Database.BookDatabase
import com.example.booker_kotlin.Database.BookDatabaseDao
import com.example.booker_kotlin.Database.BookEntity

class MainActivity : AppCompatActivity() {
//    private lateinit var bookListView: ListView
//    private lateinit var viewModel: BookViewModel
//    private lateinit var loadingIndicator: ProgressBar
//    private lateinit var noBooksText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = this.findNavController(R.id.bookFragment)

        NavigationUI.setupActionBarWithNavController(this, navController)


//
//        val application = requireNotNull(this).application
//
//        val bookDatabaseDao: BookDatabaseDao = BookDatabase.getInstance(application).bookDatabaseDao
//
//        val bookViewModelFactory = BookViewModelFactory(bookDatabaseDao, application)
//
//        viewModel = ViewModelProvider(this, bookViewModelFactory).get(BookViewModel::class.java)
//
//
//        viewModel.bookList.observe(this){ bookList ->
//            setAdapter(bookList)
////            Log.d("MainAct", "BookList is: $bookList")
//            Log.d("MainActivity", "Books from MainActivity: $bookList")
//
//        }
//
////        viewModel.testFunc()
//
//
//        bookListView = findViewById(R.id.list) as ListView
//        val editText: EditText = findViewById(R.id.edit_text) as EditText
//        val goButton: Button = findViewById(R.id.go_button) as Button
//        loadingIndicator = findViewById(R.id.progress_loader) as ProgressBar
//        noBooksText = findViewById(R.id.no_books_text_view) as TextView
//
//        goButton.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                val name = editText.text.toString()
//                if (!name.isEmpty()) {
//                    loadingIndicator.visibility = View.VISIBLE
//                    bookListView.visibility = View.GONE
//                    noBooksText.visibility = View.GONE
//                    val isConnected = checkConnection()
////                val stringUrl =
////                    "https://www.googleapis.com/books/v1/volumes?q=inauthor:stephen%20hawking&maxResults=20"
//
//                    if (isConnected) {
//                        getUrl(name)
////                    viewModel.getBooks("https://www.googleapis.com/books/v1/volumes?q=inauthor:stephen%20hawking&maxResults=20")
//                    } else {
//                        Toast.makeText(
//                            this@MainActivity,
//                            "Not Connected to Internet",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
//            }
//        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.bookFragment)
        return navController.navigateUp()
    }

//    fun setAdapter(books: List<BookEntity>?) {
//        if (books == null){
//            loadingIndicator.visibility = View.GONE
//            noBooksText.visibility = View.VISIBLE
//            bookListView.visibility = View.GONE
//            return
//        }
//        val adapter1 = BookAdapter(this, 0, books)
//        val bookView = findViewById<ListView>(R.id.list)
//        bookView.adapter = adapter1
//        loadingIndicator.visibility = View.GONE
//        bookListView.visibility = View.VISIBLE
//
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.setting_menu_action) {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
//
//    fun checkConnection():Boolean{
//        var connected = false
//        val connectivityManager =
//            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
//        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.getState() == NetworkInfo.State.CONNECTED ||
//            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.getState() == NetworkInfo.State.CONNECTED) {
//            //we are connected to a network
//            connected = true
//        }
//        else{
//            connected = false
//        }
//        return connected
//    }
//
//    fun getUrl(name:String) {
//
//        val sharedPrefs: SharedPreferences =
//            PreferenceManager.getDefaultSharedPreferences(
//                this@MainActivity
//            )
//        val searchPref: String? = sharedPrefs.getString(
//            getString(R.string.list_pref_key),
//            getString(R.string.author_pref)
//        )
//        val httpUrlBuilder: HttpUrl.Builder = HttpUrl.Builder()
//            .scheme("https")
//            .host("www.googleapis.com")
//            .addPathSegment("books")
//            .addPathSegment("v1")
//            .addPathSegment("volumes")
//
//        when (searchPref) {
//            getString(R.string.author_pref) -> httpUrlBuilder.addEncodedQueryParameter(
//                "q",
//                "inauthor:" + name
//            )
//            getString(R.string.title_pref) -> httpUrlBuilder.addEncodedQueryParameter(
//                "q",
//                "intitle:" + name
//            )
//        }
//        val httpUrl: HttpUrl = httpUrlBuilder.build()
//
//        val stringUrl = httpUrl.toString()
////                Log.d("MainActivity", "stringUrl = $stringUrl")
//
//        viewModel.getBooks(stringUrl)
//    }

}

