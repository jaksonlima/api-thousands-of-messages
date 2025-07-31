package com.financial.domain

abstract class Identifier<T> : ValueObject {

    abstract val id: T

    fun value(): T = this.id

}