package sample

import sample.matrix.Matrix
import sample.matrix.MatrixCoreInternal
import sample.vector.Vector
import sample.vector.space.VectorSpace
import sample.vector.space.toMatrix

fun vec() {
    val v1 = Vector(3)
    v1[0] = 5.toByte()
    v1[1] = 2
    v1[2] = 3
    val v2 = Vector(3)
    v2[0] = 8
    v2[1] = 9
    v2[2] = 3
    val v3 = Vector(3)
    v3[0] = 1
    v3[1] = 2
    v3[2] = 9
    println(
        "a vector is an array of scalars, which can be either Real Numbers, or Complex Numbers"
    )
    println("a vector CAN be considered as an 1 x n or n x 1 matrix")
    println("\nthis vector is a Real numbered vector")
    println("v1 = $v1")
    println("\nthis vector is a Real numbered vector")
    println("v2 = $v2")
    println("\nthis vector is a Real numbered vector")
    println("v3 = $v3")
    println("\nvector addition produces a new vector that is the result of element-wise addition of two SAME-SIZED vectors")
    println("vector addition between v1 and v2 = ${v1 + v2}")
    println("\nscalar multiplication is the resulting vector produced by scaling a vector by a factor of some number")
    println("v1 scaled by 5 = ${v1.Algorithm.scalarMultiplication(5)}")
    println("\nthe hadamardProduct produces a new vector that is the result of element-wise multiplication of two SAME-SIZED vectors")
    println("dot product is the sum of the hadamardProduct")
    println("dot product between v1 and v2 = ${v1.Algorithm.dotProduct(v2)}")
    println("\nlinear combination is the addition of scaled vectors")
    println("linear combination: 5v1 + 7v2 = ${v1.Algorithm.scale(5) + v2.Algorithm.scale(7)}")
    println("\nall operations produce a new vector and do not modify the original")
    println("v1 = $v1")
    println("v2 = $v2")
    println("\na vector space is an N-Dimensional object in which the number of dimensions is the minimum number of vectors needed to span the entire space")
    val v4 = VectorSpace()
    v4.addDimension(v1)
    v4.addDimension(v2)
    v4.addDimension(v3)
    println("v1 = $v1")
    println("v2 = $v2")
    println("v3 = $v3")
    println("v3 = $v4")
    println("vector addition between v4 and v4 = ${v4 + v4}")
    println("dot product between v4 and v4 = ${v4.Algorithm.dotProduct(v4)}")
    println("v4 scaled by 5 = ${v4.Algorithm.scale(5)}")
    println("linear combination: 5v4 + 7v4 = ${v4.Algorithm.scale(5) + v4.Algorithm.scale(7)}")
    println("v1 = $v1")
    println("v2 = $v2")
    println("v3 = $v3")
    println("v4 = $v4")
    val vm = v4.toMatrix()
    vm.debug = false
    vm.transpositionSetRowsFirst()
    vm.visualize()
    println("argMax of row 0 = ${vm.Algorithm.argMax(0)}")
    println("argMax of row 1 = ${vm.Algorithm.argMax(1)}")
    println("argMax of row 2 = ${vm.Algorithm.argMax(2)}")
    vm.transpositionSetColumnsFirst()
    vm.visualize()
    println("argMax of row 0 = ${vm.Algorithm.argMax(0)}")
    println("argMax of row 1 = ${vm.Algorithm.argMax(1)}")
    println("argMax of row 2 = ${vm.Algorithm.argMax(2)}")
}

fun main() {
    vec()
}

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
