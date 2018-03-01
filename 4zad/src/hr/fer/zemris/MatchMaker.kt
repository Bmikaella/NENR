package hr.fer.zemris

import hr.fer.zemris.data.BinaryChromosome
import hr.fer.zemris.data.Chromosome
import hr.fer.zemris.data.Dot
import hr.fer.zemris.data.FloatingPointChromosome
import java.util.*

/**
 * Created by bmihaela.
 */
abstract class MatchMaker {

    abstract fun getChild(firstParent: Chromosome, secondParent: Chromosome): Chromosome

    companion object {
        @JvmStatic
        val SIMPLE_FLOAT_CROSS_OVER = object : MatchMaker() {
            override fun getChild(firstParent: Chromosome, secondParent: Chromosome): Chromosome {
                val child = Dot(firstParent.getSize())
                val random = Random()
                for (index in 0 until firstParent.getSize()) {
                    val firstsElement = firstParent.getElementAt(index)
                    val secondsElement = secondParent.getElementAt(index)
                    val lowerBound: Double
                    val upperBound: Double
                    if (firstsElement > secondsElement) {
                        upperBound = firstsElement
                        lowerBound = secondsElement
                    } else {
                        upperBound = secondsElement
                        lowerBound = firstsElement
                    }
                    child.setElementAt(index, random.nextDouble() * (upperBound - lowerBound) + lowerBound)
                }
                return FloatingPointChromosome(child.getData(), firstParent.getTemplate())
            }
        }

        @JvmStatic
        val SIMPLE_ONE_POINT_BINARY_CROSS_OVER = object : MatchMaker() {
            override fun getChild(firstParent: Chromosome, secondParent: Chromosome): Chromosome {
                val crossPoint = Random().nextInt(firstParent.getSize())
                val data = DoubleArray(firstParent.getSize())
                for (i in 0 until firstParent.getSize()) {
                    if (i >= crossPoint) {
                        data[i] = secondParent.getElementAt(i)
                    } else {
                        data[i] = firstParent.getElementAt(i)
                    }
                }
                return BinaryChromosome(data, firstParent.getTemplate())
            }
        }

        @JvmStatic
        val UNIFORM_BINARY_CROSS_OVER = object : MatchMaker() {
            override fun getChild(firstParent: Chromosome, secondParent: Chromosome): Chromosome {
                val dataSize = firstParent.getSize()
                val data = DoubleArray(dataSize)
                val random = Random()
                for (i in 0 until dataSize) {
                    if (firstParent.getElementAt(i) == secondParent.getElementAt(i)) {
                        data[i] = firstParent.getElementAt(i)
                    } else if (random.nextBoolean()) {
                        data[i] = 1.0
                    } else {
                        data[i] = 0.0
                    }
                }
                return BinaryChromosome(data, firstParent.getTemplate())
            }
        }
    }
}