package com.nmt.film4life.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nmt.film4life.R


abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setUpUI()
        setObserver()
        setListener()
        setAnimation()
    }
    protected abstract fun init()
    protected abstract fun setUpUI()
    protected abstract fun setListener()
    protected abstract fun setObserver()
    protected abstract fun setAnimation()
    fun goNext(){
        overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_bottom)
    }





}