package com.example.booker_kotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import java.text.DecimalFormat

class BookAdapter(context: Context,
                  @LayoutRes private val layoutResource: Int,
                  private val books: List<Book>) : ArrayAdapter<Book>(context, layoutResource, books) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var current_book: Book? = getItem(position)
        var listItemView: View = convertView ?: LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false)

        var titleTextView: TextView? = listItemView.findViewById(R.id.title_text)
        var authorTextView: TextView? = listItemView.findViewById(R.id.author_text)
        var ratingTextView: TextView? = listItemView.findViewById(R.id.rating_text)

        var rating : Double? = current_book?.rating
        var textRating = "N.A"
        if(rating != -1.0) {
            textRating = getFormattedRating(rating)
        }

        titleTextView?.text = current_book?.title
        authorTextView?.text = current_book?.author
        ratingTextView?.text = textRating

        return listItemView
    }

    fun getFormattedRating(rating: Double?): String{
        var formatter: DecimalFormat = DecimalFormat("0.0")
        return formatter.format(rating)
    }

}