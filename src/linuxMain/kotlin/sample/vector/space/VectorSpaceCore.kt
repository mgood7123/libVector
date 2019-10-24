package sample.vector.space

import sample.vector.VectorCore
import sample.vector.VectorCoreInternal

/**
 * **Vector space**
 *
 * A collection of vectors, where vectors are objects that may be added together and
 * multiplied by scalars
 *
 * Euclidean vectors are an example of a vector space, typically used to represent
 * displacements, as well as physical quantities such as force or momentum
 *
 * **Dimensions of a vector space**
 *
 *
 *
 * **Generalization**
 *
 * A vector space is an N-Dimensional object,
 *
 * **Infinite vs Finite**
 *
 * if a vector space's dimensions are finite,
 * and it can only hold a single value then that vector space is to be considered to be a **finite** vector space,
 * otherwise, it is considered to be an **infinite** vector space, in the case of a **finite** number of
 * dimensions, since it is capable of holding **more than one** value, **0 to 1** itself represents an
 * **infinite series** in that `1, 1/2, 1/4, 3/4, 1/8, 3/8, 5/8, 7/8` and so on to **infinity**
 *
 * a vector space R^n is considered infinite in respect that (0) of a R^1 vector space can hold any number
 * from negative infinite to infinity,
 *
 * In mathematics, the dimension of a vector space V is the cardinality (i.e. the number of vectors) of a basis
 * of V over its base field. It is sometimes called Hamel dimension (after Georg Hamel)
 * or algebraic dimension to distinguish it from other types of dimension.
 *
 * **To qualify as a vector space**
 *
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

open class VectorSpaceCore: VectorCoreInternal<VectorCore> {
    // constructors
    constructor() : super()
    /**
     * constructs an **N**-dimensional space vector
     */
    constructor(N: Int) : super(N)
    override fun clone() = VectorSpaceCore().also { it.addAll(this) }
    override var AlgorithmHook: Any? = VectorSpaceAlgorithms<Number>(this)
    val Algorithm = AlgorithmHook as VectorSpaceAlgorithms<Number>
    operator fun plus(vectorSpaceCore: VectorSpaceCore) = Algorithm + vectorSpaceCore
//    val basis = vector.toSet().

    // a subset B of A could be defined as all of A, a subset must have no duplicates

    /*
    if the number of elements is strictly less than the number of vectors then it is linearly dependant

     */
}