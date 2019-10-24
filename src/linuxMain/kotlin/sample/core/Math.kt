package sample.core

open class Math {
    // TODO: Complex numbers: https://en.cppreference.com/w/cpp/numeric/complex std::complex
    private val engine = Engine()
    class max() {
        private val maxValueClearValue = 0.toByte()
        private var maxValue: Number = maxValueClearValue
        fun updateMax(value: Number) {
            val t = TypeChecker()
            val x = t.promoteToLargestNumberType(maxValue, value)
            when(x.second) {
                is Byte -> if ((maxValue.toByte()) < x.second.toByte()) maxValue = x.second.toByte()
                is Short -> if ((maxValue.toShort()) < x.second.toShort()) maxValue = x.second.toShort()
                is Int -> if ((maxValue.toInt()) < x.second.toInt()) maxValue = x.second.toInt()
                is Long -> if ((maxValue.toLong()) < x.second.toLong()) maxValue = x.second.toLong()
                is Float -> if ((maxValue.toFloat()) < x.second.toFloat()) maxValue = x.second.toFloat()
                is Double -> if ((maxValue.toDouble()) < x.second.toDouble()) maxValue = x.second.toDouble()
                else -> t.unsupportedType()
            }
        }
        fun clear() {
            maxValue = maxValueClearValue
        }
        fun max(): Number {
            return maxValue
        }
    }
    fun <T> abs(value: T): T {
        return when(value) {
            is Byte -> {
                val x = value as Byte
                if (x < 0) (-x).toByte()
                else x
            }
            is Short -> {
                val x = value as Short
                if (x < 0) (-x).toShort()
                else x
            }
            is Int -> {
                val x = value as Int
                if (x < 0) (-x).toInt()
                else x
            }
            is Long -> {
                val x = value as Long
                if (x < 0) (-x).toLong()
                else x
            }
            is Float -> {
                val x = value as Float
                if (x < 0) (-x).toFloat()
                else x
            }
            is Double -> {
                val x = value as Double
                if (x < 0) (-x).toDouble()
                else x
            }
            else -> throw TypeCastException("Unsupported type")
        } as T
    }
    fun addition(v1: Number, v2: Number) = engine.addition(v1, v2)
    fun subtraction(v1: Number, v2: Number) = engine.subtraction(v1, v2)
    fun multiplication(v1: Number, v2: Number) = engine.multiplication(v1, v2)
    fun division(v1: Number, v2: Number) = engine.division(v1, v2)
}