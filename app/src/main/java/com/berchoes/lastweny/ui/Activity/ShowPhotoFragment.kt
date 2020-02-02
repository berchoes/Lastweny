package com.berchoes.lastweny.ui.Activity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.alexvasilkov.gestures.views.GestureImageView
import com.squareup.otto.Subscribe
import com.squareup.picasso.Picasso
import com.berchoes.lastweny.network.AppEvent.AppEvents
import com.berchoes.lastweny.Model.Photo
import com.berchoes.lastweny.R
import com.berchoes.lastweny.Util.Utils


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FullScreenImageFragment : Fragment() {
    private var photo: Photo? = null
    private var app: PhotoGalery? = null
    private var viewLayout: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            photo = arguments!!.getParcelable(PARAM_PHOTO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater.inflate(R.layout.fullscreen_image_layout, container, false)
        val imgDisplay = this.viewLayout!!.findViewById(R.id.imgDisplay) as GestureImageView
        val btnClose = viewLayout!!.findViewById(R.id.btnClose) as Button
        val path = Utils.getImageUrl(
            photo!!.getFarm(),
            photo!!.getServer(),
            photo!!.getId(),
            photo!!.getSecret(),
            Utils.LARGE_1024
        )
        try {
            Picasso.with(activity!!.applicationContext).load(path).fit().centerInside().error(R.drawable.reload_small)
                .into(imgDisplay)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        btnClose.setOnClickListener {
            activity!!.finish()
        }
        return viewLayout
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        app = activity!!.application as PhotoGalery
        app!!.bus.register(this)
        app!!.bus.post(AppEvents.PhotoInfoRequest(arguments!!.getString(PARAM_PHOTO_ID)))
    }

    override fun onDetach() {
        super.onDetach()
        app!!.bus.unregister(this)
    }

    companion object {
        private const val PARAM_PHOTO_ID = "PHOTO_ID"
        private const val PARAM_PHOTO = "PHOTO"

        fun newInstance(photoId: String, photo: Photo): FullScreenImageFragment {
            val fragment = FullScreenImageFragment()
            val args = Bundle()
            args.putString(PARAM_PHOTO_ID, photoId)
            args.putParcelable(PARAM_PHOTO, photo)
            fragment.arguments = args
            return fragment
        }
    }
}
