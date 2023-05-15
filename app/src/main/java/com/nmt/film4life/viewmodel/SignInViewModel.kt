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

class SignInViewModel(repository: Repository) : BaseViewModel(repository) {

    var signInObserver= MutableLiveData<Boolean>()

    fun setUpGoogleSignInClient(context: Context): GoogleSignInClient {
        var gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val Client= GoogleSignIn.getClient(context,gso)
        return Client
    }

    fun registerCredential(idToken:String?){
        val credentical= GoogleAuthProvider.getCredential(idToken,null)
        FirebaseAuth.getInstance().signInWithCredential(credentical).addOnCompleteListener{

            if (it.isSuccessful)
            {
                signInObserver.postValue(true)
            }
            else
                Log.d("tagv",it.exception?.message.toString())
        }
    }
    fun checkingEmailPasswordForSignIn(email:String,password:String) : Boolean
    {
        val isInputEmpty= Validators.checkInputs(email,password)

        if (!isInputEmpty)
        {

            errorData.postValue("Please type your email and password")
            return false
        }

        return  isInputEmpty
    }

    fun signIn(email: String,password: String)
    {
        repository.getFirebaseAuth().signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful)
            {
                signInObserver.postValue(true)
            }
        }

    }
}