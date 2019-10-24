package sample.core

open class TypeChecker() {
    fun bothByte(value1: Number, value2: Number) = value1 is Byte && value2 is Byte
    fun bothShort(value1: Number, value2: Number) = value1 is Short && value2 is Short
    fun bothInt(value1: Number, value2: Number) = value1 is Int && value2 is Int
    fun bothLong(value1: Number, value2: Number) = value1 is Long && value2 is Long
    fun bothFloat(value1: Number, value2: Number) = value1 is Float && value2 is Float
    fun bothDouble(value1: Number, value2: Number) = value1 is Double && value2 is Double
    fun typesDiffer(value1: Number, value2: Number) = !(
            bothByte(value1, value2) ||
                    bothShort(value1, value2) ||
                    bothInt(value1, value2) ||
                    bothLong(value1, value2) ||
                    bothFloat(value1, value2) ||
                    bothDouble(value1, value2)
            )
    fun typesEqual(value1: Number, value2: Number) = !typesDiffer(value1, value2)
    fun isNumber(value: Number) = value is Byte || value is Short || value is Int || value is Long
    fun isFloatingPoint(value: Number) = value is Float || value is Double
    fun isNumberOrFloatingPoint(value: Number) = isNumber(value) || isFloatingPoint(value)
    fun bothValid(value1: Number, value2: Number) = isNumberOrFloatingPoint(value1) && isNumberOrFloatingPoint(value2)
    fun unsupportedType(): Nothing = throw TypeCastException("type promotion error: Unsupported type")
    fun promoteToLargestNumber(value1: Number, value2: Number) = when (value1) {
        is Byte -> {
            when (value2) {
                is Short -> Pair(value1.toShort(), value2)
                is Int -> Pair(value1.toInt(), value2)
                is Long -> Pair(value1.toLong(), value2)
                else -> unsupportedType()
            }
        }
        is Short -> {
            when (value2) {
                is Byte -> Pair(value1, value2.toShort())
                is Int -> Pair(value1.toInt(), value2)
                is Long -> Pair(value1.toLong(), value2)
                else -> unsupportedType()
            }
        }
        is Int -> {
            when (value2) {
                is Byte -> Pair(value1, value2.toInt())
                is Short -> Pair(value1, value2.toInt())
                is Long -> Pair(value1.toLong(), value2)
                else -> unsupportedType()
            }
        }
        is Long -> {
            when (value2) {
                is Byte -> Pair(value1, value2.toLong())
                is Short -> Pair(value1, value2.toLong())
                is Int -> Pair(value1, value2.toLong())
                else -> unsupportedType()
            }
        }
        else -> unsupportedType()
    } as Pair<Number, Number>
    fun promoteToFloat(value: Number) = when(value) {
        is Byte -> value.toFloat()
        is Short -> value.toFloat()
        is Int -> value.toFloat()
        is Long -> value.toFloat()
        is Float -> value
        is Double -> value
        else -> unsupportedType()
    } as Number
    fun promoteToLargestFloat(value1: Number, value2: Number): Pair<Number, Number> {
        val v1 = promoteToFloat(value1)
        val v2 = promoteToFloat(value2)
        return when (v1) {
            is Float -> {
                when (v2) {
                    is Float -> Pair(v1, v2)
                    is Double -> Pair(v1.toDouble(), v2)
                    else -> unsupportedType()
                }
            }
            is Double -> {
                when (v2) {
                    is Float -> Pair(v1, v2.toDouble())
                    is Double -> Pair(v1, v2)
                    else -> unsupportedType()
                }
            }
            else -> unsupportedType()
        } as Pair<Number, Number>
    }
    fun promoteToLargestNumberType(value1: Number, value2: Number) = when {
        typesDiffer(value1, value2) -> when {
            isNumber(value1) -> when {
                isNumber(value2) -> promoteToLargestNumber(value1, value2)
                isFloatingPoint(value2) -> promoteToLargestFloat(promoteToFloat(value1), value2)
                else -> unsupportedType()
            }
            isFloatingPoint(value1) -> when {
                isNumber(value2) -> promoteToLargestFloat(value1, promoteToFloat(value2))
                isFloatingPoint(value2) -> promoteToLargestFloat(value1, value2)
                else -> unsupportedType()
            }
            else -> unsupportedType()
        }
        else -> Pair(value1, value2)
    }
    // TODO
    fun defaultFromType(value1: Number) = when {
        isNumber(value1) -> 0
        else -> when(value1) {
            is Float -> 0F
            is Double -> 0.0
            else -> unsupportedType()
        }
    } as Number
    fun defaultFromType(value1: Number, setter: (value: Number) -> Unit) = when {
        isNumber(value1) -> setter(0)
        else -> when(value1) {
            is Float -> setter(0F)
            is Double -> setter(0.0)
            else -> unsupportedType()
        }
    } as Number
}