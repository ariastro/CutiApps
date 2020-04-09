package com.astronout.tmc.network.helper

import okhttp3.MediaType
import okhttp3.RequestBody

class RequestHelper {
    companion object{
        fun toRequestBody (value : String) : RequestBody {
            return RequestBody.create(MediaType.parse("text/plain"), value)
        }
    }
}