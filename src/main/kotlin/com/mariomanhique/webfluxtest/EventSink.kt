package com.mariomanhique.webfluxtest

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks

@Component
class EventSink {

    private val sink: Sinks.Many<Note> = Sinks.many().multicast().onBackpressureBuffer()
    val flux: Flux<Note> = sink.asFlux()

    fun emit(note: Note) {
        sink.tryEmitNext(note)
    }
}
