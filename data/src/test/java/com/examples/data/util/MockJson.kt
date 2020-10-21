package com.examples.data.util

import com.examples.entities.popular_person.remote.PopularPersonsResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

const val POPULAR_ACTORS_FILENAME = "popular_actors.json"

open class MockJson
@Inject
constructor() {
    /**
     * Reads input file and converts to json
     */
    fun getJson(fileName: String): String {
        val bytes = javaClass.classLoader!!.getResourceAsStream(fileName).readBytes()
        return String(bytes)
    }


    fun getPopularPersons(): PopularPersonsResponse {
        val rawJson = getJson(POPULAR_ACTORS_FILENAME)
        return Gson().fromJson(
            rawJson,
            object : TypeToken<PopularPersonsResponse>() {}.type
        )
    }
}