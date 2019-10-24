package sample.vector.space

import sample.matrix.Matrix

typealias VectorSpace = VectorSpaceCore

fun VectorSpaceCore.toMatrix(): Matrix {
    // make sure all vectors are of same size
    if (any { it == null } ) throw NullPointerException("all vectors must not be null")
    if (any { it!!.dimensions != vector[0]!!.dimensions } )
        throw ArrayIndexOutOfBoundsException("all vectors must be of the same size")
    val m = Matrix(vector[0]!!.dimensions, vector.size)
    m.transpositionSetRowsFirst()
    var index = 0
    forEach { it!!.forEach { m[index++] = it!! } }
    return m
}