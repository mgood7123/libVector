package sample.matrix

import sample.core.Math
import sample.core.classes.forEachIndex
import sample.core.classes.forEachIndexExceptLast
import sample.core.classes.forEachIndexedExceptLast

open class MatrixAlgorithms(var parent: MatrixCore) {
    /**
     * for matrix
     * ```
     * 5, 1, 6
     * 2, 5, 12
     * 3, 3, 18
     * argmax(row1) == 1 // 5,2,3
     * argmax(row2) == 2 // 1,5,3
     * argmax(row3) == 3 // 6,12,18
     * ```
     */
    fun argMax(row: Int): Number {
        if (parent.debug) println("row: $row")
        return when {
            parent.transpositionIsColumnsFirst() -> {
                val max = Math.max()
                val ca = mutableListOf<Int>()
                parent.column.forEachIndex {
                    ca.add(it)
                }
                val ra = mutableListOf<Int>()
                parent.column[0]!!.row.forEachIndex {
                    ra.add(it)
                }
                if (parent.debug) {
                    println("rows = $ra")
                    println("columns = $ca")
                }
                ca.forEachIndexExceptLast { index1 ->
                    val v = parent.column[index1]!!.row[row]!!
                    if (parent.debug) println("parent.column[$index1]!!.row[$row] = $v")
                    max.updateMax(v)
                }
                val v = parent.column[ca.lastIndex]!!.row[row]!!
                if (parent.debug) println("parent.column[${ca.lastIndex}]!!.row[$row] = $v")
                max.updateMax(v)
                max.max()
            }
            parent.transpositionIsRowsFirst() -> {
                val max = Math.max()
                parent.row[row]!!.column.forEachIndex {
                    val v = parent.row[row]!!.column[it]!!
                    if (parent.debug) println("parent.row[$row]!!.column[$it] = $v")
                    max.updateMax(v)
                }
                max.max()
            }
            else -> parent.invalidTranspose()
        }
    }
    fun gaussianElimination() {
        var r = 1
        var c = 1
        while (r <= parent.rows && c <= parent.columns) {
//            val iMax = argmax (i = h ... m, abs(A[i, k]))
//            val iMax = for(i_max = h, max_val = abs(A[h, k]), i = h + 1; i < m; ++i) { if (max_val < abs(A[i, k])) { max_val = abs(A[i, k]); i_max = i; } }
        }
    }
}