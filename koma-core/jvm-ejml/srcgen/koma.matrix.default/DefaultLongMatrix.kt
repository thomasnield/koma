package koma.matrix.default

import koma.*
import koma.extensions.*
import koma.matrix.*

class DefaultLongMatrix (val rows: Int, 
                          val cols: Int): Matrix<Long> {
    val storage = LongArray(rows*cols)

    
    override fun div(other: Long): Matrix<Long>
            = this.mapIndexed { _, _, ele -> ele/other}

    
    override fun div(other: Int): Matrix<Long>
            = this.mapIndexed { _, _, ele -> ele/other}


    override fun times(other: Matrix<Long>): Matrix<Long> {
        val out = getFactory().zeros(this.numRows(), other.numCols())
        out.forEachIndexed { row, col, _ ->
            for (cursor in 0 until this.numCols())
                out[row,col] += this[row, cursor]*other[cursor, col]
        }
        return out
    }
    
    override fun times(other: Long): Matrix<Long> 
            = this.map { it*other }

    override fun unaryMinus(): Matrix<Long> 
            = this.map { it*-1 }

    override fun minus(other: Long): Matrix<Long>
            = this.map { it - other }
    
    override fun minus(other: Matrix<Long>): Matrix<Long> 
            = this.mapIndexed { row, col, ele -> ele - other[row,col] }

    override fun plus(other: Long): Matrix<Long> 
            = this.map { it + other }

    override fun plus(other: Matrix<Long>): Matrix<Long> 
            = this.mapIndexed { row, col, ele -> ele + other[row,col] }

    override fun transpose(): Matrix<Long> 
            = getFactory()
            .zeros(numCols(),numRows())
            .fill { row, col -> this[col,row] }

    override fun elementTimes(other: Matrix<Long>): Matrix<Long> 
            = this.mapIndexed { row, col, ele -> ele*other[row,col] }

    
    override fun epow(other: Long): Matrix<Long> 
            = this.mapIndexed { _, _, ele -> pow(ele.toDouble(), other.toDouble()).toLong() }

    
    override fun epow(other: Int): Matrix<Long>
            = this.mapIndexed { _, _, ele -> pow(ele.toDouble(), other.toDouble()).toLong() }

    override fun numRows(): Int = this.rows
    override fun numCols(): Int = this.cols

    private fun setStorage(i: Int, v: Long) {
        storage[i] = v
    }
    private fun setStorage(i: Int, j: Int, v: Long) {
        checkBounds(i,j)
        storage[this.cols*i+j] = v
    }

    private fun getStorage(i: Int, j: Int): Long {
        checkBounds(i,j)
        return storage[this.cols*i+j]
    }

    private fun getStorage(i: Int): Long 
            = storage[i]
    
    override fun copy(): Matrix<Long> 
            = this.map { it }
    
    
    override fun getInt(i: Int, j: Int): Int = this.getStorage(i,j).toInt()
    override fun getDouble(i: Int, j: Int): Double = this.getStorage(i,j).toDouble()
    override fun getFloat(i: Int, j: Int): Float = this.getStorage(i,j).toFloat()
    override fun getGeneric(i: Int, j: Int): Long = this.getStorage(i,j)
    override fun getInt(i: Int): Int = this.getStorage(i).toInt()
    override fun getDouble(i: Int): Double = this.getStorage(i).toDouble()
    override fun getFloat(i: Int): Float = this.getStorage(i).toFloat()
    override fun getGeneric(i: Int): Long = this.getStorage(i)
    override fun setInt(i: Int, v: Int) { this.setStorage(i, v.toLong())}
    override fun setDouble(i: Int, v: Double) { this.setStorage(i, v.toLong())}
    override fun setFloat(i: Int, v: Float) { this.setStorage(i, v.toLong())}
    override fun setGeneric(i: Int, v: Long) { this.setStorage(i, v)}
    override fun setInt(i: Int, j: Int, v: Int) { this.setStorage(i, j, v.toLong())}
    override fun setDouble(i: Int, j: Int, v: Double) { this.setStorage(i, j, v.toLong())}
    override fun setFloat(i: Int, j: Int, v: Float) { this.setStorage(i, j, v.toLong())}
    override fun setGeneric(i: Int, j: Int, v: Long) { this.setStorage(i, j, v)}
    override fun getDoubleData(): DoubleArray = storage.map { it.toDouble() }.toDoubleArray()
    override fun getRow(row: Int): Matrix<Long> {
        checkBounds(row, 0)
        val out = getFactory().zeros(1,cols)
        for (i in 0 until cols)
            out[i] = this[row, i]
        return out
    }
    override fun getCol(col: Int): Matrix<Long> {
        checkBounds(0,col)
        val out = getFactory().zeros(rows,1)
        for (i in 0 until rows)
            out[i] = this[i, col]
        return out
    }

    override fun setCol(index: Int, col: Matrix<Long>) {
        checkBounds(0,index)
        for (i in 0 until rows)
            this[i, index] = col[i]
    }

    override fun setRow(index: Int, row: Matrix<Long>) {
        checkBounds(index, 0)
        for (i in 0 until cols)
            this[index, i] = row[i]
    }

    override fun chol(): Matrix<Long> {
        TODO("not implemented")
    }

    override fun LU(): Triple<Matrix<Long>, Matrix<Long>, Matrix<Long>> {
        TODO("not implemented")
    }

    override fun QR(): Pair<Matrix<Long>, Matrix<Long>> {
        TODO("not implemented")
    }

    override fun SVD(): Triple<Matrix<Long>, Matrix<Long>, Matrix<Long>> {
        TODO("not implemented")
    }

	override fun expm(): Matrix<Long> {
        TODO("not implemented")
    }

    override fun solve(other: Matrix<Long>): Matrix<Long> {
        TODO("not implemented")
    }

    override fun inv(): Matrix<Long> {
        TODO("not implemented")
    }

    override fun det(): Long {
        TODO("not implemented")
    }

    override fun pinv(): Matrix<Long> {
        TODO("not implemented")
    }

    override fun normF(): Long {
        TODO("not implemented")
    }

    override fun normIndP1(): Long {
        TODO("not implemented")
    }

    override fun elementSum(): Long 
            = this.toIterable().reduce { a, b -> a + b }

    override fun diag(): Matrix<Long> 
            = getFactory()
            .zeros(numRows(),1)
            .fill{ row, _ -> this[row,row] }

    override fun max(): Long = this[argMax()]
    override fun mean(): Long = elementSum()/(numRows()*numCols())
    override fun min(): Long = this[argMin()]

    override fun argMax(): Int {
        var highest= Long.MIN_VALUE
        var highestIdx = -1
        for (i in 0 until numRows()*numCols())
            if(this[i] > highest) {
                highest = this[i]
                highestIdx = i
            }
        return highestIdx
    }

    override fun argMin(): Int {
        var lowest = Long.MAX_VALUE
        var lowestIdx = -1
        for (i in 0 until numRows()*numCols())
            if(this[i] < lowest) {
                lowest = this[i]
                lowestIdx = i
            }
        return lowestIdx
    }

    override fun trace(): Long {
        TODO("not implemented")
    }

    override fun T(): Matrix<Long> = this.transpose()

    override fun getBaseMatrix(): Any 
            = storage
    override fun getFactory(): MatrixFactory<Matrix<Long>> 
            = DefaultLongMatrixFactory()
    
    private fun checkBounds(row: Int, col: Int) {
        if (row >= rows || col >= cols)
            throw IllegalArgumentException("row/col index out of bounds")
    }
}