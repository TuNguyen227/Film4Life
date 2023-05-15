package com.nmt.film4life.ui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.nmt.film4life.R
import com.nmt.film4life.base.BaseActivity
import com.nmt.film4life.databinding.ActivitySignInBinding
import com.nmt.film4life.viewmodel.SignInViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import java.lang.Exception

class SignInActivity : BaseActivity() {
    private lateinit var binding : ActivitySignInBinding
    private  val viewModel: SignInViewModel by inject()

    private val gsc by lazy {
        viewModel.setUpGoogleSignInClient(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashTheme)
    }
    override fun init() {

    }

    override fun setUpUI() {

    }

    override fun setListener() {
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.btnSignIn.setOnClickListener {
            if(viewModel.checkingEmailPasswordForSignIn(binding.editTxtUsername.editableText.toString(),binding.editTxtPw.editableText.toString()))
            {
                viewModel.signIn(binding.editTxtUsername.editableText.toString(),binding.editTxtPw.editableText.toString())
            }

        }
        with(binding){
            btnGoogle.setOnClickListener {
                gsc.signOut()
                signIn()
            }
        }
    }

    override fun setObserver() {
        with(viewModel){
            signInObserver.observe(this@SignInActivity){
                if (it==true){
                    startActivity(Intent(this@SignInActivity,MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    override fun setAnimation() {

    }

    private fun signIn()
    {
        resultLauncher.launch(gsc.signInIntent)
    }

    private var resultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val task=GoogleSignIn.getSignedInAccountFromIntent(it.data)
        if (task.isSuccessful){
            try {
                val account=task.getResult(ApiException::class.java)
                viewModel.registerCredential(account.idToken)
            } catch (e : Exception){

            }
        }
        else{
            Log.d("tag",task.exception?.message.toString())
        }
    }





}