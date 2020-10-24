package com.examples.data.source.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.examples.data.util.TestUtil
import com.examples.entities.popular_person.local.PopularPersons
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

private const val NAME = "Shehab Elsarky"
private const val OVERVIEW = "Nice person"
private const val POPULARITY = 10f
private const val TILE = "Awesome title"

@ExperimentalCoroutinesApi
class PopularPersonsDaoTest : AppDatabaseTest() {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun testEmptyDatabase() = runBlocking {
        val selectedPopularPersonsList: MutableList<PopularPersons> =
            getPopularPersonsDao().selectAllPopularPersons()

        assertNotNull(selectedPopularPersonsList)

        if (selectedPopularPersonsList.isNotEmpty()) {
            for (i in 0 until selectedPopularPersonsList.size)
                getPopularPersonsDao().deletePopularPerson(selectedPopularPersonsList[i])
        }

        assertEquals(0, selectedPopularPersonsList.size)
    }


    @Test
    fun insertRead() = runBlocking {
        val popularPersons = PopularPersons(TestUtil.TEST_POPULAR_PERSONS_1)

        // insert
        getPopularPersonsDao().insertPopularPerson(popularPersons)

        // read
        val insertedPopularPersonsList: MutableList<PopularPersons> =
            getPopularPersonsDao().selectAllPopularPersons()

        assertNotNull(insertedPopularPersonsList)
        assertFalse(insertedPopularPersonsList.isEmpty())
    }


    @Test
    fun insertReadDelete() = runBlocking {
        val popularPersons = PopularPersons(TestUtil.TEST_POPULAR_PERSONS_1)

        // insert
        getPopularPersonsDao().insertPopularPerson(popularPersons)

        // read
        var insertedPopularPersonsList: MutableList<PopularPersons> =
            getPopularPersonsDao().selectAllPopularPersons()

        assertNotNull(insertedPopularPersonsList)
        assertFalse(insertedPopularPersonsList.isEmpty())

        assertEquals(popularPersons.id, insertedPopularPersonsList[0].id)
        assertEquals(popularPersons.name, insertedPopularPersonsList[0].name)
        assertEquals(popularPersons.overview, insertedPopularPersonsList[0].overview)
        assertEquals(popularPersons.tile, insertedPopularPersonsList[0].tile)
        assertEquals(popularPersons.popularity, insertedPopularPersonsList[0].popularity)
        assertEquals(popularPersons.image, insertedPopularPersonsList[0].image)

        popularPersons.entityId = insertedPopularPersonsList[0].entityId
        assertEquals(popularPersons, insertedPopularPersonsList[0])

        // delete
        getPopularPersonsDao().deletePopularPerson(popularPersons)

        // confirm the database is empty
        insertedPopularPersonsList = getPopularPersonsDao().selectAllPopularPersons()
        assertEquals(0, insertedPopularPersonsList.size)
    }


    @Test
    fun insertReadUpdateReadDelete() = runBlocking {
        val popularPersons = PopularPersons(TestUtil.TEST_POPULAR_PERSONS_1)

        // insert
        getPopularPersonsDao().insertPopularPerson(popularPersons)

        // read
        var insertedPopularPersonsList: MutableList<PopularPersons> =
            getPopularPersonsDao().selectAllPopularPersons()

        assertNotNull(insertedPopularPersonsList)
        assertFalse(insertedPopularPersonsList.isEmpty())

        assertEquals(popularPersons.id, insertedPopularPersonsList[0].id)
        assertEquals(popularPersons.name, insertedPopularPersonsList[0].name)
        assertEquals(popularPersons.overview, insertedPopularPersonsList[0].overview)
        assertEquals(popularPersons.tile, insertedPopularPersonsList[0].tile)
        assertEquals(popularPersons.popularity, insertedPopularPersonsList[0].popularity)
        assertEquals(popularPersons.image, insertedPopularPersonsList[0].image)

        popularPersons.entityId = insertedPopularPersonsList[0].entityId
        assertEquals(popularPersons, insertedPopularPersonsList[0])

        //update
        popularPersons.apply {
            name = NAME
            overview = OVERVIEW
            tile = TILE
            popularity = POPULARITY
        }
        getPopularPersonsDao().updatePopularPerson(popularPersons)

        //read
        insertedPopularPersonsList = getPopularPersonsDao().selectAllPopularPersons()
        assertEquals(NAME,insertedPopularPersonsList[0].name)
        assertEquals(POPULARITY,insertedPopularPersonsList[0].popularity)
        assertEquals(OVERVIEW,insertedPopularPersonsList[0].overview)
        assertEquals(TILE,insertedPopularPersonsList[0].tile)
        popularPersons.id = insertedPopularPersonsList[0].id

        assertEquals(popularPersons,insertedPopularPersonsList[0])

        // delete
        getPopularPersonsDao().deletePopularPerson(popularPersons)

        // confirm the database is empty
        insertedPopularPersonsList = getPopularPersonsDao().selectAllPopularPersons()
        assertEquals(0, insertedPopularPersonsList.size)
    }
}