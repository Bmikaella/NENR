package hr.fer.zemris

import hr.fer.zemris.data.Chromosome
import hr.fer.zemris.data.IChromosomeTemplate
import java.util.*

/**
 * Created by bmihaela.
 */

abstract class Mutate {

    abstract fun radiationActivate(chromosome: Chromosome, mutationProbability: Double, IChromosomeTemplate: IChromosomeTemplate)

    companion object {
        @JvmStatic
        val BINARY_SIMPLE_MUTATION = object : Mutate() {
            override fun radiationActivate(chromosome: Chromosome, mutationProbability: Double, IChromosomeTemplate: IChromosomeTemplate) {
                val random = Random()
                for (index in 0 until chromosome.getSize()) {
                    val cantMutate = random.nextDouble()
                    if (cantMutate < mutationProbability) {
                        chromosome.inverse(index)
                    }
                }
            }
        }
        @JvmStatic
        val FLOATING_POINT_MUTATION = object : Mutate() {
            override fun radiationActivate(chromosome: Chromosome, mutationProbability: Double, IChromosomeTemplate: IChromosomeTemplate) {
                val random = Random()
                for (index in 0 until chromosome.getSize()) {
                    val cantMutate = random.nextDouble()
                    if (cantMutate < mutationProbability) {
                        val LBM = maxOf(IChromosomeTemplate.getLB(), chromosome.getElementAt(index) - (IChromosomeTemplate.getUB() - IChromosomeTemplate.getLB()) * random.nextDouble())
                        val UBM = minOf(IChromosomeTemplate.getUB(), chromosome.getElementAt(index) + (IChromosomeTemplate.getUB() - IChromosomeTemplate.getLB()) * random.nextDouble())
                        chromosome.setElementAt(index, LBM + random.nextDouble() * (UBM - LBM))
                    }
                }
            }
        }
    }
}