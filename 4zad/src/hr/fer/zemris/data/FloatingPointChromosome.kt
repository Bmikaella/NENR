package hr.fer.zemris.data

/**
 * Created by bmihaela.
 */
class FloatingPointChromosome(override val data: DoubleArray, val floatPointChromosomeTemplate: IChromosomeTemplate) : Chromosome() {

    override fun inverse(index: Int) {
        data[index] = 1 * data[index]
    }

    override fun getTemplate(): IChromosomeTemplate {
        return floatPointChromosomeTemplate
    }

    override fun getSolutionData(): Dot {
        return Dot(data)
    }
}