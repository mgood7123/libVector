package sample.matrix

open class MatrixCore: MatrixCoreInternal {
    // constructors
    constructor() : super()
    constructor(row: Int, column: Int) : super(row, column)
    override var AlgorithmHook: Any? = MatrixAlgorithms(this)
    val Algorithm = AlgorithmHook as MatrixAlgorithms
}