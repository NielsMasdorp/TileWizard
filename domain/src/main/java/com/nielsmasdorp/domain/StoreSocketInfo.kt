package com.nielsmasdorp.domain

class StoreSocketInfo(private val socketInfoRepository: SocketInfoRepository) {

    suspend operator fun invoke(socketInfo: SocketInfo) {
        socketInfoRepository.storeSocketInfo(socketInfo)
    }
}