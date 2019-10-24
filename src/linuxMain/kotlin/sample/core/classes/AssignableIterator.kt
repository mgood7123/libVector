package sample.core.classes

/**
 * An iterator over a collection or another entity that can be represented as a sequence of elements.
 * Allows to sequentially access (and/or modify) the elements.
 */
interface AssignableIterator<T>: Collection<T> {
    /**
     * Returns the next element in the iteration.
     */
    public operator fun next(): T?

    /**
     * Returns `true` if the iteration has more elements.
     */
    public operator fun hasNext(): Boolean
    /**
     * sets the current element to `value`
     */
    public fun set(value: T)
}

interface AssignableIterable<E>: Iterable<E> {
    fun assignableIterator(): AssignableIteratorImp<E>
}

class AssignableIteratorImp<T> : AssignableIterator<T> {
    constructor(it: Collection<T>, setter: (index: Int, value: T) -> Unit) {
        this.it = it
        this.i = this.it.iterator()
        this.setter = setter
    }

    override fun contains(element: T): Boolean = it.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = it.containsAll(elements)

    override fun isEmpty(): Boolean = it.isEmpty()

    override fun iterator(): Iterator<T> = it.iterator()

    override val size: Int
        get() = it.size

    private val it: Collection<T>
    private val i: Iterator<T>
    private var element: T? = null
    private val setter: (index: Int, value: T) -> Unit
    override fun hasNext(): Boolean = i.hasNext()
    override fun next(): T? {
        element = i.next()
        return element
    }
    override fun set(value: T) = setter(it.indexOf(this.element), value)
}

/**
 * assigns the return value of [action] to each element.
 */
inline fun <T>AssignableIterable<T>.forEachAssign(action: () -> T) {
    val i = this.assignableIterator()
    while(i.hasNext()) {
        i.next()
        i.set(action())
    }
}

/**
 * Performs the given [action] on each element, providing sequential index with the element.
 * @param [action] function that takes the index of an element and the element itself
 * and performs the desired action on the element.
 */
inline fun <T> Iterable<T>.forEachIndex(action: (index: Int) -> Unit) = forEachIndexed { index, type ->
    action(index)
}

val <T> Iterable<T>.firstIndex
    get() = indexOf(first())

val <T> Iterable<T>.lastIndex
    get() = indexOf(last())

/**
 * Performs the given [action] on each element, providing sequential index with the element.
 * @param [action] function that takes the index of an element and the element itself
 * and performs the desired action on the element.
 */
inline fun <T> Iterable<T>.forEachExceptLast(action: (T) -> Unit) = forEachIndexed { index, type ->
    if (index != lastIndex) action(type)
}

/**
 * Performs the given [action] on each element, providing sequential index with the element.
 * @param [action] function that takes the index of an element and the element itself
 * and performs the desired action on the element.
 */
inline fun <T> Iterable<T>.forEachIndexedExceptLast(action: (index: Int, T) -> Unit) = forEachIndexed { index, type ->
    if (index != lastIndex) action(index, type)
}

/**
 * Performs the given [action] on each element, providing sequential index with the element.
 * @param [action] function that takes the index of an element and the element itself
 * and performs the desired action on the element.
 */
inline fun <T> Iterable<T>.forEachIndexExceptLast(action: (index: Int) -> Unit) = forEachIndexed { index, type ->
    if (index != lastIndex) action(index)
}

/**
 * assigns the return value of [action] to each element.
 */
inline fun <T>AssignableIterable<T>.forEachAssignExceptLast(action: () -> T) {
    val i = this.assignableIterator()
    if (i.isNotEmpty()) {
        var index = i.firstIndex
        while (i.hasNext()) {
            i.next()
            index++
            if (index != i.lastIndex) i.set(action())
        }
    }
}
