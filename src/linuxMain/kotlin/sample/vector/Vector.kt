package sample.vector

fun VectorCoreInternal<Number>.toVectorCore() =
    if (this is VectorCore)
        this
    else
        VectorCore().also {
            it.addAll(this)
        }

typealias Vector = VectorCore