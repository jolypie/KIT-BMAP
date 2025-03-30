package com.example.generovanilesa.view

import android.R

object Icons {
    fun get(name: String): Int {
        return when (name.lowercase()) {
            "mec" -> R.drawable.ic_menu_compass
            "dyka" -> R.drawable.ic_menu_crop
            "stit" -> R.drawable.ic_menu_gallery
            "helma" -> R.drawable.ic_menu_upload
            "brneni" -> R.drawable.ic_menu_manage
            "lekarna" -> R.drawable.ic_menu_help
            else -> R.drawable.ic_delete
        }
    }
}
