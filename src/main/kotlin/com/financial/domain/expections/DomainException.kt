package com.financial.domain.expections

open class DomainException(
    message: String,
    val errors: ArrayList<Error>
) : NoStacktraceException(message) {

    companion object {
        fun with(message: String): DomainException {
            return DomainException(message, arrayListOf(Error(message)))
        }
    }
}

