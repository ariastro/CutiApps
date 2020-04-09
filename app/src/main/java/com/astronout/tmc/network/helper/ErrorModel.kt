package com.astronout.tmc.network.helper


import com.google.gson.annotations.SerializedName
import java.util.*

data class ErrorModel(
    @SerializedName("last_update") val lastUpdate: Objects,
    @SerializedName("message") val message: Message,
    @SerializedName("status_code") val statusCode: String
) {
    data class Message(
        @SerializedName("code")
        val code: Int,
        @SerializedName("errors")
        val errors: List<String>
    )
}