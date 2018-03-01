package hr.fer.zemris.data

/**
 * Created by bmihaela.
 */
interface IChromosomeTemplate {

    fun createRandomSolution(): Chromosome

    fun getLB(): Double

    fun getUB(): Double

    fun getChromosomeLength():Int

    fun getNumberOfChromosomes():Int
}