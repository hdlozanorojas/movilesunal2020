package com.hernanlozano.holamundo.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "¡Hola Mundo en Dashboard!"
    }
    val text: LiveData<String> = _text
}