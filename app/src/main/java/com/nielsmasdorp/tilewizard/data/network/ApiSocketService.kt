package com.nielsmasdorp.tilewizard.data.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiSocketService {

    @GET("http://{ip}/api/v1/state")
    suspend fun getSocketState(@Path("ip") ip: String): SocketResponse

    @PUT("http://{ip}/api/v1/state")
    suspend fun updateSocketState(
        @Path("ip") ip: String,
        @Body state: SocketRequest
    ): SocketResponse
}