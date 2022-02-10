package androidx.lifecycle

import java.lang.Exception
import java.lang.IllegalArgumentException


open class ExternalLiveData<T>() : MutableLiveData<T>() {

    public override fun getVersion(): Int {
        return super.getVersion()
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            return
        }
        try {
            //use ExternalLifecycleBoundObserver instead of LifecycleBoundObserver
            val wrapper: LiveData<*>.LifecycleBoundObserver = ExternalLifecycleBoundObserver(owner, observer)
            val existing:LiveData<*>.LifecycleBoundObserver =
                callMethodPutIfAbsent(observer, wrapper) as LiveData<*>.LifecycleBoundObserver
            if (!existing.isAttachedTo(owner)) {
                throw IllegalArgumentException(
                    "Cannot add the same observer"
                            + " with different lifecycles"
                )
            }
            owner.lifecycle.addObserver(wrapper)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * determine when the observer is active, means the observer can receive message
     * the default value is CREATED, means if the observer's state is above create,
     * for example, the onCreate() of activity is called
     * you can change this value to CREATED/STARTED/RESUMED
     * determine on witch state, you can receive message
     *
     * @return Lifecycle.State
     */
    protected open fun observerActiveLevel(): Lifecycle.State? {
        return Lifecycle.State.CREATED
    }

    internal inner class ExternalLifecycleBoundObserver(
        owner: LifecycleOwner,
        observer: Observer<in T>?
    ) :
        LifecycleBoundObserver(owner, observer) {
        override fun shouldBeActive(): Boolean {
            return observerActiveLevel()?.let { mOwner.lifecycle.currentState.isAtLeast(it) } == true
        }
    }

    @get:Throws(Exception::class)
    private val fieldObservers: Any?
         get() {
            val fieldObservers = LiveData::class.java.getDeclaredField("mObservers")
            fieldObservers.isAccessible = true
            return fieldObservers[this]
        }

    @Throws(Exception::class)
    private fun callMethodPutIfAbsent(observer: Any, wrapper: Any): Any? {
        val mObservers = fieldObservers
        val classOfSafeIterableMap: Class<*> = mObservers!!.javaClass
        val putIfAbsent = classOfSafeIterableMap.getDeclaredMethod(
            "putIfAbsent",
            Any::class.java, Any::class.java
        )
        putIfAbsent.isAccessible = true
        return putIfAbsent.invoke(mObservers, observer, wrapper)
    }

    companion object {
        const val START_VERSION = LiveData.START_VERSION
    }
}