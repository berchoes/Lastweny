package com.berchoes.lastweny.network.Service

import com.berchoes.lastweny.Model.PhotoInfoParent
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


class PhotoInfoRestApi {

    interface GetPhotoInfo {
        @GET("?&method=flickr.photos.getInfo")
        fun getPhotoInfo(
            @Query("api_key") apiKey: String, @Query("format") format: String, @Query("nojsoncallback") noJsonCallback: Int, @Query(
                "photo_id") photoId: String): Call<PhotoInfoParent>
    }
}
