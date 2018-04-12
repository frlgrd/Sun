package com.frlgrd.sun.common

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import com.uber.autodispose.LifecycleEndedException
import com.uber.autodispose.LifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import toothpick.Toothpick

abstract class BaseViewModel(app: Application) : ViewModel(), LifecycleScopeProvider<BaseViewModel.ViewModelLifeCycleEvent> {

    private val scopeProvider by lazy { BehaviorSubject.create<ViewModelLifeCycleEvent>() }

    init {
        Timber.i("${this.javaClass.simpleName} init")
        val viewModelScope = Toothpick.openScopes(app)
        Toothpick.inject(toBeInjected(), viewModelScope)
        scopeProvider.onNext(ViewModelLifeCycleEvent.ON_CREATED)
    }

    private /*final*/ fun toBeInjected(): Any {
        return this
    }

    @CallSuper
    override fun onCleared() {
        Timber.i("${this.javaClass.simpleName} onCleared")
        scopeProvider.onNext(ViewModelLifeCycleEvent.ON_CLEARED)
        super.onCleared()
    }

    enum class ViewModelLifeCycleEvent {
        ON_CREATED, ON_CLEARED
    }

    override fun lifecycle(): Observable<ViewModelLifeCycleEvent> = scopeProvider.hide()

    override fun peekLifecycle(): ViewModelLifeCycleEvent = scopeProvider.value

    override fun correspondingEvents(): Function<ViewModelLifeCycleEvent, ViewModelLifeCycleEvent> =
            Function { viewModelEvent ->
                when (viewModelEvent) {
                    ViewModelLifeCycleEvent.ON_CREATED ->  ViewModelLifeCycleEvent.ON_CLEARED
                    ViewModelLifeCycleEvent.ON_CLEARED ->  throw LifecycleEndedException()
                }
            }
}