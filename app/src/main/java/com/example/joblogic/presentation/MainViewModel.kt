package com.example.joblogic.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.joblogic.core.Failure
import com.example.joblogic.core.NoParams
import com.example.joblogic.core.SingleLiveEvent
import com.example.joblogic.domain.usecases.GetBuyList
import com.example.joblogic.domain.usecases.GetCallList
import com.example.joblogic.domain.usecases.GetSellList
import com.example.joblogic.presentation.list.ItemData
import com.example.joblogic.presentation.list.toItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCallList: GetCallList,
    private val getBuyList: GetBuyList,
    private val getSellList: GetSellList
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

    private fun launchLoading(call: suspend () -> Unit) = viewModelScope.launch {
        _loading.value = true
        call()
        _loading.value = false
    }

    private fun getCallList() = launchLoading {
        getCallList(NoParams).fold(
            { failure -> _error.value = failure },
            { data -> _listData.value = data.map { it.toItemData() } }
        )
    }

    private fun getSellList() = launchLoading {
        getSellList(NoParams).fold(
            { failure -> _error.value = failure },
            { data -> _listData.value = data.map { it.toItemData() } }
        )
    }

    private fun getBuyList() = launchLoading {
        getBuyList(NoParams).fold(
            { failure -> _error.value = failure },
            { data -> _listData.value = data.map { it.toItemData() } }
        )
    }

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

