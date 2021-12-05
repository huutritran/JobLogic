package com.example.joblogic.data.util

import arrow.core.Either
import com.example.joblogic.core.Failure
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

suspend fun <T> withHandlingException(
    dispatcher: CoroutineContext,
    block: suspend () -> T,
): Either<Failure, T> = withContext(dispatcher) {
    return@withContext try {
        val result = block()
        Either.Right(result)
    } catch (e: HttpException) {
        Either.Left(Failure.ApiFailure(e.code()))
    } catch (e: IOException) {
        Either.Left(Failure.NetworkFailure)
    }
}