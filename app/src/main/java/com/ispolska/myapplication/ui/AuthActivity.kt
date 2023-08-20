package com.ispolska.myapplication.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.app.ActivityOptionsCompat
import com.ispolska.myapplication.R
import com.ispolska.myapplication.databinding.ActivityAuthBinding
import com.ispolska.myapplication.utils.Constants.APP_PREFERENCES
import com.ispolska.myapplication.utils.Constants.EMAIL
import com.ispolska.myapplication.utils.Constants.ISLOGINED
import com.ispolska.myapplication.utils.Validator

class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        autoLogin()

        setListeners()
    }

    /* disables focus from EditText if clicked outside of it */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    isInputValid(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun setListeners() {
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    /* registration button clicked */
    private fun registerUser() {
        with(binding) {
            if (isInputValid(etEmail.text.toString(), etPassword.text.toString())) {

                with(preferences) {
                    edit()
                        .putString(EMAIL, etEmail.text.toString())
                        .apply()

                    if (chkRemember.isChecked) {
                        edit()
                            .putBoolean(ISLOGINED, true)
                            .apply()
                    }
                }

                goToMainActivity()

            }
        }
    }

    /* Starts main activity with animation */
    private fun goToMainActivity() {
        startActivity(
            Intent(this@AuthActivity, MainActivity::class.java),
            ActivityOptionsCompat
                .makeCustomAnimation(this, R.anim.slide_start, R.anim.slide_end)
                .toBundle()
        )
        finish()
    }

    /* Automatically pass register screen if user is logined */
    private fun autoLogin() {
        if (preferences.getBoolean(ISLOGINED, false)) {
            goToMainActivity()
        }
    }

    private fun isInputValid(email: String, password: String): Boolean {
        var isValid = true

        // email
        binding.apply {
            if (!Validator().isEmailValid(email)) {
                tilEmail.helperText = getString(R.string.error_email)
                isValid = false
            } else {
                tilEmail.helperText = null
            }

            // password
            if (!Validator().isPassValid(password)) {
                tilPassword.helperText = Validator().formPassErrorText(password)
                isValid = false
            } else {
                tilPassword.helperText = null
            }

            return isValid
        }
    }
}