package com.nmt.film4life.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nmt.film4life.R
import com.nmt.film4life.base.BaseFragment
import com.nmt.film4life.databinding.FragmentDownloadBinding

class DownloadFragment : BaseFragment() {
    private lateinit var binding: FragmentDownloadBinding

    companion object {
        fun newInstance(): DownloadFragment {
            val fragment = DownloadFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_download,container,false)
        return binding.root
    }
    override fun init() {

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