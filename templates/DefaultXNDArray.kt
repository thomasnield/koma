/**
 * THIS FILE IS AUTOGENERATED, DO NOT MODIFY. EDIT THE FILES IN templates/
 * AND RUN ./gradlew :codegen INSTEAD!
 */

package ${namespace}.ndarray

import koma.ndarray.*
import koma.internal.KomaJsName
import koma.internal.default.utils.*


/**
 * An (unoptimized) implementation of [NDArray] in pure Kotlin, for portability between the
 * different platforms koma supports.
 *
 * @param shape A vararg specifying the size of each dimension, e.g. a 3D array with size 4x6x8 would pass in 4,6,8)
 * @param init A function that takes a location in the new array and returns its initial value.
 */
open class Default${dtypeName}NDArray${genDec}(@KomaJsName("shape_private") vararg protected val shape: Int,
                             init: ((IntArray)->${dtype})): NDArray<${dtype}> {

    /**
     * Underlying storage. Default backends uses a simple array.
     */
    private val storage: ${storage}

    init {
${initStorage}
    }

    override fun getGeneric(vararg indices: Int): ${dtype} {
        checkIndices(indices)
        return storage[nIdxToLinear(indices)]
    }
    override fun getGeneric(i: Int): ${dtype} = storage[i]
    override fun setGeneric(i: Int, value: ${dtype}) { storage[i] = value }

    override fun setGeneric(vararg indices: Int, value: ${dtype}) {
        checkIndices(indices)
        storage[nIdxToLinear(indices)] = value
    }
    // TODO: cache this
    override val size get() = storage.size
    override fun shape(): List<Int> = shape.toList()
    override fun copy(): NDArray<${dtype}> = Default${dtypeName}NDArray(*shape, init = { this.getGeneric(*it) })
    override fun getBaseArray(): Any = storage

    private val wrongType = "Double methods not implemented for generic NDArray"
${getDouble}
${setDouble}
${getByte}
${setByte}
${getInt}
${setInt}
${getFloat}
${setFloat}
${getLong}
${setLong}
${getShort}
${setShort}


}

