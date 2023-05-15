package com.nmt.film4life.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.nmt.film4life.R
import com.nmt.film4life.base.BaseViewModel
import com.nmt.film4life.helper.Validators
import com.nmt.film4life.repository.Repository
import kotlin.math.log

class SignUpViewModel(repository: Repository) : BaseViewModel(repository) {

    var signUpObserver= MutableLiveData<Boolean>()

    fun checkingEmailPasswordForSignUp(email:String,password:String,confirmPassword:String) : Boolean
    {
        val isInputEmpty= Validators.checkInputs(email,password)
        val isMatchingPw=Validators.checkPasswordMatchRepeatPassword(password,confirmPassword)
        if (!isInputEmpty)
        {

            errorData.postValue("Please type your email and password")
            return false
        }

        return  (isInputEmpty && isMatchingPw)
    }

    fun signUp(email: String,password: String)
    {
        repository.getFirebaseAuth().createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful)
            {
                signUpObserver.postValue(true)
            }
        }

    }
}