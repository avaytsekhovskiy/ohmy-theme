@file:Suppress("unused")

package com.noveogroup.template.core.ext


import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins

fun <T> Observable<T>.toInfinite(): Observable<Infinite<T>> =
        map({ Infinite(value = it) })
                .onErrorReturn({ Infinite(error = it) })

fun <T> Flowable<T>.toInfinite(): Flowable<Infinite<T>> =
        map({ Infinite(value = it) })
                .onErrorReturn({ Infinite(error = it) })

fun <T> Single<T>.toInfinite(): Flowable<Infinite<T>> =
        toFlowable().toInfinite()

fun <T> Observable<T>.bindInfinite(target: (Infinite<T>) -> Unit): Disposable =
        toInfinite().subscribe(target)

fun <T> Flowable<T>.bindInfinite(target: (Infinite<T>) -> Unit): Disposable =
        toInfinite().subscribe(target)

fun <T> Single<T>.bindInfinite(target: Consumer<Infinite<T>>): Disposable =
        toInfinite().subscribe(target)

fun <T> Observable<Infinite<T>>.subscribeInfinite(
        next: (T) -> Unit = {},
        error: (Throwable) -> Unit = rxErrorConsumer
): Disposable = subscribe { it.unwrap(next, error) }

fun <T> Flowable<Infinite<T>>.subscribeInfinite(
        next: (T) -> Unit = {},
        error: (Throwable) -> Unit = rxErrorConsumer
): Disposable = subscribe { it.unwrap(next, error) }

private val rxErrorConsumer: (Throwable) -> Unit = { RxJavaPlugins.getErrorHandler()?.accept(it) }

data class Infinite<out T>(
        val value: T? = null,
        val error: Throwable? = null
) {

    val isData get() = value != null
    val isError get() = error != null

    fun unwrap(
            onNext: (T) -> Unit,
            onError: (Throwable) -> Unit
    ) = when {
        value != null -> onNext(value)
        error != null -> onError(error)
        else -> error("pair with nulls")
    }
}
