package cz.ackee.cookbook.model.api.exception

import com.squareup.moshi.JsonDataException
import cz.ackee.cookbook.utils.isDebug
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.HttpException
import java.io.IOException

// Helpers for mapping Exceptions from api to our domain Exceptions
/**
 * Map exception from server exception to this app domain Exception. Optional parameter [httpExceptionMapper] can provide
 * mapper from [HttpException] to some other exception. If mapper return null, [GeneralServerException] will be returned
 */
fun <T> Single<T>.mapException(httpExceptionMapper: (HttpException) -> Exception? = { null }): Single<T> {
    return onErrorResumeNext { err: Throwable ->
        Single.error<T>(resolveException(err, httpExceptionMapper))
    }
}

/**
 * Map exception from server exception to this app domain Exception. Optional parameter [httpExceptionMapper] can provide
 * mapper from [HttpException] to some other exception. If mapper return null, [GeneralServerException] will be returned
 */
fun <T> Observable<T>.mapException(httpExceptionMapper: (HttpException) -> Exception? = { null }): Observable<T> {
    return onErrorResumeNext { err: Throwable ->
        Observable.error<T>(resolveException(err, httpExceptionMapper))
    }
}

/**
 * Map exception from server exception to this app domain Exception. Optional parameter [httpExceptionMapper] can provide
 * mapper from [HttpException] to some other exception. If mapper return null, [GeneralServerException] will be returned
 */
fun Completable.mapException(httpExceptionMapper: (HttpException) -> Exception? = { null }): Completable {
    return onErrorResumeNext { err: Throwable ->
        Completable.error(resolveException(err, httpExceptionMapper))
    }
}

/**
 * Actual method that resolves exception from input Throwable [err] with help of [httpExceptionMapper]
 */
fun resolveException(err: Throwable, httpExceptionMapper: (HttpException) -> Exception? = { null }): Throwable {
    if (isDebug) {
        err.printStackTrace()
    }
    return when (err) {
        is HttpException -> httpExceptionMapper(err) ?: GeneralServerException(err.code())
        is IOException -> NoInternetConnectionException()
        is JsonDataException -> err
        else -> UnexpectedError(err)
    }
}
