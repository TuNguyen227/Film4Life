package com.nmt.film4life.utility

import android.app.Application
import android.content.Context
import android.view.Display
import androidx.appcompat.app.AppCompatActivity
import com.nmt.film4life.base.BaseActivity

class AppHelper {
    companion object{
        fun convertToHour(minute:Int):String{
            val hour=minute/60
            val min=minute%60
            return (hour.toString() + "h "+min.toString()+"m")
        }
        fun getScreenWidthAndHeight(actvity: BaseActivity): Display? {
            return actvity.windowManager.defaultDisplay
        }
    }
}