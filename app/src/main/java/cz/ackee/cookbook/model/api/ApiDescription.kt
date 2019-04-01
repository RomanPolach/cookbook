package cz.ackee.cookbook.model.api

import io.reactivex.Single
import retrofit2.http.GET

/**
 * Description of REST Api for Retrofit
 */
interface ApiDescription {

    @GET("recipes")
    fun getRecipes(): Single<List<Recipe>>
}