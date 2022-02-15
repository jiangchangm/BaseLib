package androidx.lifecycle

open class ExternalLiveData<T>() : MutableLiveData<T>() {
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            // ignore
            return
        }
        try {
            //use ExternalLifecycleBoundObserver instead of LifecycleBoundObserver
            val wrapper: LifecycleBoundObserver = ExternalLifecycleBoundObserver(owner, observer)
            val existing: LifecycleBoundObserver? =
                callMethodPutIfAbsent(observer, wrapper) as? LiveData<T>.LifecycleBoundObserver
            if (existing != null && !existing.isAttachedTo(owner)) {
                throw IllegalArgumentException(
                    "Cannot add the same observer"
                            + " with different lifecycles"
                )
            }
            if (existing != null) {
                return
            }
            owner.lifecycle.addObserver(wrapper)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    public override fun getVersion(): Int {
        return super.getVersion()
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
    protected open fun observerActiveLevel(): Lifecycle.State {
        return Lifecycle.State.CREATED
    }

    internal inner class ExternalLifecycleBoundObserver(
        owner: LifecycleOwner,
        observer: Observer<in T>?
    ) :
        LifecycleBoundObserver(owner, observer) {
        override fun shouldBeActive(): Boolean {
            return mOwner.lifecycle.currentState.isAtLeast(observerActiveLevel())
        }
    }

    @get:Throws(Exception::class)
    private val fieldObservers: Any
        get() {
            val fieldObservers = LiveData::class.java.getDeclaredField("mObservers")
            fieldObservers.isAccessible = true
            return fieldObservers[this]
        }

    @Throws(Exception::class)
    private fun callMethodPutIfAbsent(observer: Any, wrapper: Any): Any? {
        val mObservers = fieldObservers
        val classOfSafeIterableMap: Class<*> = mObservers.javaClass
        val putIfAbsent = classOfSafeIterableMap.getDeclaredMethod(
            "putIfAbsent",
            Any::class.java, Any::class.java
        )
        putIfAbsent.isAccessible = true
        return putIfAbsent.invoke(mObservers, observer, wrapper)
    }

    companion object {
        val START_VERSION = LiveData.START_VERSION
    }
}