package com.berchoes.lastweny.ui.Activity

import android.app.Application
import com.google.gson.GsonBuilder
import com.squareup.otto.Bus
import com.squareup.otto.ThreadEnforcer
import com.squareup.picasso.Picasso
import com.berchoes.lastweny.network.Service.PhotoService
import com.berchoes.lastweny.network.Service.RecentPhotosRestApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PhotoGalery : Application() {

    val bus: Bus
        get() {
            if (mBus == null) {
                mBus = Bus(ThreadEnforcer.ANY)
            }
            return mBus as Bus
        }

    override fun onCreate() {
        super.onCreate()
        val context = applicationContext
        val retrofit = createRetrofitObject(ROOT_URL)
        bus.register(this)
        bus.register(
            PhotoService(
                bus,
                retrofit.create<RecentPhotosRestApi.GetRecentImages>(RecentPhotosRestApi.GetRecentImages::class.java)
            )
        )
        val picasso = Picasso.Builder(context)
            .build()
        Picasso.setSingletonInstance(picasso)
    }

    private fun createRetrofitObject(url: String): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    companion object {

        private var mBus: Bus? = null
        const val ROOT_URL = "https://www.flickr.com/services/rest/"
        const val API_KEY = "b713fe4dd5aecfd9b111d03eea0d9304"
    }

}
