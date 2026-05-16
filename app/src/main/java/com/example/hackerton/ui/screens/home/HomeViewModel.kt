package com.example.hackerton.ui.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _selectedSong = MutableStateFlow<String?>(null)
    val selectedSong: StateFlow<String?> = _selectedSong.asStateFlow()

    fun selectSong(name: String) {
        _selectedSong.value = name
    }

    fun clearSelection() {
        _selectedSong.value = null
    }
}
