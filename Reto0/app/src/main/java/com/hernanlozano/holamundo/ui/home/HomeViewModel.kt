package com.hernanlozano.holamundo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "¡Hola Mundo en Inicio!"
    }
    val text: LiveData<String> = _text
}