package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.graphics.drawable.GradientDrawable;

public class EarthquakeAdapter extends ArrayAdapter<EarthQuake> {

//    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();

    public EarthquakeAdapter(Activity context, ArrayList<EarthQuake> earthQuakes){
        super(context, 0, earthQuakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;

        EarthQuake current_quake = getItem(position);

        if(itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }



        TextView magnitudeView = (TextView) itemView.findViewById(R.id.magnitude_text);
        TextView city = (TextView) itemView.findViewById(R.id.city_text);
        TextView secondaryCity = (TextView) itemView.findViewById((R.id.secondary_text));

        String[] location = current_quake.getCity().split("of ", -2);
        String secondary, primary;
        if (location.length == 1){
            secondary = "Near the ";
            primary = location[0];
        }
        else{
            secondary = location[0] + "of";
            primary = location[1];
        }



        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(current_quake.gettimeInMs());

        // Find the TextView with view ID date
        TextView dateView = (TextView) itemView.findViewById(R.id.daydate_text);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);


        // Find the TextView with view ID time
        TextView timeView = (TextView) itemView.findViewById(R.id.time_text);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);

        String formattedMag = formatDecimal(current_quake.getmagnitude());

        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);

        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        magnitudeView.setText(formattedMag);

        city.setText(primary);
        secondaryCity.setText(secondary);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(current_quake.getmagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        return itemView;
    }
    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatDecimal(double magnitude){
        DecimalFormat formatter = new DecimalFormat("0.0");
        String output = formatter.format(magnitude);
        return output;
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeFloor = (int) Math.floor(magnitude);
        int magnitudeColorResourceId;
        switch(magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }


}
