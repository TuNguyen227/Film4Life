package com.nmt.film4life.helper

class Validators {
    companion object
    {
        fun checkInputs(email: String,password:String) : Boolean
        {
            if (email.isEmpty() || password.isEmpty())
                return false
            else
                return true

        }

        fun isEmailValid(email:String) : Boolean
        {
            val isEmailValid= android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()


            return isEmailValid
        }
        fun checkpasswordlength(password:String) :Boolean
        {
            return password.length>8
        }

        fun checkPasswordPatterns(password: String,repeatpassword: String) :Boolean
        {
            return password.contains(Regex("[^a-zA-Z0-9 ]")) && password.contains(Regex("[0-9]")) && password.contains(Regex("[A-Z]"))
        }

        fun checkPasswordMatchRepeatPassword(password: String,repeatpassword: String) :Boolean
        {
            return password==repeatpassword

        }
    }
}