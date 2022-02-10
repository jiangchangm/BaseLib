package com.jiangcm.common.lifecycle

import com.jiangcm.common.lifecycle.Functions.Companion.RESUME_FUNCTION
import com.jiangcm.common.lifecycle.Functions.Companion.SHOULD_COMPLETE
import io.reactivex.Observable
import java.lang.AssertionError
import io.reactivex.functions.Function
import javax.annotation.CheckReturnValue
import javax.annotation.Nonnull


class RxLifecycle private constructor() {
    companion object {
        /**
         * @param lifecycle 当前的生命周期换
         * @param event     当前的默认值只有[, FragmentEvent , PresenterEvent][ActivityEvent]
         */
        @Nonnull
        @CheckReturnValue
        fun <T, R> bindUntilEvent(
            @Nonnull lifecycle: Observable<R>,
            @Nonnull event: R
        ): LifecycleTransformer<T> {
            return bind(takeUntilEvent(lifecycle, event))
        }

        private fun <R> takeUntilEvent(lifecycle: Observable<R>, event: R): Observable<R> {
            return lifecycle.filter { lifecycleEvent -> lifecycleEvent == event }
        }

        @Nonnull
        @CheckReturnValue
        fun <T, R> bind(@Nonnull lifecycle: Observable<R>?): LifecycleTransformer<T> {
            return LifecycleTransformer(lifecycle!!)
        }

        @Nonnull
        @CheckReturnValue
        fun <T, R> bind(
            @Nonnull lifecycle: Observable<R>,
            @Nonnull correspondingEvents: Function<R, R>
        ): LifecycleTransformer<T> {
            return bind(takeUntilCorrespondingEvent(lifecycle.share(), correspondingEvents))
        }

        private fun <R> takeUntilCorrespondingEvent(
            lifecycle: Observable<R>,
            correspondingEvents: Function<R, R>
        ): Observable<Boolean> {
            return Observable.combineLatest(
                lifecycle.take(1).map(correspondingEvents),
                lifecycle.skip(1),
                { bindUntilEvent, lifecycleEvent -> lifecycleEvent == bindUntilEvent })
                .onErrorReturn(RESUME_FUNCTION)
                .filter(SHOULD_COMPLETE)
        }
    }

    init {
        throw AssertionError("No instances")
    }
}