package hr.fer.zemris

import hr.fer.zemris.genetic.chromosome.Chromosome
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
                val child = DoubleArray(firstParent.getSize())
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
                    child[index] = random.nextDouble() * (upperBound - lowerBound) + lowerBound
                }
                return Chromosome(child)
            }
        }

        @JvmStatic
        val ONE_POINT_CROSS_OVER = object : MatchMaker() {
            override fun getChild(firstParent: Chromosome, secondParent: Chromosome): Chromosome {
                val random = Random()
                val breakPoint = random.nextInt(firstParent.getSize())
                val child = DoubleArray(firstParent.getSize())
                for (index in 0 until firstParent.getSize()) {
                    if (index < breakPoint) {
                        child[index] = firstParent.getElementAt(index)
                    } else {
                        child[index] = secondParent.getElementAt(index)
                    }
                }
                return Chromosome(child)
            }
        }

        @JvmStatic
        val UNIFORM_CROSS_OVER = object : MatchMaker() {
            override fun getChild(firstParent: Chromosome, secondParent: Chromosome): Chromosome {
                val child = DoubleArray(firstParent.getSize())
                val random = Random()
                for (index in 0 until firstParent.getSize()) {
                    val firstsElement = firstParent.getElementAt(index)
                    val secondsElement = secondParent.getElementAt(index)
                    val firstsProbability = random.nextDouble()
                    if (firstsProbability < 0.5) {
                        child[index] = firstParent.getElementAt(index)
                    } else {
                        child[index] = secondParent.getElementAt(index)
                    }
                }
                return Chromosome(child)
            }
        }
    }
}