package com.financial.domain.expections

import com.financial.domain.Aggregate
import com.financial.domain.Identifier
import kotlin.reflect.KClass

class NotFoundException(
    message: String,
    errors: ArrayList<Error>
) : DomainException(message, errors) {

    companion object {
        fun with(
            aClass: KClass<out Aggregate<*>>,
            aId: Identifier<*>
        ): NotFoundException {
            val message = "${aClass.simpleName} with ID ${aId.value().toString()} was not found"

            return NotFoundException(message, arrayListOf())
        }
    }
}

