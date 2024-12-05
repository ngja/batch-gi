package com.hansol.batchgi.tutorial

import com.hansol.batchgi.logger
import org.springframework.batch.item.ItemProcessor
import java.util.*

class PersonItemProcessor : ItemProcessor<Person, Person> {

    override fun process(person: Person): Person {
        val firstName = person.firstName.uppercase(Locale.getDefault())
        val lastName = person.lastName.uppercase(Locale.getDefault())

        val transformedPerson = Person(firstName, lastName)

        logger.info { "Converting ($person) into ($transformedPerson)" }

        return transformedPerson
    }
}