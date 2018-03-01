package hr.fer.zemris.data

import java.util.*

/**
 * Created by bmihaela.
 */
class BinaryChromosomeTemplate(private val lL: Double, private val uL: Double, private val numberOfChromosomes: Int, decimalCount: Int) : IChromosomeTemplate {
    private val chromosomeLength = Math.ceil(Math.log10((uL - lL) * Math.pow(10.0, 1.0 * decimalCount) + 1) / Math.log10(2.0)).toInt()


    override fun getChromosomeLength(): Int {
        return chromosomeLength
    }

    override fun getNumberOfChromosomes(): Int {
        return numberOfChromosomes
    }

    override fun createRandomSolution(): Chromosome {
        val size: Int = numberOfChromosomes * chromosomeLength
        val data = DoubleArray(size)
        val random = Random()
        for (i in 0 until size) {
            if (random.nextBoolean()) {
                data[i] = 1.0
            } else {
                data[i] = 0.0
            }
        }
        return BinaryChromosome(data, this)
    }

    override fun getLB(): Double {
        return lL
    }

    override fun getUB(): Double {
        return uL
    }
}