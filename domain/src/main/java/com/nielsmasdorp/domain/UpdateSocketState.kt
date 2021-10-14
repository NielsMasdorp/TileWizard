package com.nielsmasdorp.domain

class UpdateSocketState(private val socketRepository: SocketRepository) {

    suspend operator fun invoke(ip: String, state: SocketState): SocketState {
        return socketRepository.updateSocketState(ip = ip, state = state)
    }
}