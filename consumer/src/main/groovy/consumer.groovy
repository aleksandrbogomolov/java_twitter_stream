def eb = vertx.eventBus()

eb.consumer("stream", { msg ->
    println(msg.body())
    println("--- --- --- --- --- --- --- ---")
})