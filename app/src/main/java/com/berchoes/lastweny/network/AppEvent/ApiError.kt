package com.berchoes.lastweny.network.AppEvent


class ApiError {

    var statusCode: Int = 0
    var errorMessage: String? = null

    constructor(statusCode: Int, errorMessage: String) {
        this.statusCode = statusCode
        this.errorMessage = errorMessage
    }

    constructor()

}