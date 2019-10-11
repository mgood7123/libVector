import sample.VectorCore

open class VectorAlgorithms(var parent: VectorCore) {
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
    open fun elementWiseOperation(vectorCore: VectorCore, action: (result: VectorCore, lhs: Number, rhs: Number) -> Unit): VectorCore {
        println("parent = $parent")
        println("vectorCore = $vectorCore")
        val v1 = VectorCore()
        val vA = parent.clone()
        val vB = vectorCore.clone()
        if (parent.dimensions != vectorCore.dimensions) {
            vA.resize(if (parent.dimensions > vectorCore.dimensions) parent.dimensions else vectorCore.dimensions)
            vB.resize(if (parent.dimensions > vectorCore.dimensions) parent.dimensions else vectorCore.dimensions)
        }
        vA.forEachIndex {
            action(v1, vA.getOrZero(it), vB.getOrZero(it))
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
    open fun elementWiseAddition(vectorCore: VectorCore) = elementWiseOperation(vectorCore) { result, lhs, rhs ->
        result.addDimension(parent.addition(lhs, rhs))
    }

    /**
     * perform **subtraction** on each element of two vectors respectively as a pair
     * @see elementWiseOperation
     * @see elementWiseAddition
     * @see elementWiseMultiplication
     * @see elementWiseDivision
     */
    open fun elementWiseSubtraction(vectorCore: VectorCore) = elementWiseOperation(vectorCore) { result, lhs, rhs ->
        result.addDimension(parent.subtraction(lhs, rhs))
    }

    /**
     * perform **multiplication** on each element of two vectors respectively as a pair
     * @see elementWiseOperation
     * @see elementWiseAddition
     * @see elementWiseSubtraction
     * @see elementWiseDivision
     */
    open fun elementWiseMultiplication(vectorCore: VectorCore) = elementWiseOperation(vectorCore) { result, lhs, rhs ->
        result.addDimension(parent.multiplication(lhs, rhs))
    }

    /**
     * perform **division** on each element of two vectors respectively as a pair
     * @see elementWiseOperation
     * @see elementWiseAddition
     * @see elementWiseSubtraction
     * @see elementWiseMultiplication
     */
    open fun elementWiseDivision(vectorCore: VectorCore) = elementWiseOperation(vectorCore) { result, lhs, rhs ->
        result.addDimension(parent.division(lhs, rhs))
    }

    open fun sum(): Number {
        var sum: Number = 0.toByte()
        parent.forEachIndex {
            sum = parent.addition(sum, parent.getOrZero(it))
        }
        return sum
    }

    /**
     * performs [element-wise multiplication][elementWiseMultiplication] on **this vector** and the **input vector**
     */
    open fun hadamardProduct(vectorCore: VectorCore) = elementWiseMultiplication(vectorCore)

    /**
     * scales a vector by a factor of **number**
     */
    open fun scale(number: Int) = elementWiseOperation(parent) { result, lhs, rhs ->
        result.addDimension(number)
    }.Algorithm.hadamardProduct(parent)

    /**
     * performs vector addition
     */
    open fun vectorAddition(vectorCore: VectorCore) = elementWiseAddition(vectorCore)

    /**
     * scales a vector by a factor of **number**
     */
    open fun scalarMultiplication(number: Int) = scale(number)

    /**
     * performs [vector addition][vectorAddition]
     */
    open operator fun plus(vectorCore: VectorCore): VectorCore {
        println("parent $parent plus vectorCore $vectorCore")
        return vectorAddition(vectorCore)
    }
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
    open fun dotProduct(vectorCore: VectorCore) = hadamardProduct(vectorCore).Algorithm.sum()

    // synonyms for dotProduct

    /**
     * see [dotProduct]
     */
    open fun scalarProduct(vectorCore: VectorCore) = dotProduct(vectorCore)

    /**
     * see [dotProduct]
     */
    open infix fun `·`(vectorCore: VectorCore) = scalarProduct(vectorCore)

}
