package com.joonhee.spring_batch

import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor


class PersonItemProcessor : ItemProcessor<Person, Person> {
    override fun process(person: Person): Person {
        val firstName: String = person.firstName.uppercase()
        val lastName: String = person.lastName.uppercase()
        val transformedPerson = Person(firstName, lastName)
        log.info("Converting ($person) into ($transformedPerson)")
        return transformedPerson
    }

    companion object {
        private val log = LoggerFactory.getLogger(PersonItemProcessor::class.java)
    }
}