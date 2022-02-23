
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

/**
 * 将执行调度到Android主UI线程上，并提供本机[延迟] [Delay.delay]支持。
 */
val UI = HandlerContext(Handler(Looper.getMainLooper()), "UI")

/**
 * 代表任意的[Handler]作为[CoroutineDispatcher]的一个实现。
 */
fun Handler.asCoroutineDispatcher() = HandlerContext(this)

/**
 * 在任意Android [Handler]之上实现[CoroutineDispatcher]。
 *
 * @param处理程序处理程序。
 * @param名称作为调试的可选名称。
 */
@OptIn(InternalCoroutinesApi::class)
public class HandlerContext(
        private val handler: Handler,
        private val name: String? = null
) : CoroutineDispatcher(), Delay {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        handler.post(block)
    }

    override fun scheduleResumeAfterDelay(
        timeMillis: Long,
        continuation: CancellableContinuation<Unit>
    ) {
        handler.postDelayed({
            with(continuation) { resumeUndispatched(Unit) }
        }, timeMillis)
    }

    override fun invokeOnTimeout(
        timeMillis: Long,
        block: Runnable,
        context: CoroutineContext
    ): DisposableHandle {
        handler.postDelayed(block, timeMillis)
        return DisposableHandle { handler.removeCallbacks(block) }
    }

    override fun toString() = name ?: handler.toString()
    override fun equals(other: Any?): Boolean = other is HandlerContext && other.handler === handler
    override fun hashCode(): Int = System.identityHashCode(handler)
}
