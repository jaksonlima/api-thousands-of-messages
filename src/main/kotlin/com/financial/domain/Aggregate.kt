package com.financial.domain

abstract class Aggregate<ID : Identifier<*>>(id: ID): Entity<ID>(id) {
}