package com.nielsmasdorp.tilewizard.data

import com.nielsmasdorp.domain.SocketRepository
import com.nielsmasdorp.domain.SocketState
import com.nielsmasdorp.domain.SocketState.*
import com.nielsmasdorp.tilewizard.data.network.ApiSocketService
import com.nielsmasdorp.tilewizard.data.network.SocketRequest
import com.nielsmasdorp.tilewizard.data.network.SocketResponse
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException

class ApiSocketRepository(private val socketService: ApiSocketService) : SocketRepository {

    override suspend fun getSocketState(ip: String, current: SocketState): SocketState {
        return tryWithFallback(
            request = { socketService.getSocketState(ip = ip) },
            current = current
        )
    }

    override suspend fun updateSocketState(
        ip: String,
        current: SocketState,
        new: SocketState
    ): SocketState {
        return tryWithFallback(
            request = {
                socketService.updateSocketState(
                    ip = ip,
                    state = SocketRequest(status = new == ON)
                )
            },
            current = current
        )
    }

    private suspend fun tryWithFallback(
        request: suspend () -> SocketResponse,
        current: SocketState
    ): SocketState {
        return try {
            if (request().status) ON else OFF
        } catch (ex: ConnectException) {
            // Can't find IP, must not be connected to same WiFi
            UNAVAILABLE
        } catch (ex: SocketException) {
            // Cancelled request, return old state
            current
        } catch (ex: IOException) {
            // Cancelled request, return old state
            current
        }
    }
}