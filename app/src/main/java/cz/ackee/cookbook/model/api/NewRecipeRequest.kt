package cz.ackee.cookbook.model.api

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class NewRecipeRequest(
    val ingredients: List<String>? = null,
    val description: String? = null,
    val name: String? = null,
    val duration: Int? = null,
    val info: String? = null
) : Parcelable