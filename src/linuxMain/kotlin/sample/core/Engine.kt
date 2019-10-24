package sample.core

open class Engine {
    val operator = Operator()
    fun addition(v1: Number, v2: Number) = operator.addition(v1, v2)
    fun subtraction(v1: Number, v2: Number) = operator.subtraction(v1, v2)
    fun multiplication(v1: Number, v2: Number) = operator.multiplication(v1, v2)
    fun division(v1: Number, v2: Number) = operator.division(v1, v2)
    open var isFixedSize = true
    open var isResizable = false
}

