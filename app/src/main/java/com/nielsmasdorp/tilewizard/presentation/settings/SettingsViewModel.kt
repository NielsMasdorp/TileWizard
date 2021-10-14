package com.nielsmasdorp.tilewizard.presentation.settings

import androidx.lifecycle.*
import com.nielsmasdorp.domain.GetSocketInfo
import com.nielsmasdorp.domain.SocketInfo
import com.nielsmasdorp.domain.StoreSocketInfo
import com.nielsmasdorp.domain.TileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getSocketInfo: GetSocketInfo,
    private val tileManager: TileManager,
    private val storeSocketInfo: StoreSocketInfo
) : ViewModel() {

    val socketInfo: LiveData<List<SocketViewData>> = liveData {
        val info = (0 until SOCKET_COUNT)
            .map { getSocketInfo(it) }
            .map { SocketViewData(name = it.name, ip = it.ip, enabled = it.enabled) }
        emit(info)
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun onSocketChanged(id: Int, name: String, ip: String, enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(Event.SocketUpdating)
            val old = getSocketInfo(id = id)
            storeSocketInfo(
                socketInfo = SocketInfo(
                    id = id,
                    name = name,
                    ip = ip,
                    enabled = enabled
                )
            )
            if (old.enabled != enabled) {
                tileManager.enableTile(id = id, enabled = enabled)
                if (!enabled) {
                    eventChannel.send(Event.SocketDisabled)
                }
            }
            delay(500L)
            eventChannel.send(Event.SocketUpdated)
        }
    }

    sealed class Event {
        object SocketUpdating : Event()
        object SocketDisabled : Event()
        object SocketUpdated : Event()
    }

    companion object {

        private const val SOCKET_COUNT = 5
    }
}

