package hr.fer.zemris

import hr.fer.zemris.genetic.chromosome.Chromosome
import java.util.*

/**
 * Created by bmihaela.
 */

abstract class Mutate{

    abstract fun radiationActivate(chromosome: Chromosome, mutationProbability: Double, deviation: Double)

    companion object {

        @JvmStatic
        val FLOATING_POINT_GAUSSIAN_MUTATION_ADD = object : Mutate() {
            override fun radiationActivate(chromosome: Chromosome, mutationProbability: Double, deviation: Double) {
                val random = Random()
                for (index in 0 until chromosome.getSize()) {
                    val cantMutate = random.nextDouble()
                    if (cantMutate < mutationProbability) {
                        val chromosomeValue = chromosome.getElementAt(index)
                        chromosome.setElementAt(index, chromosomeValue + random.nextGaussian() * deviation)
                    }
                }
            }
        }

        @JvmStatic
        val FLOATING_POINT_GAUSSIAN_MUTATION_REPLACE = object : Mutate() {
            override fun radiationActivate(chromosome: Chromosome, mutationProbability: Double, deviation: Double) {
                val random = Random()
                for (index in 0 until chromosome.getSize()) {
                    val cantMutate = random.nextDouble()
                    if (cantMutate < mutationProbability) {
                        chromosome.setElementAt(index, random.nextGaussian() * deviation)
                    }
                }
            }
        }
    }
}