package com.hernanlozano.holamundo.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "¡Hola Mundo en Notificaciones!"
    }
    val text: LiveData<String> = _text
}