package hr.fer.zemris

import hr.fer.zemris.data.Data
import hr.fer.zemris.data.Dot
import java.awt.Point
import java.io.File
import java.lang.Math.abs
import java.util.*

/**
 * Created by bmihaela.
 */
class Util {

    companion object {
        @JvmStatic
        fun takeNNextElements(data: MutableList<Data>, size: Int, currentIndex: Int): Pair<MutableList<Data>, Int> {
            Preconditions.requireTo(size <= data.size, "Subgroup can't be larger then the group")
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

        @JvmStatic
        fun parseNNData(path: String): MutableList<Data> {
            val data = File(path).readLines()
            val nnData = mutableListOf<Data>()
            if (data.isEmpty()) {
                return nnData
            }
            for (unit in data) {
                val xYelements = unit.split("|")

                val xdata1 = xYelements[0].split("\\s+".toRegex()).dropLast(1)
                val xdata = xdata1.map(String::toDouble).toDoubleArray()
                val ydata1 = xYelements[1].split("\\s+".toRegex()).dropLast(1)
                val ydata = ydata1.map(String::toDouble).toDoubleArray()

                nnData.add(Data(xdata, ydata))
            }

            val rg : Random = Random()
            for (i in 0..nnData.size - 1) {
                val randomPosition = rg.nextInt(nnData.size)
                val tmp : Data = nnData[i]
                nnData[i] = nnData[randomPosition]
                nnData[randomPosition] = tmp
            }

            print(nnData.size)
            return nnData
        }

        @JvmStatic
        fun getDataForNN(scaledPoints: MutableList<Dot>, numberOfRepresentativeDots: Int): DoubleArray {
            var D = 0.0
            for (i in 0 until scaledPoints.size - 1) {
                val point1 = scaledPoints[i]
                val point2 = scaledPoints[i + 1]
                D += Dot.euclideanDistance(point1, point2)
            }

            val k = D / (numberOfRepresentativeDots - 1)
            val representativeDots = mutableListOf<Dot>()
            var distance = 0.0
            for (i in 0 until scaledPoints.size - 1) {
                val point1 = scaledPoints[i]
                val point2 = scaledPoints[i + 1]
                distance += Dot.euclideanDistance(point1, point2)
                if (representativeDots.size >= numberOfRepresentativeDots) {
                    break
                }
                if (distance > k) {
                    distance -= k
                    representativeDots.add(point1)
                }
            }

            val lastNToAdd = numberOfRepresentativeDots - representativeDots.size
           for(index in scaledPoints.size-lastNToAdd until scaledPoints.size){
                representativeDots.add(scaledPoints[index])
            }

            val inputData = DoubleArray(representativeDots.size * 2)
            var index = 0
            for (dot in representativeDots) {
                inputData[index] = dot.x
                inputData[index + 1] = dot.y
                index += 2
            }
            println(inputData.size)
            return inputData
        }

        @JvmStatic
        fun preprocess(data: MutableList<Point>): MutableList<Dot> {
            var Tcx = 0.0
            var Tcy = 0.0
            for (unit in data) {
                Tcx += unit.x
                Tcy += unit.y
            }
            Tcx /= data.size
            Tcy /= data.size

            //x,y
            val locationIndependentPoints = mutableListOf<Dot>()
            var mx = 0.0
            var my = 0.0
            for (unit in data) {
                val newPoint = Dot(unit.x - Tcx, unit.y - Tcy)
                if (abs(newPoint.x) > abs(mx)) {
                    mx = newPoint.x
                }
                if (abs(newPoint.y) > abs(my)) {
                    my = newPoint.y
                }
                locationIndependentPoints.add(newPoint)
            }

            val m: Double
            if (abs(mx) > abs(my)) {
                m = mx
            } else {
                m = my
            }


            val scaledPoints = mutableListOf<Dot>()
            for (unit in locationIndependentPoints) {
                scaledPoints.add(Dot(unit.x / m, unit.y / m))
            }

            return scaledPoints
        }


    }

}