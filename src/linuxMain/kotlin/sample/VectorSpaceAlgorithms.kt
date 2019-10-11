import sample.VectorCoreInternal
import sample.VectorSpaceCore
import sample.toVectorCore

open class VectorSpaceAlgorithms<T>(val parent: VectorSpaceCore) {
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
    open fun elementWiseOperation(vectorSpaceCore: VectorSpaceCore, action: (result: VectorCoreInternal<Number>, lhs: Number, rhs: Number) -> Unit): VectorSpaceCore {
        val v1 = VectorSpaceCore()
        v1.AlgorithmHook = vectorSpaceCore.AlgorithmHook
        val vA = parent.clone()
        val vB = vectorSpaceCore.clone()
        if (parent.dimensions != vectorSpaceCore.dimensions) {
            vA.resize(if (parent.dimensions > vectorSpaceCore.dimensions) parent.dimensions else vectorSpaceCore.dimensions)
            vB.resize(if (parent.dimensions > vectorSpaceCore.dimensions) parent.dimensions else vectorSpaceCore.dimensions)
        }
        vA.forEachIndex {
            val a = vB[it]!!
            val b = action
            val c = vA[it]!!.Algorithm.elementWiseOperation(a, b)
            v1.addDimension(c.toVectorCore())
        }
        return v1
    }

    /**
     * perform **addition** on each element of two vectors respectively as a pair
     * @see elementWiseOperation
     * @see elementWiseSubtraction
     * @see elementWiseMultiplication
     * @see elementWiseDivision
     */
    open fun elementWiseAddition(vectorSpaceCore: VectorSpaceCore) = elementWiseOperation(vectorSpaceCore) { result, lhs, rhs ->
        result.addDimension(parent.addition(lhs, rhs))
    }
    /**
     * perform **subtraction** on each element of two vectors respectively as a pair
     * @see elementWiseOperation
     * @see elementWiseAddition
     * @see elementWiseMultiplication
     * @see elementWiseDivision
     */
    open fun elementWiseSubtraction(vectorSpaceCore: VectorSpaceCore) = elementWiseOperation(vectorSpaceCore) { result, lhs, rhs ->
        result.addDimension(parent.subtraction(lhs, rhs))
    }

    /**
     * perform **multiplication** on each element of two vectors respectively as a pair
     * @see elementWiseOperation
     * @see elementWiseAddition
     * @see elementWiseSubtraction
     * @see elementWiseDivision
     */
    open fun elementWiseMultiplication(vectorSpaceCore: VectorSpaceCore) = elementWiseOperation(vectorSpaceCore) { result, lhs, rhs ->
        result.addDimension(parent.multiplication(lhs, rhs))
    }

    /**
     * perform **division** on each element of two vectors respectively as a pair
     * @see elementWiseOperation
     * @see elementWiseAddition
     * @see elementWiseSubtraction
     * @see elementWiseMultiplication
     */
    open fun elementWiseDivision(vectorSpaceCore: VectorSpaceCore) = elementWiseOperation(vectorSpaceCore) { result, lhs, rhs ->
        result.addDimension(parent.division(lhs, rhs))
    }

    open fun sum(): Number {
        var sum: Number = 0.toByte()
        parent.forEachIndex {
            sum = parent.addition(sum, parent.get(it)?.Algorithm?.sum() ?: 0)
        }
        return sum
    }

    /**
     * performs [element-wise multiplication][elementWiseMultiplication] on **this vector** and the **input vector**
     */
    open fun hadamardProduct(vectorSpaceCore: VectorSpaceCore) = elementWiseMultiplication(vectorSpaceCore)

    /**
     * scales a vector by a factor of **number**
     */
    open fun scale(number: Int) = elementWiseOperation(parent) { result, lhs, rhs ->
        result.addDimension(number)
    }.Algorithm.hadamardProduct(parent)

    /**
     * performs vector addition
     */
    open fun vectorAddition(vectorSpaceCore: VectorSpaceCore) = elementWiseAddition(vectorSpaceCore)

    /**
     * scales a vector by a factor of **number**
     */
    open fun scalarMultiplication(number: Int) = scale(number)

    /**
     * performs [vector addition][vectorAddition]
     */
    open operator fun plus(vectorSpaceCore: VectorSpaceCore) = vectorAddition(vectorSpaceCore)
    /**
     * performs [scalar multiplication][scalarMultiplication]
     */
    open operator fun times(number: Int) = scalarMultiplication(number)

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
    open fun linearCombination(number: Int) = scale(number)


    /**
     * returns the **sum** of the [hadamardProduct]
     *
     * example: ***`[1, 2] · [1, 2] -> [1*1, 2*2] -> [1, 4] -> 1 + 4 -> 5`***
     */
    open fun dotProduct(vectorSpaceCore: VectorSpaceCore) = hadamardProduct(vectorSpaceCore).Algorithm.sum()

    // synonyms for dotProduct

    /**
     * see [dotProduct]
     */
    open fun scalarProduct(vectorSpaceCore: VectorSpaceCore) = dotProduct(vectorSpaceCore)

    /**
     * see [dotProduct]
     */
    open infix fun `·`(vectorSpaceCore: VectorSpaceCore) = scalarProduct(vectorSpaceCore)

}
