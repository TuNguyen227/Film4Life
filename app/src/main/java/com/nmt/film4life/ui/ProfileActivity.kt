package com.nmt.film4life.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nmt.film4life.R
import com.nmt.film4life.base.BaseActivity
import com.nmt.film4life.databinding.ActivityProfileBinding
import com.nmt.film4life.viewmodel.HomeViewModel
import org.koin.android.ext.android.inject

class ProfileActivity : BaseActivity() {
    private lateinit var binding:ActivityProfileBinding
    private val viewModel: HomeViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=DataBindingUtil.setContentView(this,R.layout.activity_profile)
        super.onCreate(savedInstanceState)

    }

    override fun init() {
        Glide.with(this@ProfileActivity).load(viewModel.getAvatarUri()).diskCacheStrategy(
            DiskCacheStrategy.ALL).into(binding.avatar)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter_left_to_right,R.anim.exit_right_to_left)
    }

    override fun setUpUI() {

    }

    override fun setListener() {

    }

    override fun setObserver() {

    }

    override fun setAnimation() {

    }
}