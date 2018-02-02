package com.noveogroup.template.core.ext

import com.noveogroup.template.core.rx.RxHelper
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import org.reactivestreams.Publisher
import java.util.concurrent.TimeUnit

/**
 * Useful with [Observable] & [com.jakewharton.rxrelay2.Relay] to avoid [io.reactivex.exceptions.MissingBackpressureException].
 *
 * @param strategy [BackpressureStrategy] Flowable convertion strategy.
 * @param tag used to store Disposable in [RxHelper].
 * @param helper [RxHelper] to store subscription.
 * @param scheduler [Observable.observeOn] scheduler. **Skipped if null**.
 * @param transformer [Flowable.compose] transformation. **Skipped if null**.
 * @param consumer [Flowable.subscribe] onNext consumer. last lambda outside arguments.
 */
fun <T> Observable<T>.observeSafe(
        scheduler: Scheduler? = null,
        tag: String? = null,
        helper: RxHelper? = null,
        strategy: BackpressureStrategy = BackpressureStrategy.LATEST,
        transformer: ((Flowable<T>) -> Publisher<T>)? = null,
        consumer: (T) -> Unit = {}
): Disposable = toFlowable(strategy)
        .run { takeIf { transformer != null }?.compose(transformer) ?: this }
        .run { takeIf { scheduler != null }?.observeOn(scheduler) ?: this }
        .subscribe(consumer)
        .apply { helper?.let { trackBy(tag, it) } }

fun Disposable.trackBy(rxHelper: RxHelper) =
        rxHelper.add(this)

fun Disposable.trackBy(tag: String?, rxHelper: RxHelper) =
        tag?.let { rxHelper.add(it, this) } ?: trackBy(rxHelper)

fun <T> Flowable<T>.debounceWithBuffer(debounceTime: Long, timeUnit: TimeUnit): Flowable<MutableList<T>> =
        share().run { buffer(debounce(debounceTime, timeUnit)) }

fun <T> Observable<T>.debounceWithBuffer(debounceTime: Long, timeUnit: TimeUnit) =
        toFlowable(BackpressureStrategy.BUFFER).debounceWithBuffer(debounceTime, timeUnit)
