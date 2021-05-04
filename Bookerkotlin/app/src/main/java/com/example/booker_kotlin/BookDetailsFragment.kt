package com.example.booker_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import org.w3c.dom.Text
import java.text.DecimalFormat

class BookDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_book_details, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar?.title = "Book Details"

        val args = BookDetailsFragmentArgs.fromBundle(requireArguments())

        val titleTextView = view.findViewById<TextView>(R.id.book_detail_title)
        val authorsTextView = view.findViewById<TextView>(R.id.book_detail_authors)
        val ratingView = view.findViewById<TextView>(R.id.book_detail_rating)
        val imageView = view.findViewById<ImageView>(R.id.book_detail_img)

        val rating = args.rating.toDouble()

        titleTextView.text = args.titleText
        authorsTextView.text = args.authorsText

        var textRating = "N.A."
        if(rating != -1.0) {
            textRating = getFormattedRating(rating)
        }
        ratingView.text = textRating

        Glide.with(requireContext()).load(args.thumbnailImg)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(imageView)
        // Inflate the layout for this fragment
        return view
    }

    fun getFormattedRating(rating: Double?): String{
        var formatter: DecimalFormat = DecimalFormat("0.0")
        return formatter.format(rating)
    }
}