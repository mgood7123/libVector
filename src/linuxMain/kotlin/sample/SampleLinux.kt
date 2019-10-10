package sample

// Mathematical Terms

/*
Linear Algebra is primarily the study of vector spaces

Vector space
    A collection of vectors, where vectors are objects that may be added together and
    multiplied by scalars

    Euclidean vectors are an example of a vector space, typically used to represent
    displacements, as well as physical quantities such as force or momentum

Dimensions of a vector space
    The number of coordinates required to specify any point within the space
 */

/*
Matrix
    A rectangular arrangement of numbers, symbols, or expressions organized in rows
    and columns

    A matrix having R rows and C columns is said to have size R x C

    Matrices provide a useful way of representing linear transformations from one
    vector space to another

Element
    An individual member of the rectangular arrangement comprising the matrix

    Rows are traditionally indexed from 1 to R, and columns from 1 to C

    In matrix A, element a11 appears in the upper left-hand corner, while element
    aRC appears in the lower right-hand corner
 */

/*
Row vector
    A matrix containing a single row - a matrix of size 1 x C

    The rows of the matrix are sometimes called row vectors

Column vector
    A matrix containing a single column - a matrix of size R x 1

    The columns of the matrix are sometimes called column vectors

NOTE: We do not distinguish between Row vectors and Column vectors
      which themselves are matrices that represent a single column
      of another matrix

Rank (of a matrix)
    The dimension of the vector space spanned by its rows/columns
    for example, if you have a matrix of 3 x 3 it would be Rank 3
    if you have a matrix of 3 x 4 it would be Rank 3

    Also equal to the maximum of linearly-independent rows/columns

 */

/*
Element transforms
    Non-arithmetic operations that allow modifying the relative positions of elements in a
    matrix, such as transpose, column exchange, and row exchange

Element arithmetic
    Arithmetical operations that read or modify the values of individual elements
    independently of other elements

Matrix arithmetic
    Assignment, addition, subtraction, negation, and multiplication operations
    defined for matrices and vectors as wholes
 */

/*
Decompositions
    Complex sequences of arithmetic operations, element arithmetic, and element
    transforms performed upon a matrix to determine the important mathematical
    properties of that matrix

Eigen-decompositions
    Sequences of operations performed upon a symmetric matrix in order to compute
    the eigenvalues and eigenvectors of that matrix

 */

// Terms regarding Types

/*
Math object
    Generically, one of the types matrix or vector described here

Storage
    A synonym for memory

Dense
    A math object representation with storage allocated for every element

Sparse
    A math object representation with storage allocated only for non-zero elements
 */

/*
Engines are implementation types that manage the resources associated with a math object
    Element storage ownership and lifetime

    Access to individual elements

    Resizing/reserving, if appropriate

    Execution context

In this interface design, an engine object is a private member of a containing
math object, ei a matrix object contains a matrix engine

Other than as a template parameter, engines are not part of a math object's
public interface
 */

/*
Traits
    A (usually) stateless class or class template whose members provide an interface
    normally over some set of types or template parameters

    Often appear as parameters in class/function templates

Row capacity / column capacity
    The maximum number of rows/columns that t    The process of determining the resulting element type is element promotion
he math object could possibly have

Row size / column size
    The number of rows/columns that the math object actually has
    Must be less than or equal to the corresponding row/column capacities
 */

/*
Fixed-size
    An engine type whose row/column sizes are fixed and known at compile time

Fixed-capacity
    An engine type whose row/column capacities are fixed and known at compile time

Dynamically re-sizable
    An engine type whose row/column sizes/capacities are set at run time
 */

/*
In programming, dimension refers to the number of indices required to
access an element of an array

In linear algebra, a vector space V is n-dimensional if there exists n
linearly independent vectors that span V

We use dimension both ways
    A vector describing a point in an electric field is a one-dimensional data structure
    implemented as a three-dimensional vector

    A rotation matrix used by a game engine is two-dimensional data structure
    composed of three-dimensional row and column vectors
 */

// Design Aspects

/*
Capacity and resizability
    In some problem domains, it is useful for a math object to have excess storage
    capacity, so that resizes fo not require reallocations

    In other problem domains (like graphics) math objects are small and never resize

Expressions with mixed element types
    In general, when multiple primitive types are present in a arithmetic expression,
    the resulting type is the "largest" of all the types

    Information should be preserved

    The process of determining the resulting element type is element promotion

Expressions with mixed engine types
    Consider fixed-size matrix multiplied by a dynamically-resizable matrix

    The resulting engine should be at least as "general" as the "most general" of all
    the engine types participating in the expression

    Determining the resulting engine type is called engine promotion
 */

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
    fun isNumber(value: Number) = value is Byte || value is Short || value is Int || value is Long
    fun isFloatingPoint(value: Number) = value is Float || value is Double
    fun isNumberOrFloatingPoint(value: Number) = isNumber(value) || isFloatingPoint(value)
    fun bothValid(value1: Number, value2: Number) = isNumberOrFloatingPoint(value1) && isNumberOrFloatingPoint(value2)
    fun promoteToLargestNumber(value1: Number, value2: Number) = when (value1) {
        is Byte -> {
            when (value2) {
                is Short -> Pair(value1.toShort(), value2)
                is Int -> Pair(value1.toInt(), value2)
                is Long -> Pair(value1.toLong(), value2)
                else -> throw TypeCastException("type promotion error: Unsupported type")
            }
        }
        is Short -> {
            when (value2) {
                is Byte -> Pair(value1, value2.toShort())
                is Int -> Pair(value1.toInt(), value2)
                is Long -> Pair(value1.toLong(), value2)
                else -> throw TypeCastException("type promotion error: Unsupported type")
            }
        }
        is Int -> {
            when (value2) {
                is Byte -> Pair(value1, value2.toInt())
                is Short -> Pair(value1, value2.toInt())
                is Long -> Pair(value1.toLong(), value2)
                else -> throw TypeCastException("type promotion error: Unsupported type")
            }
        }
        is Long -> {
            when (value2) {
                is Byte -> Pair(value1, value2.toLong())
                is Short -> Pair(value1, value2.toLong())
                is Int -> Pair(value1, value2.toLong())
                else -> throw TypeCastException("type promotion error: Unsupported type")
            }
        }
        else -> throw TypeCastException("type promotion error: Unsupported type")
    } as Pair<Number, Number>
    fun promoteToFloat(value: Number) = when(value) {
        is Byte -> value.toFloat()
        is Short -> value.toFloat()
        is Int -> value.toFloat()
        is Long -> value.toFloat()
        is Float -> value
        is Double -> value
        else -> throw TypeCastException("type promotion error: Unsupported type")
    } as Number
    fun promoteToLargestFloat(value1: Number, value2: Number): Pair<Number, Number> {
        val v1 = promoteToFloat(value1)
        val v2 = promoteToFloat(value2)
        return when (v1) {
            is Float -> {
                when (v2) {
                    is Float -> Pair(v1, v2)
                    is Double -> Pair(v1.toDouble(), v2)
                    else -> throw TypeCastException("type promotion error: Unsupported type")
                }
            }
            is Double -> {
                when (v2) {
                    is Float -> Pair(v1, v2.toDouble())
                    is Double -> Pair(v1, v2)
                    else -> throw TypeCastException("type promotion error: Unsupported type")
                }
            }
            else -> throw TypeCastException("type promotion error: Unsupported type")
        } as Pair<Number, Number>
    }
    // TODO
    fun defaultFromType(value1: Number) = when {
        isNumber(value1) -> 0
        else -> when(value1) {
            is Float -> 0F
            is Double -> 0.0
            else -> throw TypeCastException("type promotion error: Unsupported type")
        }
    } as Number
    fun defaultFromType(value1: Number, setter: (value: Number) -> Unit) = when {
        isNumber(value1) -> setter(0)
        else -> when(value1) {
            is Float -> setter(0F)
            is Double -> setter(0.0)
            else -> throw TypeCastException("type promotion error: Unsupported type")
        }
    } as Number
}

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
                        val absOfB = if (b<0) (-b).toByte() else b
                        while(i < absOfB) {
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
                        val absOfB = if (b<0) (-b).toShort() else b
                        while(i < absOfB) {
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
                        val absOfB = if (b<0) (-b).toInt() else b
                        while(i < absOfB) {
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
                        val absOfB = if (b<0) (-b).toLong() else b
                        while(i < absOfB) {
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
                        val absOfB = if (b<0) (-b).toFloat() else b
                        while(i < absOfB) {
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
                        val absOfB = if (b<0) (-b).toDouble() else b
                        while(i < absOfB) {
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
                        val absOfB = if (b<0) (-b).toByte() else b
                        while(i < absOfB) {
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
                        val absOfB = if (b<0) (-b).toShort() else b
                        while(i < absOfB) {
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
                        val absOfB = if (b<0) (-b).toInt() else b
                        while(i < absOfB) {
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
                        val absOfB = if (b<0) (-b).toLong() else b
                        while(i < absOfB) {
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
                        val absOfB = if (b<0) (-b).toFloat() else b
                        while(i < absOfB) {
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
                        val absOfB = if (b<0) (-b).toDouble() else b
                        while(i < absOfB) {
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

open class Engine {
    private val operator = Operator()
    fun addition(v1: Number, v2: Number) = operator.addition(v1, v2)
    fun subtraction(v1: Number, v2: Number) = operator.subtraction(v1, v2)
    fun multiplication(v1: Number, v2: Number) = operator.multiplication(v1, v2)
    fun division(v1: Number, v2: Number) = operator.division(v1, v2)
    open var isFixedSize = true
    open var isResizable = false
}

open class EngineFixedSize: Engine() {
}

open class Math {
    // TODO: Complex numbers: https://en.cppreference.com/w/cpp/numeric/complex std::complex
    private val engine = Engine()
    fun addition(v1: Number, v2: Number) = engine.addition(v1, v2)
    fun subtraction(v1: Number, v2: Number) = engine.subtraction(v1, v2)
    fun multiplication(v1: Number, v2: Number) = engine.multiplication(v1, v2)
    fun division(v1: Number, v2: Number) = engine.division(v1, v2)
}

/**
 * at the bare minimum i want to make a vector library that is easy to understand
 * and easily reimplementable, while maintaining an official adheration to the
 * definition of what a vector is and how it should behave aswell as what operations
 * it has and so on for those operations
 * (eg what those operations are and how they should behave, and what each operation does)
 * eg so that even someone with absolutely zero knowledge
 * (or at least is farmiliar with basic math operators like `+`, `-`, `/` and `*`)
 * of maths and algebra (like myself) can understand how a vector
 * (and a vector space, and anything that may also be related) works
 */

open class VectorCore: Math {
    // constructors
    constructor() : super() {
        this.vector = mutableListOf()
    }
    constructor(dimension: Int) : super() {
        this.vector = mutableListOf()
        this.resize(dimension)
    }
    // variables
    protected val vector: MutableList<Number?>
    var dimensions: Int
        get() = vector.size
        set(value: Int) = resize(value)
    // vector resizing operations
    fun resize(size: Int) = when {
        size == 0 -> vector.clear()
        size > vector.size -> while(size > vector.size) vector.add(null)
        else -> while(size < vector.size) vector.removeAt(vector.lastIndex)
    }
    fun addDimension(default: Number) = vector.add(default)
    fun removeDimension(dimension: Int) = vector.removeAt(dimension)
    // vector indexing
    operator fun get(index: Int) = vector.get(index)
    fun getOrZero(index: Int): Number = if (get(index) == null) 0 else get(index)!!
    operator fun set(index: Int, value: Number) {
        vector[index] = value
    }
    fun clone() = VectorCore().also { it.vector.addAll(vector) }
    override fun toString(): String = vector.toString()
    fun info(): String {
        return "dimensions: $dimensions\n" +
                "size: ${vector.size}\n" +
                "vector: $vector\n"
    }
    fun iterator(): Iterator<Number?> = vector.iterator()
    fun forEach(action: (Item: Number?) -> Unit) = vector.forEach {
        action(it)
    }
    fun forEachIndex(action: (index: Int) -> Unit) = vector.forEachIndexed { index, type ->
        action(index)
    }
}

open class VectorBase: VectorCore {
    // constructors
    constructor() : super()
    constructor(dimension: Int) : super(dimension)

    /**
     * perform **action** on each element of two vectors respectively as a pair
     *
     * *example:*
     *
     * ```
     * val A = Vector()
     * A.addDimension(1)
     * A.addDimension(2)
     * A.addDimension(3)
     * val B = Vector()
     * B.addDimension(2)
     * B.addDimension(2)
     * B.addDimension(2)
     * val C = A.elementWiseOperation(B) { result, lhs, rhs ->
     *     result.addDimension(multiplication(lhs, rhs))
     * }
     *
     * [1]   [2]   [1*2]   [2]
     * [2] * [2] = [2*2] = [4]
     * [3]   [2]   [3*2]   [6]
     * ```
     * @see elementWiseAddition
     * @see elementWiseSubtraction
     * @see elementWiseMultiplication
     * @see elementWiseDivision
     */
    fun elementWiseOperation(vector: VectorCore, action: (result: VectorCore, lhs: Number, rhs: Number) -> Unit): VectorBase {
        val v1 = VectorCore()
        val vA = clone()
        val vB = vector.clone()
        if (dimensions != vector.dimensions) {
            vA.resize(if (dimensions > vector.dimensions) dimensions else vector.dimensions)
            vB.resize(if (dimensions > vector.dimensions) dimensions else vector.dimensions)
        }
        vA.forEachIndex {
            action(v1, vA.getOrZero(it), vB.getOrZero(it))
        }
        return v1 as VectorBase
    }

    /**
     * perform **addition** on each element of two vectors respectively as a pair
     * @see elementWiseOperation
     * @see elementWiseSubtraction
     * @see elementWiseMultiplication
     * @see elementWiseDivision
     */
    fun elementWiseAddition(vector: VectorCore) = elementWiseOperation(vector) { result, lhs, rhs ->
        result.addDimension(addition(lhs, rhs))
    }

    /**
     * perform **subtraction** on each element of two vectors respectively as a pair
     * @see elementWiseOperation
     * @see elementWiseAddition
     * @see elementWiseMultiplication
     * @see elementWiseDivision
     */
    fun elementWiseSubtraction(vector: VectorCore) = elementWiseOperation(vector) { result, lhs, rhs ->
        result.addDimension(subtraction(lhs, rhs))
    }

    /**
     * perform **multiplication** on each element of two vectors respectively as a pair
     * @see elementWiseOperation
     * @see elementWiseAddition
     * @see elementWiseSubtraction
     * @see elementWiseDivision
     */
    fun elementWiseMultiplication(vector: VectorCore) = elementWiseOperation(vector) { result, lhs, rhs ->
        result.addDimension(multiplication(lhs, rhs))
    }

    /**
     * perform **division** on each element of two vectors respectively as a pair
     * @see elementWiseOperation
     * @see elementWiseAddition
     * @see elementWiseSubtraction
     * @see elementWiseMultiplication
     */
    fun elementWiseDivision(vector: VectorCore) = elementWiseOperation(vector) { result, lhs, rhs ->
        result.addDimension(division(lhs, rhs))
    }

    fun sum(): Number {
        var sum: Number = 0.toByte()
        forEachIndex {
            sum = addition(sum, getOrZero(it))
        }
        return sum
    }

    /**
     * performs [element-wise multiplication][elementWiseMultiplication] on **this vector** and the **input vector**
     */
    fun hadamardProduct(vector: VectorCore) = elementWiseMultiplication(vector)

    /**
     * scales a vector by a factor of **number**
     */
    fun scale(number: Int) = elementWiseOperation(this) { result, lhs, rhs ->
        result.addDimension(number)
    }.hadamardProduct(this)

    /**
     * performs vector addition
     */
    fun vectorAddition(vector: VectorCore) = elementWiseAddition(vector)

    /**
     * scales a vector by a factor of **number**
     */
    fun scalarMultiplication(number: Int) = scale(number)

    /**
     * Linear Combination is the addition of scaled vectors
     *
     * **example**: linear combination: 5V1 + 5V2
     * ```
     * V1 = vector(1,2,3)
     * V2 = vector(1,2,3)
     * V3 = V1.scale(5) + V2.scale(5)
     * // V3 is equivilant to
     * // V3 = vector(10,20,30)
     * ```
     */
    fun linearCombination(number: Int) = scale(number)


    /**
     * returns the **sum** of the [hadamardProduct]
     *
     * example: ***`[1, 2] · [1, 2] -> [1*1, 2*2] -> [1, 4] -> 1 + 4 -> 5`***
     */
    fun dotProduct(vector: VectorCore) = hadamardProduct(vector).sum()

    // synonyms for dotProduct

    /**
     * see [dotProduct]
     */
    fun scalarProduct(vector: VectorCore) = dotProduct(vector)

    /**
     * see [dotProduct]
     */
    infix fun `·`(vector: VectorCore) = scalarProduct(vector)

}

open class Vector : VectorBase {
    constructor() : super()
    constructor(dimension: Int) : super(dimension)

    /**
     * performs [vector addition][vectorAddition]
     */
    operator fun plus(vector: Vector) = vectorAddition(vector) as Vector
    /**
     * performs [scalar multiplication][scalarMultiplication]
     */
    operator fun times(number: Int) = scalarMultiplication(number) as Vector
}

open class VectorSpaceCore: Math {
    // constructors
    constructor() : super() {
        this.vector = mutableListOf()
    }
    constructor(dimension: Int) : super() {
        this.vector = mutableListOf()
        this.resize(dimension)
    }
    // variables
    protected val vector: MutableList<Vector?>
    // TODO: correct this to adhere to the rules of a Vector Space
    var dimensions: Int
        get() = vector.size
        set(value: Int) = resize(value)
    // vector resizing operations
    fun resize(size: Int) = when {
        size == 0 -> vector.clear()
        size > vector.size -> while(size > vector.size) vector.add(null)
        else -> while(size < vector.size) vector.removeAt(vector.lastIndex)
    }
    fun addDimension(default: Vector) = vector.add(default)
    fun removeDimension(dimension: Int) = vector.removeAt(dimension)
    // vector indexing
    operator fun get(index: Int) = vector.get(index)
//    protected fun getOrZero(index: Int): Number = if (get(index) == null) 0 else get(index)!!
    operator fun set(index: Int, value: Vector?) {
        vector[index] = value
    }
    fun clone() = VectorSpace().also { it.vector.addAll(vector) }
    override fun toString(): String {
        return "dimensions: $dimensions\n" +
                "size: ${vector.size}\n" +
                "vector: $vector\n"
    }
    fun iterator(): Iterator<Vector?> = vector.iterator()
    fun forEach(action: (Item: Vector?) -> Unit) = vector.forEach {
        action(it)
    }
    fun forEachIndex(action: (index: Int) -> Unit) = vector.forEachIndexed { index, type ->
        action(index)
    }
}

open class VectorSpaceBase: VectorSpaceCore {
    // constructors
    constructor() : super()
    constructor(dimension: Int) : super(dimension)

    // base operations
    fun operation(vector: VectorSpace, action: (result: Vector, lhs: Number, rhs: Number) -> Unit): VectorSpace {
        val v1 = VectorSpace()
        val vA = clone()
        val vB = vector.clone()
        if (dimensions != vector.dimensions) {
            vA.resize(if (dimensions > vector.dimensions) dimensions else vector.dimensions)
            vB.resize(if (dimensions > vector.dimensions) dimensions else vector.dimensions)
        }
        vA.forEachIndex {
            v1.addDimension(vA[it]!!.elementWiseOperation(vB[it]!!, action))
        }
        return v1
    }
    fun sum(): Number {
        var sum: Number = 0.toByte()
        forEachIndex {
            sum = addition(sum, get(it)?.sum() ?: 0)
        }
        return sum
    }
}

/**
 * **Vector space**
 *
 * A collection of vectors, where vectors are objects that may be added together and
 * multiplied by scalars
 *
 * Euclidean vectors are an example of a vector space, typically used to represent
 * displacements, as well as physical quantities such as force or momentum
 *
 *  **Dimensions of a vector space**
 *
 * The number of coordinates required to specify any point within the space
 *
 * [19:43] <BigBrian> macroprep: a basis B of V is a set of linearly independent vectors of V such that *every* vector of V is a linear combination of vectors in B.
 *
 * [19:43] <BigBrian> notice that I said "a basis" and not "the basis"
 *
 * To qualify as a vector space,
 * the set ***`V`*** and the operations of addition and multiplication
 * must adhere to a number of requirements called axioms.

 * In the list below, let ***`u`***, ***`v`*** and ***`w`*** be arbitrary vectors in ***`V`***,
 * and ***`a`*** and ***`b`*** scalars in ***`F`***.
 *
 * **Axiom**
 *
 * : ***`Meaning`***
 *
 * **Associativity of addition**
 *
 * : ***`u + (v + w) = (u + v) + w`***
 *
 * **Commutativity of addition**
 *
 * : ***`u + v = v + u`***
 *
 * **Identity element of addition**
 *
 * : ***`There exists an element 0 ∈ V, called the zero vector, such that v + 0 = v for all v ∈ V.`***
 *
 * **Inverse elements of addition**
 *
 * : ***`For every v ∈ V, there exists an element −v ∈ V, called the additive inverse of v, such that v + (−v) = 0.`***
 *
 * **Compatibility of scalar multiplication with field multiplication**
 *
 * *NOTE: This axiom and the next refer to two different operations:*
 *
 * *scalar multiplication: ***`bv`****
 *
 * *field multiplication: ***`ab`***.*
 *
 * *They do not assert the associativity of either operation.*
 * *More formally, scalar multiplication is a monoid action of the multiplicative monoid of the field ***`F`****
 * *on the vector space ***`V`***.*
 *
 * : ***`a(bv) = (ab)v`***
 *
 *
 * **Identity element of scalar multiplication**
 *
 * : ***`1v = v, where 1 denotes the multiplicative identity in F.`***
 *
 * **Distributivity of scalar multiplication with respect to vector addition**
 *
 * : ***`a(u + v) = au + av`***
 *
 * **Distributivity of scalar multiplication with respect to field addition**
 *
 * : ***`(a + b)v = av + bv`***
 */
open class VectorSpace: VectorSpaceBase {
    constructor() : super()
    constructor(dimension: Int) : super(dimension)

    /**
     * performs vector addition
     */
    operator fun plus(vector: VectorSpace): VectorSpace = operation(vector) { result, lhs, rhs ->
        result.addDimension(addition(lhs, rhs))
    }
    /**
     * takes an **input vector**, then multiplies **this vector** and the **input vector**
     * and then returns the **sum** of the **resulting vector**
     *
     * example: ***`(1, 2) ⊗ (1, 2) -> (1*1, 2*2) -> (1, 4) -> 1 + 4 -> 5`***
     */
    fun scalarProduct(vector: VectorSpace) = operation(vector) { result, lhs, rhs ->
        result.addDimension(multiplication(lhs, rhs))
    }.sum()

    /**
     * see [scalarProduct]
     */
    fun dotProduct(vector: VectorSpace) = scalarProduct(vector)

    /**
     * see [scalarProduct]
     */
    infix fun `⊗`(vector: VectorSpace) = scalarProduct(vector)
}

fun vec() {
    val v1 = Vector(3)
    v1[0] = 1.toByte()
    v1[1] = 2
    v1[2] = 3
    val v2 = Vector(3)
    v2[0] = 1
    v2[1] = 2
    v2[2] = 3
    println("v1 = $v1")
    println("v2 = $v2")
    println("vector addition between v1 and v2 = ${v1 + v2}")
    println("dot product between v1 and v2 = ${v1.dotProduct(v2)}")
    println("v1 scaled by 5 = ${v1.scale(5)}")
    println("linear combination: 5v1 + 7v2 = ${v1.scale(5) + v2.scale(7)}")
    println(v1)
    println(v2)
    val v3 = VectorSpace(2)
    v3[0] = v1
    v3[1] = v2
    println(v1)
    println(v2)
    println(v3)
    println("v3 + v3 = ${v3 + v3}")
    println("v3 dot v3 = ${v3 `⊗` v3}")
}

fun main() {
    vec()
}