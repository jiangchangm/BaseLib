package com.jiangcm.base.lifecycle

import com.jiangcm.base.lifecycle.Functions.Companion.CANCEL_COMPLETABLE
import io.reactivex.*
import org.reactivestreams.Publisher
import javax.annotation.ParametersAreNonnullByDefault

@ParametersAreNonnullByDefault
class LifecycleTransformer<T> internal constructor(private val observable: Observable<*>) :
    ObservableTransformer<T, T>, FlowableTransformer<T, T>, SingleTransformer<T, T>,
    MaybeTransformer<T, T>, CompletableTransformer {
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.takeUntil(observable)
    }

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.takeUntil(observable.toFlowable(BackpressureStrategy.LATEST))
    }

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.takeUntil(observable.firstOrError())
    }

    override fun apply(upstream: Maybe<T>): MaybeSource<T> {
        return upstream.takeUntil(observable.firstElement())
    }

    override fun apply(upstream: Completable): CompletableSource {
        return Completable.ambArray(upstream, observable.flatMapCompletable(CANCEL_COMPLETABLE))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val that = other as LifecycleTransformer<*>
        return observable == that.observable
    }

    override fun hashCode(): Int {
        return observable.hashCode()
    }

    override fun toString(): String {
        return "LifecycleTransformer{" +
                "observable=" + observable +
                '}'
    }
}