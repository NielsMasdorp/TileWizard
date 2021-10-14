package com.nielsmasdorp.domain

interface SocketRepository {

    suspend fun getSocketState(ip: String): SocketState

    suspend fun updateSocketState(ip: String, state: SocketState): SocketState
}