package com.example.booker_kotlin

import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        title = "Settings"

    }

    class BookPreferenceFragment: PreferenceFragment(), Preference.OnPreferenceChangeListener{
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.settings_layout)

            val listPreference = findPreference(getString(R.string.list_pref_key))
            bindPreferenceSummaryToValue(listPreference)
        }

        override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
            val changedVal: String = newValue.toString()
            preference?.setSummary(changedVal)
//            Toast.makeText(this, "Changed preference to ", Toast.LENGTH_LONG).show()

            return true
        }

        private fun bindPreferenceSummaryToValue(preference: Preference) {
            preference.onPreferenceChangeListener = this
            val preferences = PreferenceManager.getDefaultSharedPreferences(preference.context)
            val preferenceString = preferences.getString(preference.key, "")
            onPreferenceChange(preference, preferenceString)
        }

    }
}