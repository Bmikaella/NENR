package hr.fer.zemris

import hr.fer.zemris.data.Data

/**
 * Created by bmihaela.
 */
class Util {

    companion object {
        @JvmStatic
        fun takeNNextElements(data: MutableList<Data>, size: Int, currentIndex: Int): Pair<MutableList<Data>, Int> {
            Preconditions.requireTo(size <= data.size, "Can only du subgroup od data, can't take more.")
            Preconditions.requireTo(currentIndex >= 0 && currentIndex < data.size, "")
            if (size == data.size && currentIndex == 0) {
                return Pair(data, 0)
            }
            val subData = mutableListOf<Data>()
            for (i in 0 until size) {
                subData.add(data[(currentIndex + i) % data.size])
            }
            return Pair(subData, (currentIndex + size) % data.size)
        }



    }

}