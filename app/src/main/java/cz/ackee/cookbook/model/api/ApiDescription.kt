package cz.ackee.cookbook.model.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

/**
 * Description of REST Api for Retrofit
 */
interface ApiDescription {

    @GET("recipes")
    fun getRecipes(): Deferred<List<Recipe>>
}