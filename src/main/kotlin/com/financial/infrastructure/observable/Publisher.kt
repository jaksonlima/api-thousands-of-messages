package com.financial.infrastructure.observable

import org.slf4j.LoggerFactory

class Publisher<T>(
    private val subscribers: ArrayList<Subscriber<T>> = arrayListOf()
) {

    companion object {
        private val log = LoggerFactory.getLogger(Publisher::class.java)
    }

    fun publish(event: T): Boolean {
        var success = true
        for (subscriber in this.subscribers) {
            log.info("On processing [subscriber:${subscriber::class.simpleName}]")
            try {
                if (subscriber.test(event)) {
                    subscriber.onEvent(event)
                    log.info("Success processed [subscriber:${subscriber::class.simpleName}]")
                    return success
                }
            } catch (t: Throwable) {
                success = false;
                log.error("Error processed [event:${event.toString()}] [subscriber:${subscriber::class.simpleName}]", t)
            }
        }
        return success
    }

}