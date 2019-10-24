package sample.core

class Operator() : TypeChecker() {
    val operationAddition: Short = 1
    val operationSubtraction: Short = 2
    val operationMultiplication: Short = 3
    val operationDivision: Short = 4
    var allowAutomaticTypePromotion = false

    private val unsupportedOperationString = "operation: Unsupported operation: only Addition, Subtraction, Multiplication, and Division operations are available"
    private val unsupportedTypeString = "operation: Unsupported type"
    
    fun detectOverflow(operation: Short, value1: Number, value2: Number): Boolean = when (value1) {
        // assume both values to be the same type
        is Byte -> {
            val a = value1 as Byte
            val b = value2 as Byte
            val max = Byte.MAX_VALUE
            when (operation) {
                operationAddition -> a > 0 && max - a < b
                operationSubtraction -> b < 0 && max + b < a
                operationMultiplication -> {
                    if (a < b) detectOverflow(operation, b, a)
                    else {
                        var sum: Byte = 0
                        var i: Byte = 0
                        var overflow = false
                        while(i < Math().abs(b)) {
                            if (detectOverflow(operationAddition, sum, a)) {
                                overflow = true
                                break
                            }
                            sum = (sum + a).toByte()
                            i++
                        }
                        overflow
                    }
                }
                else -> false
            }
        }
        is Short -> {
            val a = value1 as Short
            val b = value2 as Short
            val max = Short.MAX_VALUE
            when (operation) {
                operationAddition -> a > 0 && max - a < b
                operationSubtraction -> b < 0 && max + b < a
                operationMultiplication -> {
                    if (a < b) detectOverflow(operation, b, a)
                    else {
                        var sum: Short = 0
                        var i: Short = 0
                        var overflow = false
                        while(i < Math().abs(b)) {
                            if (detectOverflow(operationAddition, sum, a)) {
                                overflow = true
                                break
                            }
                            sum = (sum + a).toShort()
                            i++
                        }
                        overflow
                    }
                }
                else -> false
            }
        }
        is Int -> {
            val a = value1 as Int
            val b = value2 as Int
            val max = Int.MAX_VALUE
            when (operation) {
                operationAddition -> a > 0 && max - a < b
                operationSubtraction -> b < 0 && max + b < a
                operationMultiplication -> {
                    if (a < b) detectOverflow(operation, b, a)
                    else {
                        var sum: Int = 0
                        var i: Int = 0
                        var overflow = false
                        while(i < Math().abs(b)) {
                            if (detectOverflow(operationAddition, sum, a)) {
                                overflow = true
                                break
                            }
                            sum = (sum + a).toInt()
                            i++
                        }
                        overflow
                    }
                }
                else -> false
            }
        }
        is Long -> {
            val a = value1 as Long
            val b = value2 as Long
            val max = Long.MAX_VALUE
            when (operation) {
                operationAddition -> a > 0 && max - a < b
                operationSubtraction -> b < 0 && max + b < a
                operationMultiplication -> {
                    if (a < b) detectOverflow(operation, b, a)
                    else {
                        var sum: Long = 0
                        var i: Long = 0
                        var overflow = false
                        while(i < Math().abs(b)) {
                            if (detectOverflow(operationAddition, sum, a)) {
                                overflow = true
                                break
                            }
                            sum = (sum + a).toLong()
                            i++
                        }
                        overflow
                    }
                }
                else -> false
            }
        }
        is Float -> {
            val a = value1 as Float
            val b = value2 as Float
            val max = Float.MAX_VALUE
            when (operation) {
                operationAddition -> a > 0 && max - a < b
                operationSubtraction -> b < 0 && max + b < a
                operationMultiplication -> {
                    if (a < b) detectOverflow(operation, b, a)
                    else {
                        var sum: Float = 0F
                        var i: Float = 0F
                        var overflow = false
                        while(i < Math().abs(b)) {
                            if (detectOverflow(operationAddition, sum, a)) {
                                overflow = true
                                break
                            }
                            sum = (sum + a).toFloat()
                            i++
                        }
                        overflow
                    }
                }
                else -> false
            }
        }
        is Double -> {
            val a = value1 as Double
            val b = value2 as Double
            val max = Double.MAX_VALUE
            when (operation) {
                operationAddition -> a > 0 && max - a < b
                operationSubtraction -> b < 0 && max + b < a
                operationMultiplication -> {
                    if (a < b) detectOverflow(operation, b, a)
                    else {
                        var sum: Double = 0.0
                        var i: Double = 0.0
                        var overflow = false
                        while(i < Math().abs(b)) {
                            if (detectOverflow(operationAddition, sum, a)) {
                                overflow = true
                                break
                            }
                            sum = (sum + a).toDouble()
                            i++
                        }
                        overflow
                    }
                }
                else -> false
            }
        }
        else -> throw TypeCastException(unsupportedTypeString)
    }

    fun detectUnderflow(operation: Short, value1: Number, value2: Number): Boolean = when (value1) {
        // assume both values to be the same type
        is Byte -> {
            val a = value1 as Byte
            val b = value2 as Byte
            val min = Byte.MIN_VALUE
            when (operation) {
                operationAddition -> a < 0 && min - a > b
                operationSubtraction -> b > 0 && min + b > a
                operationMultiplication -> {
                    if (a < b) detectUnderflow(operation, b, a)
                    else {
                        var sum: Byte = 0
                        var i: Byte = 0
                        var underflow = false
                        while(i < Math().abs(b)) {
                            if (detectUnderflow(operationAddition, sum, a)) {
                                underflow = true
                                break
                            }
                            sum = (sum + a).toByte()
                            i++
                        }
                        underflow
                    }
                }
                else -> false
            }
        }
        is Short -> {
            val a = value1 as Short
            val b = value2 as Short
            val min = Short.MIN_VALUE
            when (operation) {
                operationAddition -> a < 0 && min - a > b
                operationSubtraction -> b > 0 && min + b > a
                operationMultiplication -> {
                    if (a < b) detectUnderflow(operation, b, a)
                    else {
                        var sum: Short = 0
                        var i: Short = 0
                        var underflow = false
                        while(i < Math().abs(b)) {
                            if (detectUnderflow(operationAddition, sum, a)) {
                                underflow = true
                                break
                            }
                            sum = (sum + a).toShort()
                            i++
                        }
                        underflow
                    }
                }
                else -> false
            }
        }
        is Int -> {
            val a = value1 as Int
            val b = value2 as Int
            val min = Int.MIN_VALUE
            when (operation) {
                operationAddition -> a < 0 && min - a > b
                operationSubtraction -> b > 0 && min + b > a
                operationMultiplication -> {
                    if (a < b) detectUnderflow(operation, b, a)
                    else {
                        var sum: Int = 0
                        var i: Int = 0
                        var underflow = false
                        while(i < Math().abs(b)) {
                            if (detectUnderflow(operationAddition, sum, a)) {
                                underflow = true
                                break
                            }
                            sum = (sum + a).toInt()
                            i++
                        }
                        underflow
                    }
                }
                else -> false
            }
        }
        is Long -> {
            val a = value1 as Long
            val b = value2 as Long
            val min = Long.MIN_VALUE
            when (operation) {
                operationAddition -> a < 0 && min - a > b
                operationSubtraction -> b > 0 && min + b > a
                operationMultiplication -> {
                    if (a < b) detectUnderflow(operation, b, a)
                    else {
                        var sum: Long = 0
                        var i: Long = 0
                        var underflow = false
                        while(i < Math().abs(b)) {
                            if (detectUnderflow(operationAddition, sum, a)) {
                                underflow = true
                                break
                            }
                            sum = (sum + a).toLong()
                            i++
                        }
                        underflow
                    }
                }
                else -> false
            }
        }
        is Float -> {
            val a = value1 as Float
            val b = value2 as Float
            val min = Float.MIN_VALUE
            when (operation) {
                operationAddition -> a < 0 && min - a > b
                operationSubtraction -> b > 0 && min + b > a
                operationMultiplication -> {
                    if (a < b) detectUnderflow(operation, b, a)
                    else {
                        var sum: Float = 0F
                        var i: Float = 0F
                        var underflow = false
                        while(i < Math().abs(b)) {
                            if (detectUnderflow(operationAddition, sum, a)) {
                                underflow = true
                                break
                            }
                            sum = (sum + a).toFloat()
                            i++
                        }
                        underflow
                    }
                }
                else -> false
            }
        }
        is Double -> {
            val a = value1 as Double
            val b = value2 as Double
            val min = Double.MIN_VALUE
            when (operation) {
                operationAddition -> a < 0 && min - a > b
                operationSubtraction -> b > 0 && min + b > a
                operationMultiplication -> {
                    if (a < b) detectUnderflow(operation, b, a)
                    else {
                        var sum: Double = 0.0
                        var i: Double = 0.0
                        var underflow = false
                        while(i < Math().abs(b)) {
                            if (detectUnderflow(operationAddition, sum, a)) {
                                underflow = true
                                break
                            }
                            sum = (sum + a).toDouble()
                            i++
                        }
                        underflow
                    }
                }
                else -> false
            }
        }
        else -> throw TypeCastException(unsupportedTypeString)
    }

    fun <T> process(value1: Number, value2: Number, result: (v1: T, v2: T) -> Number) = result(value1 as T, value2 as T)

    fun promoteMessage(Type: String, NewType: String?) =
        println("Warning: the current expression (of type $Type) would result in a value larger than " +
                "the maximum possible value of that a $Type type can hold" +
                if (NewType != null) ", to prevent this from occurring, " +
                        "the current expression (of type $Type) will now be promoted to an expression of type $NewType"
                else ", however no type currently exists in which is capable of holding the larger value")

    fun promoteMessage(Type: String) = promoteMessage(Type, null)

    fun convert(operation: Short, value1: Number, value2: Number, result: (v1: Int, v2: Int) -> Number) = when (value1) {
        is Byte -> {
            val a = value1 as Byte
            val b = value2 as Byte
            var promotionNeeded = false
            if (detectOverflow(operation, a, b)) promotionNeeded = true
            if (detectUnderflow(operation, a, b)) {
                if (promotionNeeded) throw IllegalStateException(
                    "both overflow and underflow should not be allowed to occur in the same expression"
                )
                promotionNeeded = true
            }
            if (promotionNeeded) {
                promoteMessage("Byte", "Short")
                val a = a.toShort()
                val b = a.toShort()
                var promotionNeeded = false
                if (detectOverflow(operation, a, b)) promotionNeeded = true
                if (detectUnderflow(operation, a, b)) {
                    if (promotionNeeded)
                        throw IllegalStateException(
                            "both overflow and underflow should not be allowed to occur in the same expression"
                        )
                    promotionNeeded = true
                }
                if (promotionNeeded) {
                    promoteMessage("Short", "Int")
                    val a = a.toInt()
                    val b = a.toInt()
                    var promotionNeeded = false
                    if (detectOverflow(operation, a, b)) promotionNeeded = true
                    if (detectUnderflow(operation, a, b)) {
                        if (promotionNeeded)
                            throw IllegalStateException(
                                "both overflow and underflow should not be allowed to occur in the same expression"
                            )
                        promotionNeeded = true
                    }
                    if (promotionNeeded) {
                        promoteMessage("Int", "Long")
                        val a = a.toLong()
                        val b = a.toLong()
                        var promotionNeeded = false
                        if (detectOverflow(operation, a, b)) {
                            promoteMessage("Long")
                            println("warning: an Overflow will occur")
                        }
                        if (detectUnderflow(operation, a, b)) {
                            if (promotionNeeded) throw IllegalStateException(
                                "both overflow and underflow should not be allowed to occur in the same expression"
                            )
                            promoteMessage("Long")
                            println("warning: an Underflow will occur")
                        }
                        process<Long>(a, b, result as (v1: Long, v2: Long) -> Number).toLong()
                    } else process<Int>(a, b, result as (v1: Int, v2: Int) -> Number).toInt()
                } else process<Short>(a, b, result as (v1: Short, v2: Short) -> Number).toShort()
            } else process<Byte>(a, b, result as (v1: Byte, v2: Byte) -> Number).toByte()
        }
        is Short -> {
            val a = value1 as Short
            val b = value2 as Short
            var promotionNeeded = false
            if (detectOverflow(operation, a, b)) promotionNeeded = true
            if (detectUnderflow(operation, a, b)) {
                if (promotionNeeded)
                    throw IllegalStateException(
                        "both overflow and underflow should not be allowed to occur in the same expression"
                    )
                promotionNeeded = true
            }
            if (promotionNeeded) {
                promoteMessage("Short", "Int")
                val a = a.toInt()
                val b = a.toInt()
                var promotionNeeded = false
                if (detectOverflow(operation, a, b)) promotionNeeded = true
                if (detectUnderflow(operation, a, b)) {
                    if (promotionNeeded)
                        throw IllegalStateException(
                            "both overflow and underflow should not be allowed to occur in the same expression"
                        )
                    promotionNeeded = true
                }
                if (promotionNeeded) {
                    promoteMessage("Int", "Long")
                    val a = a.toLong()
                    val b = a.toLong()
                    var promotionNeeded = false
                    if (detectOverflow(operation, a, b)) {
                        promoteMessage("Long")
                        println("warning: an Overflow will occur")
                    }
                    if (detectUnderflow(operation, a, b)) {
                        if (promotionNeeded) throw IllegalStateException(
                            "both overflow and underflow should not be allowed to occur in the same expression"
                        )
                        promoteMessage("Long")
                        println("warning: an Underflow will occur")
                    }
                    process<Long>(a, b, result as (v1: Long, v2: Long) -> Number).toLong()
                } else process<Int>(a, b, result as (v1: Int, v2: Int) -> Number).toInt()
            } else process<Short>(a, b, result as (v1: Short, v2: Short) -> Number).toShort()
        }
        is Int -> {
            val a = value1 as Int
            val b = value2 as Int
            var promotionNeeded = false
            if (detectOverflow(operation, a, b)) promotionNeeded = true
            if (detectUnderflow(operation, a, b)) {
                if (promotionNeeded)
                    throw IllegalStateException(
                        "both overflow and underflow should not be allowed to occur in the same expression"
                    )
                promotionNeeded = true
            }
            if (promotionNeeded) {
                promoteMessage("Int", "Long")
                val a = a.toLong()
                val b = a.toLong()
                var promotionNeeded = false
                if (detectOverflow(operation, a, b)) {
                    promoteMessage("Long")
                    println("warning: an Overflow will occur")
                }
                if (detectUnderflow(operation, a, b)) {
                    if (promotionNeeded) throw IllegalStateException(
                        "both overflow and underflow should not be allowed to occur in the same expression"
                    )
                    promoteMessage("Long")
                    println("warning: an Underflow will occur")
                }
                process<Long>(a, b, result as (v1: Long, v2: Long) -> Number).toLong()
            } else process<Int>(a, b, result as (v1: Int, v2: Int) -> Number).toInt()
        }
        is Long -> {
            val a = value1 as Long
            val b = value2 as Long
            var promotionNeeded = false
            if (detectOverflow(operation, a, b)) {
                promoteMessage("Long")
                println("warning: an Overflow will occur")
            }
            if (detectUnderflow(operation, a, b)) {
                if (promotionNeeded) throw IllegalStateException(
                    "both overflow and underflow should not be allowed to occur in the same expression"
                )
                promoteMessage("Long")
                println("warning: an Underflow will occur")
            }
            process<Long>(a, b, result as (v1: Long, v2: Long) -> Number).toLong()
        }
        is Float -> {
            val a = value1 as Float
            val b = value2 as Float
            var promotionNeeded = false
            if (detectOverflow(operation, a, b)) promotionNeeded = true
            if (detectUnderflow(operation, a, b)) {
                if (promotionNeeded)
                    throw IllegalStateException(
                        "both overflow and underflow should not be allowed to occur in the same expression"
                    )
                promotionNeeded = true
            }
            if (promotionNeeded) {
                promoteMessage("Float", "Double")
                val a = a.toDouble()
                val b = a.toDouble()
                var promotionNeeded = false
                if (detectOverflow(operation, a, b)) {
                    promotionNeeded = true
                    promoteMessage("Double")
                    println("warning: an Overflow will occur")
                }
                if (detectUnderflow(operation, a, b)) {
                    if (promotionNeeded) throw IllegalStateException(
                        "both overflow and underflow should not be allowed to occur in the same expression"
                    )
                    promoteMessage("Double")
                    println("warning: an Underflow will occur")
                }
                process<Double>(a, b, result as (v1: Double, v2: Double) -> Number).toDouble()
            } else process<Float>(a, b, result as (v1: Float, v2: Float) -> Number).toFloat()
        }
        is Double -> {
            val a = value1 as Double
            val b = value2 as Double
            var promotionNeeded = false
            if (detectOverflow(operation, a, b)) {
                promotionNeeded = true
                promoteMessage("Double")
                println("warning: an Overflow will occur")
            }
            if (detectUnderflow(operation, a, b)) {
                if (promotionNeeded) throw IllegalStateException(
                    "both overflow and underflow should not be allowed to occur in the same expression"
                )
                promoteMessage("Double")
                println("warning: an Underflow will occur")
            }
            process<Double>(a, b, result as (v1: Double, v2: Double) -> Number).toDouble()
        }
        else -> throw TypeCastException(unsupportedTypeString)
    } as Number

    fun x(operation: Short, value1: Number, value2: Number): Number {
        return when(operation) {
            operationAddition -> convert(operation, value1, value2) {v1, v2 -> v1 + v2 }
            operationSubtraction -> convert(operation, value1, value2) {v1, v2 -> v1 - v2 }
            operationMultiplication -> convert(operation, value1, value2) {v1, v2 -> v1 * v2 }
            operationDivision -> convert(operation, value1, value2) {v1, v2 -> v1 / v2 }
            else -> throw UnsupportedOperationException(unsupportedOperationString)
        }
    }
    fun x(operation: Short, pair: Pair<Number,Number>): Number = x(operation, pair.first, pair.second)

    private fun isValidOperation(operator: Short) = operator == operationAddition || operator == operationSubtraction ||
            operator == operationMultiplication || operator == operationDivision
    @Throws(TypeCastException::class, UnsupportedOperationException::class)
    fun operation(operation: Short, value1: Number, value2: Number): Number = when {
        isValidOperation(operation) -> when {
            bothValid(value1, value2) -> when {
                typesDiffer(value1, value2) -> when {
                    isNumber(value1) -> when {
                        isNumber(value2) -> {
                            val x = promoteToLargestNumber(value1, value2)
                            when {
                                bothShort(x.first, x.second) -> x(operation, x)
                                bothInt(x.first, x.second) -> x(operation, x)
                                bothLong(x.first, x.second) -> x(operation, x)
                                else -> throw TypeCastException(unsupportedTypeString)
                            }
                        }
                        isFloatingPoint(value2) -> {
                            val x = promoteToLargestFloat(promoteToFloat(value1), value2)
                            if (bothFloat(x.first, x.second)) x(operation, x)
                            else x(operation, x)
                        }
                        else -> throw TypeCastException(unsupportedTypeString)
                    }
                    isFloatingPoint(value1) -> when {
                        isNumber(value2) -> {
                            val x = promoteToLargestFloat(value1, promoteToFloat(value2))
                            if (bothFloat(x.first, x.second)) x(operation, x)
                            else x(operation, x)
                        }
                        isFloatingPoint(value2) -> {
                            val x = promoteToLargestFloat(value1, value2)
                            if (bothFloat(x.first, x.second)) x(operation, x)
                            else x(operation, x)
                        }
                        else -> throw TypeCastException(unsupportedTypeString)
                    }
                    else -> throw TypeCastException(unsupportedTypeString)
                }
                else -> x(operation, value1, value2)
            }
            else -> throw TypeCastException(unsupportedTypeString)
        }
        else -> throw UnsupportedOperationException(unsupportedOperationString)
    }
    fun addition(value1: Number, value2: Number) = operation(operationAddition, value1, value2)
    fun subtraction(value1: Number, value2: Number) = operation(operationSubtraction, value1, value2)
    fun multiplication(value1: Number, value2: Number) = operation(operationMultiplication, value1, value2)
    fun division(value1: Number, value2: Number) = operation(operationDivision, value1, value2)
}
