package com.nielsmasdorp.domain

class GetSocketState(private val socketRepository: SocketRepository) {

    suspend operator fun invoke(ip: String, current: SocketState): SocketState {
        return socketRepository.getSocketState(ip = ip, current = current)
    }
}