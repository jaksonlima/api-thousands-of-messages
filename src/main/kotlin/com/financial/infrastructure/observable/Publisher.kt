package com.financial.infrastructure.observable

import org.slf4j.LoggerFactory

class Publisher<T>(
    private val subscribers: ArrayList<Subscriber<T>> = arrayListOf()
) {

    companion object {
        private val log = LoggerFactory.getLogger(Publisher::class.java)
    }

    fun register(subscriber: Subscriber<T>) {
        subscribers.add(subscriber);
    }

    fun publish(event: T): Boolean {
        var success = true
        for (subscriber in this.subscribers) {
            log.error("On process [event:${event.toString()}] [subscriber:${subscriber::class.simpleName}]")
            try {
                if (subscriber.test(event)) {
                    subscriber.onEvent(event)
                    return success
                }
            } catch (t: Throwable) {
                success = false;
                log.error("Error process [event:${event.toString()}] [subscriber:${subscriber::class.simpleName}]")
            }
        }
        return success
    }

}