package sample.vector

open class VectorCore: VectorCoreInternal<Number> {
    // constructors
    constructor() : super()
    /**
     * constructs an **N**-dimensional vector
     */
    constructor(N: Int) : super(N)
    override fun clone() = VectorCore().also { it.addAll(this) }
    override var AlgorithmHook: Any? = VectorAlgorithms(this)
    val Algorithm = AlgorithmHook as VectorAlgorithms
    operator fun plus(vectorCore: VectorCore) = Algorithm + vectorCore
}