package com.nielsmasdorp.tilewizard.data.network

import com.google.gson.annotations.SerializedName

data class SocketRequest(
    @SerializedName("power_on")
    val status: Boolean
)