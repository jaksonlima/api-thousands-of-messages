package com.financial.domain

interface Identifier<T> : ValueObject {
    fun value(): T
}