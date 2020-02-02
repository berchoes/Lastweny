package com.berchoes.lastweny.ui.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.Window
import android.view.WindowManager.LayoutParams.*
import com.berchoes.lastweny.ui.Adapter.FullScreenImageAdapter
import com.berchoes.lastweny.Model.Photos
import com.berchoes.lastweny.R

class ShowPhotoActivity : AppCompatActivity() {

      private val PARAMETER_PHOTOS = "PHOTOS"
      private val PARAMETER_INDEX = "POSITION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            FLAG_FULLSCREEN,
            FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_show_photo)

        val intent = intent
        val viewPager = findViewById<ViewPager>(R.id.pager)
        val photos = intent.getParcelableExtra<Photos>(PARAMETER_PHOTOS)
        val position = intent.getIntExtra(PARAMETER_INDEX, 0)
        val adapter =
            FullScreenImageAdapter(this@ShowPhotoActivity.supportFragmentManager, photos.photoArrayList)
        viewPager.adapter = adapter
        viewPager.currentItem = position
        viewPager.offscreenPageLimit = 1
    }


}
