package cz.ackee.cookbook.screens.base.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * ViewModel that automatically dispose its [disposables]
 */
open class RxAwareViewModel : ViewModel() {

    protected var disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}