package com.example.radio_aplikacija.ui.radio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RadioViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is radio Fragment"
    }
    val text: LiveData<String> = _text
}