package hr.fer.zemris.data

/**
 * Created by bmihaela.
 */
class FloatPointChromosomeTemplate(private val lL: Double, private val uL: Double, private val numberOfChromosomes: Int) : IChromosomeTemplate {
    override fun createRandomSolution(): Chromosome {
        return FloatingPointChromosome(Dot.randomDot(lL, uL, numberOfChromosomes).getData(), this)
    }

    override fun getLB(): Double {
        return lL
    }

    override fun getChromosomeLength(): Int {
        return 1
    }

    override fun getNumberOfChromosomes(): Int {
        return numberOfChromosomes
    }

    override fun getUB(): Double {
        return uL
    }
}