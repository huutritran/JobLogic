package com.example.joblogic.domain.usecases

data class JobLogicUseCase(
    val getCallList: GetCallList,
    val getBuyList: GetBuyList,
    val getSellList: GetSellList,
)