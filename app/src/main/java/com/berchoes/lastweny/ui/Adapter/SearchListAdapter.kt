package com.berchoes.lastweny.ui.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.berchoes.lastweny.ui.Activity.ShowPhotoActivity
import com.berchoes.lastweny.Model.Photo
import com.berchoes.lastweny.Model.Photos
import com.berchoes.lastweny.R
import com.berchoes.lastweny.Util.Utils
import java.util.ArrayList


@Suppress("NAME_SHADOWING")
class SearchListAdapter(
    private val context: Context,
    private val recyclerView: RecyclerView,
    private val photos: Photos?,
    private val loadMoreCallback: LastItemVisible?
) :
    RecyclerView.Adapter<SearchListAdapter.DataObjectHolder>() {

    private var data: ArrayList<Photo>? = null

    class DataObjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val image: ImageView

        init {
            image = itemView.findViewById(R.id.image) as ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataObjectHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_row_item, parent, false)
        view.setOnClickListener { view ->
            val intent = Intent(view.context, ShowPhotoActivity::class.java)
            intent.putExtra("PHOTOS", photos)
            intent.putExtra("POSITION", recyclerView.getChildLayoutPosition(view))
            view.context.startActivity(intent)
        }
        return DataObjectHolder(view)
    }

    init {
        data = photos!!.getPhotoArrayList()
    }

    fun add(data: Photo) {
        if (this.photos == null) {
            this.data = ArrayList<Photo>()
        }
        this.data!!.add(data)
        val position = this.data!!.size - 1
        notifyItemInserted(position)
    }

    fun addData(data: ArrayList<Photo>) {
        if (this.data == null) {
            this.data = data
        } else {
            this.data!!.addAll(data)
        }
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return if (data == null) {
            0
        } else data!!.size
    }

    fun getItem(position: Int): Photo {
        return data!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {
        val item = getItem(position)
        val path =
            Utils.getImageUrl(item.getFarm(), item.getServer(), item.getId(), item.getSecret(), Utils.SMALL_240)
        Picasso.with(context).load(path).fit().centerCrop().into(holder.image)

        if (position == itemCount - 2) {
            loadMoreCallback?.loadMoreData(position)
        }
    }

    interface LastItemVisible {
        fun loadMoreData(lastVisibleItemIndex: Int)
    }
}
