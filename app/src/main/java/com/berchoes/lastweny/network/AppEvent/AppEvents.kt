package com.berchoes.lastweny.network.AppEvent

import com.berchoes.lastweny.Model.PhotosParent
import retrofit2.Response


class AppEvents {

    //class SearchImageRequest

    class SearchImageResponse(val response: Response<PhotosParent>)

    class RecentImagesRequest(val page: Int, val perPage: Int)

    class RecentImagesResponse(val response: Response<PhotosParent>)

    class PhotoInfoRequest(val photoId: String)

}
