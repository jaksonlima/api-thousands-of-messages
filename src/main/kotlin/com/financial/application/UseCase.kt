package com.financial.application

interface UseCase<I, O> {
    fun execute(input: I): O
}