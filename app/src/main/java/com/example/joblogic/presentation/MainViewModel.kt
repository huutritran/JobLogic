package com.example.joblogic.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.joblogic.core.Failure
import com.example.joblogic.core.NoParams
import com.example.joblogic.core.SingleLiveEvent
import com.example.joblogic.domain.usecases.GetCallList
import com.example.joblogic.presentation.list.ItemData
import com.example.joblogic.presentation.list.toItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCallList: GetCallList
) : ViewModel() {
    private val _listType = SingleLiveEvent<ListType>()
    val listType: SingleLiveEvent<ListType> = _listType

    private val _error = SingleLiveEvent<Failure>()
    val error: SingleLiveEvent<Failure> = _error

    private val _listData = SingleLiveEvent<List<ItemData>>()
    val listData: SingleLiveEvent<List<ItemData>> = _listData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val typeChangeObserver = Observer<ListType> {
        when (_listType.value) {
            ListType.CALL -> getCallList()
            ListType.BUY -> getBuyList()
            ListType.SELL -> getSellList()
        }
    }

    init {
        _listType.observeForever(typeChangeObserver)
    }

    private fun getCallList() = viewModelScope.launch {
        _loading.value = true
        getCallList(NoParams).fold(
            { failure -> _error.value = failure },
            { data -> _listData.value = data.map { it.toItemData() } }
        ).also {
            _loading.value = false
        }
    }

    private fun getSellList() {}

    private fun getBuyList() {}

    fun showCallList() {
        _listType.value = ListType.CALL
    }

    fun showBuyList() {
        _listType.value = ListType.BUY
    }

    fun showSellList() {
        _listType.value = ListType.SELL
    }

    override fun onCleared() {
        super.onCleared()
        _listType.removeObserver(typeChangeObserver)
    }
}

enum class ListType {
    CALL, BUY, SELL
}

