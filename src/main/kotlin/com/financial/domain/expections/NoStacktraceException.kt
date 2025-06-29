package com.financial.domain.expections

open class NoStacktraceException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause, true, false)