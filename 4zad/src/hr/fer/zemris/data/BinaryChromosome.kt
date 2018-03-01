package hr.fer.zemris.data

/**
 * Created by bmihaela.
 */
class BinaryChromosome(override val data: DoubleArray, val binaryChromosomeTemplate: IChromosomeTemplate)
    : Chromosome() {

    override fun inverse(index: Int) {
        data[index] = 1 - data[index]
    }


    override fun getTemplate(): IChromosomeTemplate {
        return binaryChromosomeTemplate
    }

    override fun getSolutionData(): Dot {
        val binaryChromosomeData = DoubleArray(binaryChromosomeTemplate.getNumberOfChromosomes())
        var sum = 0.0
        var element = 0
        var pot = binaryChromosomeTemplate.getChromosomeLength() - 1
        for (i in 1..data.size) {
            sum += Math.pow(2.0, pot + 0.0) * data[i - 1]
            if (pot == 0) {
                binaryChromosomeData[element] = sum
                sum = 0.0
                pot = binaryChromosomeTemplate.getChromosomeLength() - 1
                element++
            } else {
                pot--
            }
        }
        val realData = DoubleArray(binaryChromosomeData.size)
        for (index in 0 until binaryChromosomeData.size) {
            realData[index] = binaryChromosomeTemplate.getLB() + (binaryChromosomeData[index] / Math.pow(2.0, binaryChromosomeTemplate.getChromosomeLength() + 0.0)
                    * (binaryChromosomeTemplate.getUB() - binaryChromosomeTemplate.getLB()))
        }
        return Dot(realData)
    }

    override fun toString(): String {
        val builder = StringBuilder("(")
        for (elem in this) {
            builder.append("$elem ")
        }
        builder.append(") --- ")
        builder.append(this.getSolutionData())
        return builder.toString()
    }

}