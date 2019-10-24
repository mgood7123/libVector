package sample.matrix

import sample.core.Math
import sample.core.classes.*
import sample.vector.Vector
import kotlin.collections.lastIndex

open class MatrixCoreInternal: Math {
    // constructors
    constructor() : super() {
        this.row = MatrixSubCoreInternal()
        this.column = MatrixSubCoreInternal()
    }
    /**
     * constructs a **R**x**C** (**Row** by **Column**) sized matrix
     */
    constructor(row: Int, column: Int) : super() {
        this.row = MatrixSubCoreInternal()
        this.column = MatrixSubCoreInternal()
        this.resize(row, column)
        this.buildMatrix()
    }
    // variables
    var debug = false
    inner class row_ {
        val row = Vector()
    }
    val row: MatrixSubCoreInternal<column_>
    inner class column_ {
        val column = Vector()
    }
    val column: MatrixSubCoreInternal<row_>
    /**
     * in a **RowsFirst** configuration, a **2 by 4** matrix has **2 rows** and **4 columns**,
     * ```
     * [0] -> row 0, column 0, row[0]!!.column[0] = 0
     * [1] -> row 1, column 0, row[1]!!.column[0] = 1
     * [2] -> row 0, column 1, row[0]!!.column[1] = 2
     * [3] -> row 1, column 1, row[1]!!.column[1] = 3
     * [4] -> row 0, column 2, row[0]!!.column[2] = 4
     * [5] -> row 1, column 2, row[1]!!.column[2] = 5
     * [6] -> row 0, column 3, row[0]!!.column[3] = 6
     * [7] -> row 1, column 3, row[1]!!.column[3] = 7
     *
     * 0 2 4 6
     * 1 3 5 7
     * ```
     */
    private val transpositionRowsFirst = 1
    /**
     * in a **ColumnsFirst** configuration, a **2 by 4** matrix has **2 columns** and **4 rows**
     * ```
     * [0] -> row 0, column 0, column[0].row[0] = 0
     * [1] -> row 0, column 1, column[1].row[0] = 1
     * [2] -> row 1, column 0, column[0].row[1] = 2
     * [3] -> row 1, column 1, column[1].row[1] = 3
     * [4] -> row 2, column 0, column[0].row[2] = 4
     * [5] -> row 2, column 1, column[1].row[2] = 5
     * [6] -> row 3, column 0, column[0].row[3] = 6
     * [7] -> row 3, column 1, column[1].row[3] = 7
     *
     * 0 1
     * 2 3
     * 4 5
     * 6 7
     * ```
     */
    private val transpositionColumnsFirst = 2
    /**
     * specifies the transposing order of **index to element** relationship
     *
     * when assuming a 2 by 4 matrix:
     *
     * elements are indexed **0 to 7** (**8** elements total including ***0th*** element)
     *
     * in a **RowsFirst** configuration, a **2 by 4** matrix has **2 rows** and **4 columns**,
     * ```
     * [0] -> row 0, column 0, row[0]!!.column[0] = 0
     * [1] -> row 1, column 0, row[1]!!.column[0] = 1
     * [2] -> row 0, column 1, row[0]!!.column[1] = 2
     * [3] -> row 1, column 1, row[1]!!.column[1] = 3
     * [4] -> row 0, column 2, row[0]!!.column[2] = 4
     * [5] -> row 1, column 2, row[1]!!.column[2] = 5
     * [6] -> row 0, column 3, row[0]!!.column[3] = 6
     * [7] -> row 1, column 3, row[1]!!.column[3] = 7
     *
     * 0 2 4 6
     * 1 3 5 7
     * ```
     *
     * in a **ColumnsFirst** configuration, a **2 by 4** matrix has **2 columns** and **4 rows**
     * ```
     * [0] -> row 0, column 0, column[0].row[0] = 0
     * [1] -> row 0, column 1, column[1].row[0] = 1
     * [2] -> row 1, column 0, column[0].row[1] = 2
     * [3] -> row 1, column 1, column[1].row[1] = 3
     * [4] -> row 2, column 0, column[0].row[2] = 4
     * [5] -> row 2, column 1, column[1].row[2] = 5
     * [6] -> row 3, column 0, column[0].row[3] = 6
     * [7] -> row 3, column 1, column[1].row[3] = 7
     *
     * 0 1
     * 2 3
     * 4 5
     * 6 7
     * ```
     */
    private var transposition: Int = transpositionColumnsFirst
        get() = field
        set(value) {
            if (value != transpositionRowsFirst && value != transpositionColumnsFirst) invalidTranspose()
            val pt = dimensions
            val pts = transpositionGetAsString
            field = value
            val ct = dimensions
            val cts = transpositionGetAsString
            if (debug) println("transposition has changed from $pts ($pt) to $cts ($ct)")
        }
    open val dimensions
        get() = when (transposition) {
            transpositionColumnsFirst -> "${columnsReal}x$rowsReal"
            transpositionRowsFirst -> "${rowsReal}x$columnsReal"
            else -> invalidTranspose()
        }
    private val transpositionGetAsString
        get() = when (transposition) {
            transpositionRowsFirst -> "Rows First"
            else -> "Columns First"
        }
    fun invalidTranspose(): Nothing = throw IllegalArgumentException(
        "value must be either transpositionRowsFirst (value 1) or transpositionColumnsFirst (value 2)"
    )
    /**
     * in a **RowsFirst** configuration, a **2 by 4** matrix has **2 rows** and **4 columns**,
     * ```
     * [0] -> row 0, column 0, row[0]!!.column[0] = 0
     * [1] -> row 1, column 0, row[1]!!.column[0] = 1
     * [2] -> row 0, column 1, row[0]!!.column[1] = 2
     * [3] -> row 1, column 1, row[1]!!.column[1] = 3
     * [4] -> row 0, column 2, row[0]!!.column[2] = 4
     * [5] -> row 1, column 2, row[1]!!.column[2] = 5
     * [6] -> row 0, column 3, row[0]!!.column[3] = 6
     * [7] -> row 1, column 3, row[1]!!.column[3] = 7
     *
     * 0 2 4 6
     * 1 3 5 7
     * ```
     */
    fun transpositionSetRowsFirst() {
        transposition = transpositionRowsFirst
    }
    /**
     * in a **ColumnsFirst** configuration, a **2 by 4** matrix has **2 columns** and **4 rows**
     * ```
     * [0] -> row 0, column 0, column[0].row[0] = 0
     * [1] -> row 0, column 1, column[1].row[0] = 1
     * [2] -> row 1, column 0, column[0].row[1] = 2
     * [3] -> row 1, column 1, column[1].row[1] = 3
     * [4] -> row 2, column 0, column[0].row[2] = 4
     * [5] -> row 2, column 1, column[1].row[2] = 5
     * [6] -> row 3, column 0, column[0].row[3] = 6
     * [7] -> row 3, column 1, column[1].row[3] = 7
     *
     * 0 1
     * 2 3
     * 4 5
     * 6 7
     * ```
     */
    fun transpositionSetColumnsFirst() {
        transposition = transpositionColumnsFirst
    }
    /**
     * specifies the transposing order of **index to element** relationship
     *
     * when assuming a 2 by 4 matrix:
     *
     * elements are indexed **0 to 7** (**8** elements total including ***0th*** element)
     *
     * in a **RowsFirst** configuration, a **2 by 4** matrix has **2 rows** and **4 columns**,
     * ```
     * [0] -> row 0, column 0
     * [1] -> row 1, column 0
     * [2] -> row 0, column 1
     * [3] -> row 1, column 1
     * [4] -> row 0, column 2
     * [5] -> row 1, column 2
     * [6] -> row 0, column 3
     * [7] -> row 1, column 3
     *
     * 0 2 4 6
     * 1 3 5 7
     * ```
     *
     * in a **ColumnsFirst** configuration, a **2 by 4** matrix has **2 columns** and **4 rows**
     * ```
     * [0] -> row 0, column 0
     * [1] -> row 0, column 1
     * [2] -> row 1, column 0
     * [3] -> row 1, column 1
     * [4] -> row 2, column 0
     * [5] -> row 2, column 1
     * [6] -> row 3, column 0
     * [7] -> row 3, column 1
     *
     * 0 1
     * 2 3
     * 4 5
     * 6 7
     * ```
     */
    fun transpositionGet() = transposition
    /**
     * in a **RowsFirst** configuration, a **2 by 4** matrix has **2 rows** and **4 columns**,
     * ```
     * [0] -> row 0, column 0, row[0]!!.column[0] = 0
     * [1] -> row 1, column 0, row[1]!!.column[0] = 1
     * [2] -> row 0, column 1, row[0]!!.column[1] = 2
     * [3] -> row 1, column 1, row[1]!!.column[1] = 3
     * [4] -> row 0, column 2, row[0]!!.column[2] = 4
     * [5] -> row 1, column 2, row[1]!!.column[2] = 5
     * [6] -> row 0, column 3, row[0]!!.column[3] = 6
     * [7] -> row 1, column 3, row[1]!!.column[3] = 7
     *
     * 0 2 4 6
     * 1 3 5 7
     * ```
     */
    fun transpositionIsRowsFirst() = transposition == transpositionRowsFirst
    /**
     * in a **ColumnsFirst** configuration, a **2 by 4** matrix has **2 columns** and **4 rows**
     * ```
     * [0] -> row 0, column 0, column[0].row[0] = 0
     * [1] -> row 0, column 1, column[1].row[0] = 1
     * [2] -> row 1, column 0, column[0].row[1] = 2
     * [3] -> row 1, column 1, column[1].row[1] = 3
     * [4] -> row 2, column 0, column[0].row[2] = 4
     * [5] -> row 2, column 1, column[1].row[2] = 5
     * [6] -> row 3, column 0, column[0].row[3] = 6
     * [7] -> row 3, column 1, column[1].row[3] = 7
     *
     * 0 1
     * 2 3
     * 4 5
     * 6 7
     * ```
     */
    fun transpositionIsColumnsFirst() = transposition == transpositionColumnsFirst
    // matrix resizing operations
    open fun resize(rowSize: Int, columnSize: Int) {
        rowsReal = rowSize
        columnsReal = columnSize
    }
    var rowsReal = 0
    var columnsReal = 0
    fun transformRowCol(row: Int, col: Int): Pair<Int, Int> {
        var RT = 0
        var CT = 0
        when(transposition) {
            transpositionColumnsFirst -> {
                RT = col
                CT = row
            }
            transpositionRowsFirst -> {
                RT = row
                CT = col
            }
            else -> invalidTranspose()
        }
        return Pair(RT, CT)
    }
    val rows: Int
        get() = when(transposition) {
            transpositionColumnsFirst -> columnsReal
            transpositionRowsFirst -> rowsReal
            else -> invalidTranspose()
        }
    val columns: Int
        get() = when(transposition) {
            transpositionColumnsFirst -> rowsReal
            transpositionRowsFirst -> columnsReal
            else -> invalidTranspose()
        }
    open fun addRow() = rowsReal++
    open fun removeRow() = rowsReal--
    open fun addColumn() = columnsReal++
    open fun removeColumn() = columnsReal--
    open fun buildMatrix() {
        row.resizeAndAssign(rowsReal) { column_() }
        row.forEach {
            it!!.column.resize(columnsReal)
        }
        column.resizeAndAssign(rowsReal) { row_() }
        column.forEach {
            it!!.row.resize(columnsReal)
        }
    }
    open fun addAll(matrixCoreInternal: MatrixCoreInternal): Nothing = TODO()
    // matrix indexing
    open operator fun get(index: Int): Number? {
        var realRow = 0
        var realCol = 0
        var total = 0
        return when (transposition) {
            transpositionColumnsFirst -> {
                while(true) {
                    if (total == index) break
                    realCol++
                    if (realCol == columns) {
                        realCol = 0
                        realRow++
                    }
                    total++
                }
                val value = column[realCol]!!.row[realRow]
                if (debug) println("[$index] -> row $realRow, column $realCol, column[$realCol]!!.row[$realRow] = $value")
                return value
            }
            transpositionRowsFirst -> {
                while(true) {
                    if (total == index) break
                    realRow++
                    if (realRow == rows) {
                        realRow = 0
                        realCol++
                    }
                    total++
                }
                val value = row[realRow]!!.column[realCol]
                if (debug) println("[$index] -> row $realRow, column $realCol, row[$realRow]!!.column[$realCol] = $value")
                value
            }
            else -> invalidTranspose()
        }
    }
    private fun setAtTranspose(index: Int, value: Number) {
        var realRow = 0
        var realCol = 0
        var total = 0
        when (transposition) {
            transpositionColumnsFirst -> {
                while(true) {
                    if (total == index) break
                    realCol++
                    if (realCol == columns) {
                        realCol = 0
                        realRow++
                    }
                    total++
                }
                column[realCol]!!.row[realRow] = value
            }
            transpositionRowsFirst -> {
                while(true) {
                    if (total == index) break
                    realRow++
                    if (realRow == rows) {
                        realRow = 0
                        realCol++
                    }
                    total++
                }
                row[realRow]!!.column[realCol] = value
            }
            else -> invalidTranspose()
        }
    }
    fun rowColumnToIndex(row: Int, column: Int): Int {
        var realRow = 0
        var realCol = 0
        var total = 0
        when (transposition) {
            transpositionColumnsFirst -> {
                while(true) {
                    if (debug) println("realRow=$realRow,row=$row,realCol=$realCol,column=$column")
                    if (realRow == row && realCol == column) break
                    realCol++
                    total++
                    if (realCol == columns) {
                        if (realRow == row && realCol == column) break
                        realCol = 0
                        realRow++
                        total++
                    }
                }
            }
            transpositionRowsFirst -> {
                while(true) {
                    if (debug) println("realRow=$realRow,row=$row,realCol=$realCol,column=$column")
                    if (realRow == column && realCol == row) break
                    realRow++
                    total++
                    if (realRow == rows) {
                        if (realRow == column && realCol == row) break
                        realRow = 0
                        realCol++
                        total++
                    }
                }
            }
            else -> invalidTranspose()
        }
        return total
    }
    private fun setAtTransposeAll(index: Int, value: Number) {
        val t = transposition
        if (debug) println("setting index $index for transposition Columns First to value $value")
        transpositionSetColumnsFirst()
        setAtTranspose(index, value)
        if (debug) println("setting index $index for transposition Rows First to value $value")
        transpositionSetRowsFirst()
        setAtTranspose(index, value)
        if (debug) println("restoring transposition")
        transposition = t
    }
    open operator fun set(index: Int, value: Number) = setAtTransposeAll(index, value)
    open fun print() {
        if (debug) println("dimension: ${dimensions}")
        val debugSaved = debug
        debug = true
        for (i in 0.until(columnsReal*rowsReal)) {
            get(i)
        }
        debug = debugSaved
    }
    open fun clone(): Nothing = TODO()
    override fun toString(): Nothing = TODO()
    open fun info(): Nothing = TODO()
    open fun iterator(): Nothing = TODO()

    open var AlgorithmHook: Any? = null

    open fun getOrZero(index: Int): Nothing = TODO()

    open fun visualizeToString() = when(transposition) {
        transpositionColumnsFirst -> {
            var x = ""
            val ca = mutableListOf<Int>()
            column.forEachIndex {
                ca.add(it)
            }
            val ra = mutableListOf<Int>()
            column[0]!!.row.forEachIndex {
                ra.add(it)
            }
            if (debug) {
                println("rows = $ra")
                println("columns = $ca")
            }
            ra.forEachIndex { index0 ->
                ca.forEachIndexExceptLast { index1 ->
                    val number = column[index1]!!.row[index0]
                    x += "$number, "
                }
                x += "${column[ca.lastIndex]!!.row[index0]}\n"
            }
            x
        }
        transpositionRowsFirst -> {
            var x = ""
            val ra = mutableListOf<Int>()
            row.forEachIndex {
                ra.add(it)
            }
            val ca = mutableListOf<Int>()
            row[0]!!.column.forEachIndex {
                ca.add(it)
            }
            if (debug) {
                println("rows = $ra")
                println("columns = $ca")
            }
            ra.forEachIndex { index0 ->
                ca.forEachIndexExceptLast { index1 ->
                    val number = row[index0]!!.column[index1]
                    x += "$number, "
                }
                x += "${row[index0]!!.column[ca.lastIndex]}\n"
            }
            x
        }
        else -> invalidTranspose()
    }
    open fun visualize() = print(visualizeToString())
}