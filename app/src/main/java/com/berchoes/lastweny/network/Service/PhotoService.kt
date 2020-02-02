package com.berchoes.lastweny.network.Service

import android.util.Log
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import com.berchoes.lastweny.network.AppEvent.ApiError
import com.berchoes.lastweny.network.AppEvent.AppEvents
import com.berchoes.lastweny.Model.PhotoInfoParent
import com.berchoes.lastweny.Model.PhotosParent
import com.berchoes.lastweny.ui.Activity.PhotoGalery
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PhotoService(
    private val mBus: Bus,
    private val recentImagesRestApi: RecentPhotosRestApi.GetRecentImages,
    private val photoInfoRestApi: PhotoInfoRestApi.GetPhotoInfo
) {
    private val s = "AuthService"

    @Subscribe
    fun getRecentImages(event: AppEvents.RecentImagesRequest) {
        recentImagesRestApi.getRecentImagesResult(PhotoGalery.API_KEY, "json", 1, event.perPage, event.page)
            .enqueue(object :
                Callback<PhotosParent> {
                override fun onResponse(call: Call<PhotosParent>, response: Response<PhotosParent>) {
                    Log.d(
                        s,
                        "ON RESPONSE recent photos: " + response.isSuccessful + " - responsecode: " + response.code() + " - response:" + response.message()
                    )
                    Log.d(s, "CALL URL : " + call.request().url())
                    if (response.isSuccessful) {
                        mBus.post(AppEvents.RecentImagesResponse(response))
                    } else {
                        mBus.post(ApiError(response.code(), ""))
                    }
                }

                override fun onFailure(call: Call<PhotosParent>, t: Throwable) {
                    Log.d(s, "ON FAILURE: " + t.message)
                    t.printStackTrace()
                    mBus.post(ApiError())
                }
            })
    }

    @Subscribe
    fun getPhotoInfo(event: AppEvents.PhotoInfoRequest) {
        Log.d(s, "SERVICE PHOTO ID: " + event.photoId)
        photoInfoRestApi.getPhotoInfo(PhotoGalery.API_KEY, "json", 1, event.photoId).enqueue(object :
            Callback<PhotoInfoParent> {
            override fun onResponse(call: Call<PhotoInfoParent>, response: Response<PhotoInfoParent>) {
                Log.d(
                    s,
                    "ON RESPONSE photoInfo: " + response.isSuccessful + " - responsecode: " + response.code() + " - response:" + response.message()
                )
                Log.d(s, "CALL URL : " + call.request().url())
            }

            override fun onFailure(call: Call<PhotoInfoParent>, t: Throwable) {
                Log.d(s, "ON FAILURE: " + t.message)
                t.printStackTrace()
                mBus.post(ApiError())
            }
        })
    }
}
