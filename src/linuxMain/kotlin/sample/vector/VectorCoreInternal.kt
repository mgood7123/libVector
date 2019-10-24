package sample.vector

import sample.core.Math
import sample.core.classes.AssignableIterable
import sample.core.classes.AssignableIteratorImp

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

open class VectorCoreInternal<T>: Math, AssignableIterable<T?> {
    // constructors
    constructor() : super() {
        this.vector = mutableListOf()
    }
    /**
     * constructs an **N**-dimensional vector
     */
    constructor(N: Int) : super() {
        this.vector = mutableListOf()
        this.resize(N)
    }
    // variables
    val vector: MutableList<T?>
    open var dimensions: Int
        get() = vector.size
        set(value: Int) = resize(value)
    // vector resizing operations
    open fun resize(size: Int) = when {
        size == 0 -> vector.clear()
        size > vector.size -> while(size > vector.size) vector.add(null)
        else -> while(size < vector.size) vector.removeAt(vector.lastIndex)
    }
    open fun addDimension(default: T) = vector.add(default)
    open fun removeDimension(dimension: Int) = vector.removeAt(dimension)
    open fun addAll(vectorCore: VectorCoreInternal<T>) = vector.addAll(vectorCore.vector)
    // vector indexing
    open operator fun get(index: Int) = vector.get(index)
    open operator fun set(index: Int, value: T) {
        vector[index] = value
    }
    open fun clone() = VectorCoreInternal<T>().also {
        it.addAll(this)
    }
    override fun toString(): String = vector.toString()
    open fun info(): String {
        return "dimensions: $dimensions\n" +
                "size: ${vector.size}\n" +
                "vector: $vector\n"
    }
    override fun iterator(): Iterator<T?> = vector.iterator()
    override fun assignableIterator() = AssignableIteratorImp(vector) { index, value ->
        vector.set(index, value)
    }

    /**
     *
     */
    open var AlgorithmHook: Any? = null

    open fun getOrZero(index: Int): T = if (get(index) == null) 0 as T else get(index)!!
}