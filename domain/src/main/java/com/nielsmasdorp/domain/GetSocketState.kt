package com.nielsmasdorp.domain

class GetSocketState(private val socketRepository: SocketRepository) {

    suspend operator fun invoke(ip: String): SocketState = socketRepository.getSocketState(ip = ip)
}