package com.berchoes.lastweny.Util



object Utils {
    val SMALL_240 = 'm'
    val LARGE_1024 = 'b'

    fun getImageUrl(farmId: String, serverId: String, id: String, secret: String, size: Char): String {
        return String.format("https://farm%s.staticflickr.com/%s/%s_%s_%c.jpg", farmId, serverId, id, secret, size)
    }
}
