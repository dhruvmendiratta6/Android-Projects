package com.example.booker_kotlin

import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.booker_kotlin.Database.BookDatabase
import com.example.booker_kotlin.Database.BookDatabaseDao
import com.example.booker_kotlin.Database.BookEntity
import com.example.booker_kotlin.ViewModel.BookViewModel
import com.example.booker_kotlin.ViewModel.BookViewModelFactory
import okhttp3.HttpUrl

class BookFragment : Fragment() {

    private lateinit var bookListView: ListView
    private lateinit var viewModel: BookViewModel
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var noBooksText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_book, container, false)
        // Inflate the layout for this fragment

        val application = requireNotNull(activity).application

        val bookDatabaseDao: BookDatabaseDao = BookDatabase.getInstance(application).bookDatabaseDao

        val bookViewModelFactory = BookViewModelFactory(bookDatabaseDao, application)

        viewModel = ViewModelProvider(this, bookViewModelFactory).get(BookViewModel::class.java)


        viewModel.bookList.observe(viewLifecycleOwner){ bookList ->
            setAdapter(bookList)
//            Log.d("MainAct", "BookList is: $bookList")
            Log.d("MainActivity", "Books from MainActivity: $bookList")

        }

//        viewModel.testFunc()

        bookListView = view.findViewById(R.id.list) as ListView
        val editText: EditText = view.findViewById(R.id.edit_text) as EditText
        val goButton: Button = view.findViewById(R.id.go_button) as Button
        loadingIndicator = view.findViewById(R.id.progress_loader) as ProgressBar
        noBooksText = view.findViewById(R.id.no_books_text_view) as TextView

        goButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val name = editText.text.toString()
                if (!name.isEmpty()) {
                    loadingIndicator.visibility = View.VISIBLE
                    bookListView.visibility = View.GONE
                    noBooksText.visibility = View.GONE
                    val isConnected = checkConnection()
//                val stringUrl =
//                    "https://www.googleapis.com/books/v1/volumes?q=inauthor:stephen%20hawking&maxResults=20"

                    if (isConnected) {
                        getUrl(name)
//                    viewModel.getBooks("https://www.googleapis.com/books/v1/volumes?q=inauthor:stephen%20hawking&maxResults=20")
                    } else {
                        Toast.makeText(
                            context,
                            "Not Connected to Internet",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })

        bookListView.setOnItemClickListener { parent, viewClicked, position, id ->
            val selectedBookEntity: BookEntity = parent.getItemAtPosition(position) as BookEntity
            Log.e("BooksFrag", "item clicked: $selectedBookEntity")
            var title: String? = selectedBookEntity.title
            val authors = selectedBookEntity.authors
            val rating = selectedBookEntity.rating.toFloat()
            val imgLink = selectedBookEntity.thumbnailImage
//            Navigation.createNavigateOnClickListener(R.id.action_bookFragment2_to_bookDetailsFragment2)
            view.findNavController().navigate(BookFragmentDirections
                .actionBookFragment2ToBookDetailsFragment2(
                    titleText = title,
                    authorsText = authors,
                    rating = rating,
                    thumbnailImg = imgLink
                )
            )
        }

        return view
    }

    fun setAdapter(books: List<BookEntity>?) {
        if (books == null){
            loadingIndicator.visibility = View.GONE
            noBooksText.visibility = View.VISIBLE
            bookListView.visibility = View.GONE
            return
        }
        val adapter1 = BookAdapter(context, 0, books)
        val bookView = view?.findViewById<ListView>(R.id.list)
        bookView?.adapter = adapter1
        loadingIndicator.visibility = View.GONE
        bookListView.visibility = View.VISIBLE

    }

    fun getUrl(name:String) {

        val sharedPrefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(
                context
            )
        val searchPref: String? = sharedPrefs.getString(
            getString(R.string.list_pref_key),
            getString(R.string.author_pref)
        )
        val httpUrlBuilder: HttpUrl.Builder = HttpUrl.Builder()
            .scheme("https")
            .host("www.googleapis.com")
            .addPathSegment("books")
            .addPathSegment("v1")
            .addPathSegment("volumes")

        when (searchPref) {
            getString(R.string.author_pref) -> httpUrlBuilder.addEncodedQueryParameter(
                "q",
                "inauthor:" + name
            )
            getString(R.string.title_pref) -> httpUrlBuilder.addEncodedQueryParameter(
                "q",
                "intitle:" + name
            )
        }
        val httpUrl: HttpUrl = httpUrlBuilder.build()

        val stringUrl = httpUrl.toString()
//                Log.d("MainActivity", "stringUrl = $stringUrl")

        viewModel.getBooks(stringUrl)
    }

    fun checkConnection():Boolean{
        var connected = false
        val connectivityManager =
            context?.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.getState() == NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true
        }
        return connected
    }

}