package com.nielsmasdorp.domain

class GetSocketInfo(private val socketInfoRepository: SocketInfoRepository) {

    suspend operator fun invoke(id: Int): SocketInfo = socketInfoRepository.getSocketInfo(id = id)
}