package com.nielsmasdorp.tilewizard.data

import com.nielsmasdorp.domain.SocketRepository
import com.nielsmasdorp.domain.SocketState
import com.nielsmasdorp.domain.SocketState.*
import com.nielsmasdorp.tilewizard.data.network.ApiSocketService
import com.nielsmasdorp.tilewizard.data.network.SocketRequest
import java.lang.Exception

class ApiSocketRepository(private val socketService: ApiSocketService) : SocketRepository {

    override suspend fun getSocketState(ip: String): SocketState {
        return try {
            val state = socketService.getSocketState(ip = ip)
            if (state.status) ON else OFF
        } catch (ex: Exception) {
            UNAVAILABLE
        }
    }

    override suspend fun updateSocketState(ip: String, state: SocketState): SocketState {
        return try {
            val result = socketService.updateSocketState(
                ip = ip,
                state = SocketRequest(status = state == ON)
            )
            if (result.status) ON else OFF
        } catch (ex: Exception) {
            UNAVAILABLE
        }
    }
}