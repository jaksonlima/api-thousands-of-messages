package com.financial.infrastructure.observable

interface Subscriber<T> {
    fun test(ev: T): Boolean
    fun onEvent(ev: T)
}