package com.berchoes.lastweny.Util

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.berchoes.lastweny.ui.Activity.PhotoGalery


abstract class BaseActivity : AppCompatActivity() {

    protected var TAG = "BaseActivity"
    protected lateinit var app: PhotoGalery


    protected abstract fun setTag()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as PhotoGalery
        setTag()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            finish()

        }
    }
}
