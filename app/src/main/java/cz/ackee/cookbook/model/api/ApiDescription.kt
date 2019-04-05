package cz.ackee.cookbook.model.api

import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Description of REST Api for Retrofit
 */
interface ApiDescription {

    @GET("recipes")
    fun getRecipes(): Deferred<List<Recipe>>

    @GET("recipes/{recipeId}")
    fun getRecipeDetailById(@Path("recipeId") recipeId: String): Deferred<Recipe>

    @POST("recipes/{recipeId}/ratings")
    fun rateReceipeById(@Path("recipeId") recipeId: String, @Body body: RateReceipeRequest): Deferred<Recipe>

    @POST("recipes")
    fun sendRecipe(@Body body: NewRecipeRequest): Deferred<Recipe>
}