package com.nielsmasdorp.tilewizard.data

import com.nielsmasdorp.domain.SocketInfo
import com.nielsmasdorp.domain.SocketInfoRepository
import io.paperdb.Paper

class PaperSocketInfoRepository : SocketInfoRepository {

    init {
        if (!Paper.book().contains(SOCKET_BOOK_NAME)) {
            Paper.book().write(
                SOCKET_BOOK_NAME, listOf(
                    SocketInfo(0, "", "", false),
                    SocketInfo(1, "", "", false),
                    SocketInfo(2, "", "", false),
                    SocketInfo(3, "", "", false),
                    SocketInfo(4, "", "", false)
                )
            )
        }
    }

    override suspend fun getSocketInfo(id: Int): SocketInfo {
        val all: List<SocketInfo> = Paper.book().read(SOCKET_BOOK_NAME)
        return all.find { it.id == id }!!
    }

    override suspend fun storeSocketInfo(info: SocketInfo) {
        val current: MutableList<SocketInfo> = Paper.book().read(SOCKET_BOOK_NAME)
        val new = current.map { if (it.id == info.id) info else it }
        Paper.book().write(SOCKET_BOOK_NAME, new)
    }

    companion object {

        private const val SOCKET_BOOK_NAME = "sockets"
    }
}