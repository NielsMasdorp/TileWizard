package com.nielsmasdorp.tilewizard.presentation.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nielsmasdorp.tilewizard.R
import com.nielsmasdorp.tilewizard.databinding.ListItemSocketBinding
import com.nielsmasdorp.tilewizard.util.textChanges
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SettingsAdapter(private val lifecycleOwner: LifecycleOwner) :
    ListAdapter<SocketViewData, SettingsAdapter.SocketViewHolder>(DIFF_CALLBACK) {

    lateinit var onSocketChanged: ((Int, String, String, Boolean) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocketViewHolder {
        val binding =
            ListItemSocketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SocketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SocketViewHolder, position: Int) =
        holder.bindSocket(getItem(position))

    inner class SocketViewHolder(private val binding: ListItemSocketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindSocket(socketData: SocketViewData) {
            with(binding) {
                title.text = String.format(
                    binding.root.context.getString(
                        R.string.socket_title
                    ), adapterPosition + 1
                )
                name.setText(socketData.name)
                ip.setText(socketData.ip)
                enabled.isChecked = socketData.enabled
                initListener(name)
                initListener(ip)
                enabled.setOnCheckedChangeListener { _, _ ->
                    onSocketChanged()
                }
            }
        }

        private fun initListener(editText: EditText) {
            editText.textChanges().debounce(300L)
                .onEach { onSocketChanged() }
                .launchIn(lifecycleOwner.lifecycleScope)
        }

        private fun onSocketChanged() {
            onSocketChanged(
                adapterPosition,
                binding.name.text.toString(),
                binding.ip.text.toString(),
                binding.enabled.isChecked
            )
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SocketViewData>() {

            override fun areItemsTheSame(
                oldItem: SocketViewData,
                newItem: SocketViewData
            ): Boolean {
                return oldItem.ip == newItem.ip
            }

            override fun areContentsTheSame(
                oldItem: SocketViewData,
                newItem: SocketViewData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}