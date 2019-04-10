package cz.ackee.cookbook.model.api.converters

import androidx.room.TypeConverter

/**
 * Converter class for Room
 */
class Converters {

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.split(";")?.map { it.trim() }
    }

    @TypeConverter
    fun fromStringList(list: List<String?>?): String? {
        return list?.joinToString(separator = ";")
    }
}