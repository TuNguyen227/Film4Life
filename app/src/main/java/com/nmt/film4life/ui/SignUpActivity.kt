package com.nmt.film4life.ui


import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nmt.film4life.R
import com.nmt.film4life.base.BaseActivity
import com.nmt.film4life.databinding.ActivitySignUpBinding
import com.nmt.film4life.viewmodel.SignUpViewModel
import org.koin.android.ext.android.inject

class SignUpActivity : BaseActivity() {
    private lateinit var binding : ActivitySignUpBinding
    private val viewModel:SignUpViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }
    override fun init() {

    }

    override fun setUpUI() {

    }

    override fun setListener() {
        with(binding){
            btnSignUp.setOnClickListener{
                if(viewModel.checkingEmailPasswordForSignUp(edtTxtUsername.editableText.toString(),edtTxtPw.editableText.toString(), edtxtConfrimPw.editableText.toString())){
                    viewModel.signUp(edtTxtUsername.editableText.toString(),edtTxtPw.editableText.toString())
                    }
            }
        }
    }

    override fun setObserver() {
        viewModel.signUpObserver.observe(this){
            if(it==true){
                onBackPressed()
            }
        }
    }

    override fun setAnimation() {

    }






}