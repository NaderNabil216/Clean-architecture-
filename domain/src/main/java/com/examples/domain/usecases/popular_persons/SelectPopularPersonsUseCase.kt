package com.examples.domain.usecases.popular_persons

import com.examples.data.repository.AppRepository
import com.examples.domain.base.LocalUseCase
import com.examples.entities.popular_person.local.PopularPersons
import javax.inject.Inject

open class SelectPopularPersonsUseCase @Inject constructor(
    private val appRepository: AppRepository
) : LocalUseCase<Unit, MutableList<PopularPersons>>() {

    override suspend fun executeOnBackground(parameters: Unit): MutableList<PopularPersons> {
        return appRepository.selectPopularPerson()
    }
}