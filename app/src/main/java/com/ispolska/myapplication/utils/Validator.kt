package com.ispolska.myapplication.utils

import android.util.Patterns

class Validator {

    fun isEmailValid(email: String): Boolean {
        return (email.isNotEmpty()) and (Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    fun isPassValid(pass: String): Boolean {
        if (!pass.matches(
                Regex( /* at least 1 lowercase, 1 uppercase, 1 number, length 8+ */
                    "^(?=.*[${Constants.PASSWORD_LOWERCASE_LETTERS}])" +
                            "(?=.*[${Constants.PASSWORD_UPPERCASE_LETTERS}])" +
                            "(?=.*[${Constants.PASSWORD_NUMBERS}])" +
                            "[${Constants.PASSWORD_LOWERCASE_LETTERS}$" +
                            "{Constants.PASSWORD_UPPERCASE_LETTERS}${Constants.PASSWORD_NUMBERS}]" +
                            "{${Constants.PASSWORD_LENGTH},}$"
                )
            )
        ) {
            return false
        }
        return true
    }

    /* forms error text of each incorrect case in password independently */
    fun formPassErrorText(password: String): String {

        var errorMessage = ""

        if (password.length < 8) {
            errorMessage += "length ${Constants.PASSWORD_LENGTH}"
        }
        if (!password.contains(Regex("[${Constants.PASSWORD_LOWERCASE_LETTERS}]"))) {
            errorMessage = "${addComma(errorMessage)}${Constants.PASSWORD_LOWERCASE_LETTERS}"
        }
        if (!password.contains(Regex("[${Constants.PASSWORD_UPPERCASE_LETTERS}]"))) {
            errorMessage = "${addComma(errorMessage)}${Constants.PASSWORD_UPPERCASE_LETTERS}"
        }
        if (!password.contains(Regex("[${Constants.PASSWORD_NUMBERS}]"))) {
            errorMessage = "${addComma(errorMessage)}${Constants.PASSWORD_NUMBERS}"
        }

        return "$errorMessage required"
    }

    /* adds comma to error message if there is some text before */
    fun addComma(s: String): String {
        if (s.isNotBlank()) {
            return "$s, "
        }
        return s
    }

}