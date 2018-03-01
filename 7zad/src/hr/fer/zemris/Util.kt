package hr.fer.zemris

import hr.fer.zemris.data.NNData
import java.io.File
import java.util.*

/**
 * Created by bmihaela.
 */
class Util {

    companion object {
        //Parses train data and shuffles them
        @JvmStatic
        fun parseNNData(path: String): MutableList<NNData> {
            val data = File(path).readLines()
            val nnData = mutableListOf<NNData>()
            if (data.isEmpty()) {
                return nnData
            }
            for (unit in data) {
                val elements = unit.split("\\s+".toRegex())

                val xdata = elements.subList(0, 2).map(String::toDouble).toDoubleArray()
                val ydata = elements.subList(2, 5).map(String::toDouble).toDoubleArray()

                nnData.add(NNData(xdata, ydata))
            }

            val rg: Random = Random()
            for (i in 0..nnData.size - 1) {
                val randomPosition = rg.nextInt(nnData.size)
                val tmp: NNData = nnData[i]
                nnData[i] = nnData[randomPosition]
                nnData[randomPosition] = tmp
            }

            return nnData
        }

    }

}