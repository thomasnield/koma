@file:koma.internal.JvmName("NDArray")
@file:koma.internal.JvmMultifileClass

/**
 * THIS FILE IS AUTOGENERATED, DO NOT MODIFY. EDIT THE FILES IN templates/
 * AND RUN ./gradlew :codegen INSTEAD!
 */

package koma.extensions

import koma.internal.default.generated.ndarray.DefaultGenericNDArray
import koma.internal.default.utils.checkIndices
import koma.internal.default.utils.linearToNIdx
import koma.matrix.doubleFactory
import koma.ndarray.NDArray
import koma.pow
import koma.matrix.Matrix

$toMatrix

@koma.internal.JvmName("fill${dtypeName}")
${inline}fun ${genDec} NDArray<${dtype}>.fill(f: (idx: IntArray) -> ${dtype}): NDArray<${dtype}> {
    this.forEachIndexedN { idx, ele ->
        this.set(indices=*idx, value = f(idx))
    }
    return this
}


/**
 * Takes each element in a NDArray, passes them through f, and puts the output of f into an
 * output NDArray.
 *
 * @param f A function that takes in an element and returns an element
 *
 * @return the new NDArray after each element is mapped through f
 */
@koma.internal.JvmName("map${dtypeName}")
${inline}fun ${genDec} NDArray<${dtype}>.map(f: (${dtype}) -> ${dtype}): NDArray<${dtype}> {
    // TODO: Something better than copy here
    val out = this.copy()
    for ((idx, ele) in this.toIterable().withIndex())
        out.setLinear(idx, f(ele))
    return out
}
/**
 * Takes each element in a NDArray, passes them through f, and puts the output of f into an
 * output NDArray. Index given to f is a linear index, depending on the underlying storage
 * major dimension.
 *
 * @param f A function that takes in an element and returns an element. Function also takes
 *      in the linear index of the element's location.
 *
 * @return the new NDArray after each element is mapped through f
 */
@koma.internal.JvmName("mapIndexed${dtypeName}")
${inline}fun ${genDec} NDArray<${dtype}>.mapIndexed(f: (idx: Int, ele: ${dtype}) -> ${dtype}): NDArray<${dtype}> {
    // TODO: Something better than copy here
    val out = this.copy()
    for ((idx, ele) in this.toIterable().withIndex())
        out.setLinear(idx, f(idx, ele))
    return out
}
/**
 * Takes each element in a NDArray and passes them through f.
 *
 * @param f A function that takes in an element
 *
 */
@koma.internal.JvmName("forEach${dtypeName}")
${inline}fun ${genDec} NDArray<${dtype}>.forEach(f: (ele: ${dtype}) -> Unit) {
    for (ele in this.toIterable())
        f(ele)
}
/**
 * Takes each element in a NDArray and passes them through f. Index given to f is a linear
 * index, depending on the underlying storage major dimension.
 *
 * @param f A function that takes in an element. Function also takes
 *      in the linear index of the element's location.
 *
 */
@koma.internal.JvmName("forEachIndexed${dtypeName}")
${inline}fun $genDec NDArray<${dtype}>.forEachIndexed(f: (idx: Int, ele: ${dtype}) -> Unit) {
    for ((idx, ele) in this.toIterable().withIndex())
        f(idx, ele)
}

// TODO: for both of these, batch compute [linearToNIdx] instead of computing for every ele

/**
 * Takes each element in a NDArray, passes them through f, and puts the output of f into an
 * output NDArray. Index given to f is the full ND index of the element.
 *
 * @param f A function that takes in an element and returns an element. Function also takes
 *      in the ND index of the element's location.
 *
 * @return the new NDArray after each element is mapped through f
 */
@koma.internal.JvmName("mapIndexedN${dtypeName}")
${inline}fun $genDec NDArray<${dtype}>.mapIndexedN(f: (idx: IntArray, ele: ${dtype}) -> ${dtype}): NDArray<${dtype}>
        = this.mapIndexed { idx, ele -> f(linearToNIdx(idx), ele) }

/**
 * Takes each element in a NDArray and passes them through f. Index given to f is the full
 * ND index of the element.
 *
 * @param f A function that takes in an element. Function also takes
 *      in the ND index of the element's location.
 *
 */
@koma.internal.JvmName("forEachIndexedN${dtypeName}")
${inline}fun $genDec NDArray<${dtype}>.forEachIndexedN(f: (idx: IntArray, ele: ${dtype}) -> Unit)
        = this.forEachIndexed { idx, ele -> f(linearToNIdx(idx), ele) }


@koma.internal.JvmName("getRanges${dtypeName}")
operator fun $genDec NDArray<${dtype}>.get(vararg indices: IntRange): NDArray<${dtype}> {
    checkIndices(indices.map { it.last }.toIntArray())
    return DefaultGenericNDArray<${dtype}>(shape = *indices
            .map { it.last - it.first + 1 }
            .toIntArray()) { newIdxs ->
        val offsets = indices.map { it.first }
        val oldIdxs = newIdxs.zip(offsets).map { it.first + it.second }
        this.getGeneric(*oldIdxs.toIntArray())
    }
}

@koma.internal.JvmName("set${dtypeName}")
operator fun $genDec NDArray<${dtype}>.set(vararg indices: Int, value: NDArray<${dtype}>) {
    val shape = shape()
    val lastIndex = indices.mapIndexed { i, range -> range + value.shape()[i] }
    val outOfBounds = lastIndex.withIndex().any { it.value > shape()[it.index] }
    if (outOfBounds)
        throw IllegalArgumentException("NDArray with shape \${shape()} cannot be " +
                "set at \${indices.toList()} by a \${value.shape()} array " +
                "(out of bounds)")

    val offset = indices.map { it }.toIntArray()
    value.forEachIndexedN { idx, ele ->
        val newIdx = offset.zip(idx).map { it.first + it.second }.toIntArray()
        this.setGeneric(indices=*newIdx, value=ele)
    }
}


operator fun $genDec NDArray<${dtype}>.get(vararg indices: Int) = get${dtypeName}(*indices)
operator fun $genDec NDArray<${dtype}>.set(vararg indices: Int, value: ${dtype}) = set${dtypeName}(indices=*indices, value=value)

$operators
