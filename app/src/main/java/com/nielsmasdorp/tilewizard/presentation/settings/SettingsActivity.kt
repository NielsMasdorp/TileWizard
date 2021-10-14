package com.nielsmasdorp.tilewizard.presentation.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.nielsmasdorp.tilewizard.R
import com.nielsmasdorp.tilewizard.databinding.ActivitySettingsBinding
import com.nielsmasdorp.tilewizard.util.observeInLifecycle
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {

    private val settingsViewModel: SettingsViewModel by viewModel()

    private lateinit var binding: ActivitySettingsBinding

    private lateinit var adapter: SettingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        adapter = SettingsAdapter(this).also {
            it.onSocketChanged = settingsViewModel::onSocketChanged
            binding.recycler.adapter = it
        }
        settingsViewModel.socketInfo.observe(this, { initList(it) })
        settingsViewModel.eventsFlow
            .onEach {
                when (it) {
                    SettingsViewModel.Event.SocketDisabled -> {
                        Toast.makeText(this, getString(R.string.reboot_message), Toast.LENGTH_LONG)
                            .show()
                    }
                    SettingsViewModel.Event.SocketUpdating -> showLoading(loading = true)
                    SettingsViewModel.Event.SocketUpdated -> showLoading(loading = false)
                }
            }.observeInLifecycle(this)
    }

    private fun showLoading(loading: Boolean) {
        binding.toolbar.subtitle = if (loading) {
            getString(R.string.not_saved)
        } else {
            getString(R.string.saved)
        }
    }

    private fun initList(info: List<SocketViewData>) {
        adapter.submitList(info)
    }
}