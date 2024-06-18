package com.example.happylauncher.domain.usecases

import com.example.happylauncher.common.utils.DateUtils


class GetDateUseCase {

    fun invoke() = DateUtils.getDate()

}