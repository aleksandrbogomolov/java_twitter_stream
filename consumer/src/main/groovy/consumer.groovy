def eb = vertx.eventBus()

eb.consumer("stream", { msg ->
    println("--- --- --- --- --- --- --- ---")
    println(msg.body())
    println("--- --- --- --- --- --- --- ---")
})