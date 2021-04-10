package com.example.booker_kotlin

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.HttpUrl
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    private var bookListView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bookListView = findViewById(R.id.list) as ListView
        var editText: EditText = findViewById(R.id.edit_text) as EditText
        var goButton: Button = findViewById(R.id.go_button) as Button

        var name: String
        goButton.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {
                name = editText.text.toString()
                val isConnected = checkConnection()

//                Log.d("mainactivity", "name is $name")
//                val stringUrl =
//                    "https://www.googleapis.com/books/v1/volumes?q=inauthor:stephen%20hawking&maxResults=20"

                if (isConnected) {
                    val networkUtils = NetworkUtils()
                    val stringUrl = getUrl(name)
                    networkUtils.getBooksFromApi(
                        stringUrl,
                        Listener(WeakReference(this@MainActivity))
                    )
                }
                else{
                    Toast.makeText(this@MainActivity, "Not Connected to Internet", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun setAdapter(books: List<Book>) {
        val adapter1 = BookAdapter(this, 0, books)
        val bookView = findViewById<ListView>(R.id.list)
        bookView.adapter = adapter1
    }

    inner class Listener(activity: WeakReference<Activity>) : OnRequestCompleteListener{

        var booki :List<Book>? = null
        val _activity = activity

        val activity1 = this@MainActivity

        override fun onSuccess(books: List<Book>) {
            if(_activity.get() != null){
                val activity = _activity.get()!!

                activity.runOnUiThread(Runnable {
                    this.booki = books
//                    Log.d("mainActivity", "books in listener UI thread ${this.booki}")
                    setAdapter(booki!!)
                });
            }
//            Log.d("mainActivity", "books in listener ${this.booki}")   //null
        }

        override fun onError() {
            TODO("Not yet implemented")
        }
    }

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

    fun checkConnection():Boolean{
        var connected = false
        val connectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.getState() == NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true
        }
        else{
            connected = false
        }
        return connected
    }

    fun getUrl(name:String) :String{

        var sharedPrefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(
                this@MainActivity
            )
        var searchPref: String? = sharedPrefs.getString(
            getString(R.string.list_pref_key), getString(
                R.string.author_pref
            )
        )
        var httpUrlBuilder: HttpUrl.Builder = HttpUrl.Builder()
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
        var httpUrl: HttpUrl = httpUrlBuilder.build()

        var stringUrl = httpUrl.toString()
//                Log.d("MainActivity", "stringUrl = $stringUrl")

        return stringUrl
    }
}

