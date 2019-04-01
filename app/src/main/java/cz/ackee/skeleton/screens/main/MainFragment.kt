package cz.ackee.skeleton.screens.main

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import cz.ackee.extensions.rx.observeOnMainThread
import cz.ackee.skeleton.model.api.Recipe
import cz.ackee.skeleton.model.repository.State
import cz.ackee.skeleton.screens.base.fragment.BaseFragment
import cz.ackee.skeleton.screens.layout.ListLayout
import cz.ackee.skeleton.screens.main.epoxy.recipe
import io.reactivex.rxkotlin.plusAssign
import org.jetbrains.anko.design.longSnackbar
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Main app fragment
 */
class MainFragment : BaseFragment<ListLayout>() {

    private val viewModel: MainViewModel by viewModel()

    override fun createLayout(parent: Context) = ListLayout(parent)

    override fun ListLayout.viewCreated(savedState: Bundle?) {
        disposables += viewModel.observeState()
            .observeOnMainThread()
            .subscribe { state ->
                when (state) {
                    is State.Empty -> {
                        showProgress(false)
                        showEmpty(true)
                        showError(false)
                    }
                    is State.Loading -> {
                        showProgress(true)
                        showEmpty(false)
                        showError(false)
                    }
                    is State.Reloading -> {
                        showProgress(true)
                        showEmpty(false)
                        showError(false)
                        addRecipes(state.previousData)
                    }
                    is State.Loaded -> {
                        showProgress(false)
                        showEmpty(false)
                        showError(false)
                        addRecipes(state.data)
                    }
                    is State.Error -> {
                        showProgress(false)
                        showEmpty(false)
                        showError(true)
                        view.longSnackbar(state.error.localizedMessage)
                    }
                }
            }
    }

    private fun addRecipes(recipes: List<Recipe>?) {
        layout.epoxyRecyclerView.buildModelsWith { controller ->
            with(controller) {
                recipes?.forEach {
                    recipe {
                        id(it.id)
                        title(it.name)
                        subtitle("${it.score} *")
                    }
                }
            }
        }
    }

    override fun getTitle() = "Ackee Skeleton"
}