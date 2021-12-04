package com.example.joblogic.presentation

import androidx.lifecycle.ViewModel
import com.example.joblogic.core.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _listType = SingleLiveEvent<ListType>()
    val listType:SingleLiveEvent<ListType> = _listType

    fun showCallList() {
        _listType.value = ListType.CALL
    }

    fun showBuyList() {
        _listType.value = ListType.BUY
    }

    fun showSellList() {
        _listType.value = ListType.SELL
    }
}

enum class ListType {
   CALL, BUY, SELL
}

