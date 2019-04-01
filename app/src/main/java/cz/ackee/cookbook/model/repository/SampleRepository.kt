package cz.ackee.cookbook.model.repository

import cz.ackee.extensions.rx.subscribeOnNewThread
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.api.exception.ExceptionMapperHelper
import cz.ackee.cookbook.model.interactor.ApiInteractor
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 * Sample repository
 */
interface SampleRepository {

    /**
     * Fetch fresh sample data from API
     */
    fun fetchSampleData()

    /**
     * Observe the sample data. Calls [fetchSampleData] when no data has been fetched before.
     */
    fun observeSampleData(): Observable<State<List<Recipe>>>
}

class SampleRepositoryImpl(val apiInteractor: ApiInteractor) : SampleRepository, ExceptionMapperHelper {

    private val stateObserver = StateObserver<List<Recipe>>()
    private var fetchDisposable: Disposable? = null

    override fun fetchSampleData() {
        stateObserver.loading()
        fetchDisposable = apiInteractor.getSampleData()
            .subscribeOnNewThread()
            .mapException()
            .subscribe({ sampleData ->
                stateObserver.loaded(sampleData)
            }, { e ->
                stateObserver.error(e)
                Timber.e(e)
            })
    }

    override fun observeSampleData(): Observable<State<List<Recipe>>> {
        if (fetchDisposable == null) { // load data on first observe
            fetchSampleData()
        }
        return stateObserver.observeState()
    }
}