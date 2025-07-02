package com.financial.infrastructure.configuration.annotations

import org.springframework.beans.factory.annotation.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*

@Target(FIELD, FUNCTION, VALUE_PARAMETER, TYPE, ANNOTATION_CLASS)
@Retention(RUNTIME)
@Qualifier("AccountTopic")
annotation class AccountTopic()
