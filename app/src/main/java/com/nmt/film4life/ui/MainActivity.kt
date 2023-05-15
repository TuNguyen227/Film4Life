package com.nmt.film4life.ui

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nmt.film4life.R
import com.nmt.film4life.base.BaseActivity
import com.nmt.film4life.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        with(binding.homeViewPaper){
            adapter=ViewPaperAdapter()
            offscreenPageLimit=3
            isUserInputEnabled=false
        }

    }

    override fun setUpUI() {

    }

    override fun setListener() {
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.action_home -> {
                    binding.homeViewPaper.currentItem=0
                }

                R.id.action_download -> {
                    binding.homeViewPaper.currentItem=2
                }
                R.id.action_search -> {
                    binding.homeViewPaper.currentItem=1
                }
            }
            true
        }
        binding.btnAvatar.setOnClickListener {
            startActivity(Intent(this,ProfileActivity::class.java))
            overridePendingTransition(R.anim.enter_right_to_left,R.anim.left_to_right)
        }

    }

    override fun setObserver() {

    }

    override fun setAnimation() {

    }




    inner class ViewPaperAdapter() : FragmentStateAdapter(this){
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> HomeFragment.newInstance()
                1 -> SearchFragment.newInstance()
                else -> DownloadFragment.newInstance()

            }
        }

    }

}