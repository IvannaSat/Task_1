package com.ispolska.myapplication.ui

import android.content.SharedPreferences
import android.os.Bundle
import com.ispolska.myapplication.R
import com.ispolska.myapplication.databinding.MainLayoutBinding
import com.ispolska.myapplication.utils.Constants.APP_PREFERENCES
import com.ispolska.myapplication.utils.Constants.DELIMITER_AT
import com.ispolska.myapplication.utils.Constants.DELIMITER_DOT
import com.ispolska.myapplication.utils.Constants.EMAIL
import com.ispolska.myapplication.utils.Constants.NAME
import com.ispolska.myapplication.utils.Constants.SURNAME
import com.ispolska.myapplication.utils.extensions.capitalizeFirstChar

class MainActivity : BaseActivity<MainLayoutBinding>(MainLayoutBinding::inflate) {
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        parseEmailToPrefs()
        setNameInTextView()
    }

    private fun parseEmailToPrefs() {
        val email = preferences.getString(EMAIL, null)
        if (email != null) {
            val leftPart = email.split(DELIMITER_AT)[0]
            if (leftPart.contains(DELIMITER_DOT)) {
                val name = leftPart.split(DELIMITER_DOT)[0].capitalizeFirstChar()
                val surname = leftPart.split(DELIMITER_DOT)[1].capitalizeFirstChar()
                preferences.edit()
                    .putString(NAME, name)
                    .putString(SURNAME, surname)
                    .apply()
            }

        } else {
            preferences.edit()
                .putString(NAME, getString(R.string.default_name))
                .putString(SURNAME, getString(R.string.default_lastname))
                .apply()
        }
    }

    private fun setNameInTextView() {
        val name = preferences.getString(NAME, getString(R.string.default_name))
        val lastname = preferences.getString(SURNAME, getString(R.string.default_lastname))
        val fullName = "$name $lastname"
        binding.tvFullName.text = fullName
    }

}