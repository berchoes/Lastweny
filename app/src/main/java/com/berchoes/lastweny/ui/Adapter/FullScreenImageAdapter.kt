package com.berchoes.lastweny.ui.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.berchoes.lastweny.ui.Activity.FullScreenImageFragment
import com.berchoes.lastweny.Model.Photo
import java.util.ArrayList


class FullScreenImageAdapter(fm: FragmentManager, private val photoArrayList: ArrayList<Photo>?) :
    FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return photoArrayList?.size ?: 0
    }

    override fun getItem(position: Int): Fragment {
        val photo = photoArrayList!![position]
        return FullScreenImageFragment.newInstance(photo.id, photo)
    }
}
