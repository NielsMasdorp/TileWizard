package com.nielsmasdorp.domain

interface SocketInfoRepository {

    suspend fun getSocketInfo(id: Int): SocketInfo

    suspend fun storeSocketInfo(info: SocketInfo)
}