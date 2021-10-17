package com.nielsmasdorp.domain

class UpdateSocketState(private val socketRepository: SocketRepository) {

    suspend operator fun invoke(ip: String, current: SocketState, new: SocketState): SocketState {
        return socketRepository.updateSocketState(ip = ip, current = current, new = new)
    }
}