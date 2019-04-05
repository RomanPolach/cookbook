package cz.ackee.cookbook.model.api

import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Description of REST Api for Retrofit
 */
interface ApiDescription {

    @GET("recipes")
    fun getRecipes(): Deferred<List<Recipe>>

    @POST("recipes")
    fun sendRecipe(@Body body: NewRecipeRequest): Deferred<Recipe>
}