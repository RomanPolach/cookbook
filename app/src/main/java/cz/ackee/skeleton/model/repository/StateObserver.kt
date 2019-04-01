package cz.ackee.skeleton.model.repository

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

// Observers of [State]

/**
 * Class responsible for propagating [State] of some loading and exposing [Observable] that is
 * emitted every time state changes.
 */
open class StateObserver<T>(default: State<T> = State.Idle) {

    private val stateSubject: BehaviorSubject<State<T>> = BehaviorSubject.createDefault<State<T>>(default)

    fun loaded(data: T) {
        stateSubject.onNext(State.Loaded(data))
    }

    fun loading() {
        stateSubject.onNext(State.Loading)
    }

    fun error(err: Throwable) {
        stateSubject.onNext(State.Error(err))
    }

    fun observeState(): Observable<State<T>> = stateSubject
}

/**
 * [StateObserver] that has no value type and therefore is emulated by [Unit] type
 */
class NoValueStateObserver : StateObserver<Unit>() {

    fun loaded() {
        loaded(Unit)
    }
}