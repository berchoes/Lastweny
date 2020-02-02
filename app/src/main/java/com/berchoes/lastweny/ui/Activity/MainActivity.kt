package com.berchoes.lastweny.ui.Activity

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.squareup.otto.Subscribe
import com.berchoes.lastweny.ui.Adapter.SearchListAdapter
import com.berchoes.lastweny.network.AppEvent.AppEvents
import com.berchoes.lastweny.Model.PhotosParent
import com.berchoes.lastweny.R
import com.berchoes.lastweny.Util.BaseActivity
import retrofit2.Response

class MainActivity : BaseActivity(), SearchListAdapter.LastItemVisible {
    private var page = 1
    private var isLoading: Boolean = false
    private var searchQuery: String? = null

    override fun setTag() {
        this.TAG = "MainActivity"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val toolbarTitle = toolbar.findViewById(R.id.toolbarTitle) as TextView
        setSupportActionBar(toolbar)
        toolbarTitle.text = "All Activity"
        handleIntent(getIntent())
        page = 1
        app.bus.register(this)
        loadMoreItems(page, null)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(getIntent())
    }

    @SuppressLint("SetTextI18n")
    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            searchQuery = intent.getStringExtra(SearchManager.QUERY)
            val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
            toolbarTitle.text = "Images with $searchQuery tag"
            page = 0
            isLoading = true

        }
    }

    private fun loadMoreItems(page: Int, query: String?) {
        isLoading = true
        if (query == null) {
            app.bus.post(AppEvents.RecentImagesRequest(page, PER_PAGE))
        }
    }

    override fun onRestart() {
        super.onRestart()
        page = 1
        app.bus.register(this)
    }

    override fun onStop() {
        super.onStop()
        app.bus.unregister(this)
    }


    @Subscribe
    fun onLoadRecentImagesResults(response: AppEvents.RecentImagesResponse?) {
        if (response != null) {
            fillRecyclerViewWithImages(response.response)
        }

    }

    private fun fillRecyclerViewWithImages(response: Response<PhotosParent>?) {
        isLoading = false
        val loadingLayout = findViewById(R.id.loadingLayout) as RelativeLayout
        val recyclerView = findViewById(R.id.searchRecylerView) as RecyclerView
        if (response != null) {
            val photos = response.body()!!.photos ?: return
            page = Integer.parseInt(photos.getPage())

            if (page == 1) {
                val layoutManager = GridLayoutManager(this, 2)

                recyclerView.layoutManager = layoutManager
                recyclerView.itemAnimator = DefaultItemAnimator()
                recyclerView.adapter = SearchListAdapter(
                    this@MainActivity.getApplicationContext(),
                    recyclerView,
                    photos,
                    this@MainActivity
                )
                recyclerView.setHasFixedSize(true)
            } else {
                val adapter = recyclerView.adapter as SearchListAdapter?
                adapter!!.addData(photos.getPhotoArrayList())

            }

        } else {
            Toast.makeText(this, "Opps!!!", Toast.LENGTH_SHORT).show()
        }
        loadingLayout.visibility = View.GONE

    }

    override fun loadMoreData(lastVisibleItemIndex: Int) {
        if (!isLoading) {
            loadMoreItems(++page, searchQuery)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = getMenuInflater()
        inflater.inflate(R.menu.menu_main, menu)
        getSystemService(Context.SEARCH_SERVICE) as SearchManager
        return true
    }

    companion object {

        private val PER_PAGE = 20
    }
}
