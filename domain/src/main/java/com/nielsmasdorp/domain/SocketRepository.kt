package com.nielsmasdorp.domain

interface SocketRepository {

    suspend fun getSocketState(ip: String, current: SocketState): SocketState

    suspend fun updateSocketState(ip: String, current: SocketState, new: SocketState): SocketState
}