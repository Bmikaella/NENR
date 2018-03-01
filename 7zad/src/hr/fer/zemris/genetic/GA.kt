package hr.fer.zemris

import hr.fer.zemris.functions.OptimizingFunction
import hr.fer.zemris.genetic.chromosome.Chromosome
import java.util.*

/**
 * Created by bmihaela.
 */
class GA(val function: OptimizingFunction) {

    companion object {
        @JvmStatic
        val EPSILON = 10e-6

        @JvmStatic
        val NORMALIZATION_STEP = 5
    }

    fun eliminationOptimization(generationSize: Int, mutationProbability: Double, maxIterationCount: Int,
                                printIterationValue: Boolean, mutator: Mutate, matchMaker: MatchMaker,
                                 statusIteration: Int, tournamentSize: Int = 3,
                                rouletteOrRandom: Boolean = false, chromosomeSize : Int, deviation : Double): Pair<Chromosome, Pair<Int, Double>> {

        val currentGeneration = createGeneration(generationSize,chromosomeSize)
        var bestUnitData: Pair<Chromosome, Double> = getBestData(currentGeneration)
        var iteration = 0
        val goodnessSortedGeneration = mutableListOf<Pair<Chromosome, Double>>()
        for (unit in currentGeneration) {
            val good = good(unit)
            goodnessSortedGeneration.add(Pair(unit, good))
        }
        goodnessSortedGeneration.sortBy { it -> it.second }

        var child : Chromosome
        var parents : Pair<Pair<Chromosome, Double>, Pair<Chromosome, Double>>
        do {
            parents = getTwoParentsAndKickOne(goodnessSortedGeneration, tournamentSize, rouletteOrRandom)
            child = matchMaker.getChild(parents.first.first, parents.second.first)
            mutator.radiationActivate(child, mutationProbability,deviation)
            if (punishment(child) < bestUnitData.second) {
                bestUnitData = Pair(child, punishment(child))
            }
            goodnessSortedGeneration.add(Pair(child, good(child)))
            goodnessSortedGeneration.sortBy { it -> it.second }
            if (printIterationValue && iteration % statusIteration == 0) {
                println("Iteration: $iteration \nBest unit is: ${bestUnitData.first} \nError is: ${bestUnitData.second}")
            }
            iteration++
        } while (bestUnitData.second > EPSILON && iteration < maxIterationCount)
        println("Iterations done: $iteration")
        return Pair(bestUnitData.first, Pair(iteration, bestUnitData.second))
    }

    private fun getTwoParentsAndKickOne(currentGeneration: MutableList<Pair<Chromosome, Double>>, tournamentSize: Int, rouletteOrRandom: Boolean): Pair<Pair<Chromosome, Double>, Pair<Chromosome, Double>> {
        //n-tur
        val parents: MutableList<Pair<Chromosome, Double>>
        if (rouletteOrRandom) {
            parents = chooseParentsRouletteWheel(currentGeneration, tournamentSize)
        } else {
            parents = chooseRandomParents(currentGeneration, tournamentSize)
        }
        var worst = parents[0]
        for (parent in parents) {
            if (worst.second > parent.second) {
                worst = parent
            }
        }
        currentGeneration.remove(worst)
        parents.remove(worst)

        val chosenParents: MutableList<Pair<Chromosome, Double>>
        if (rouletteOrRandom) {
            chosenParents = chooseParentsRouletteWheel(parents, 2)
        } else {
            chosenParents = chooseRandomParents(parents, 2)
        }
        return Pair(chosenParents.first(), chosenParents.last())
    }

    private fun chooseRandomParents(currentGeneration: MutableList<Pair<Chromosome, Double>>, tournamentSize: Int): MutableList<Pair<Chromosome, Double>> {
        val parents = mutableListOf<Pair<Chromosome, Double>>()
        val random = Random()
        for (i in 0 until tournamentSize) {
            parents.add(currentGeneration[random.nextInt(currentGeneration.size)])
        }
        return parents
    }


    private fun chooseParentsRouletteWheel(currentGeneration: MutableList<Pair<Chromosome, Double>>, generationSize: Int): MutableList<Pair<Chromosome, Double>> {

        val normalizedChromosomeGoodness = mutableListOf<Pair<Chromosome, Int>>()
        var sum = 0
        var goodness = NORMALIZATION_STEP
        for (chromosome in currentGeneration) {
            sum += goodness
            normalizedChromosomeGoodness.add(Pair(chromosome.first, goodness))
            goodness += NORMALIZATION_STEP
        }

        val parents = mutableListOf<Pair<Chromosome, Double>>()
        val random = Random()
        for (index in 0 until generationSize) {
            val choose = random.nextInt(sum)
            val chosenOne = chooseOne(choose, normalizedChromosomeGoodness)
            parents.add(Pair(chosenOne, good(chosenOne)))
        }
        return parents
    }

    private fun chooseOne(chosenPunishment: Int, currentGeneration: MutableList<Pair<Chromosome, Int>>): Chromosome {
        var sum = 0
        var goodness = NORMALIZATION_STEP
        for (index in 0 until currentGeneration.size) {
            sum += goodness
            if (sum >= chosenPunishment) {
                return currentGeneration[index].first
            }
            goodness += NORMALIZATION_STEP
        }
        return currentGeneration[currentGeneration.size - 1].first
    }


    private fun getBestData(currentGeneration: MutableList<Chromosome>): Pair<Chromosome, Double> {
        var bestUnit = currentGeneration[0]
        var smallestPunishment = punishment(bestUnit)
        for (unit in currentGeneration) {
            val currentPunishment = punishment(unit)
            if (currentPunishment < smallestPunishment) {
                smallestPunishment = currentPunishment
                bestUnit = unit
            }
        }
        return Pair(bestUnit, smallestPunishment)
    }


    private fun createGeneration(generationSize: Int, chromosomeSize: Int): MutableList<Chromosome> {
        val generation = mutableListOf<Chromosome>()
        for (i in 0 until generationSize) {
            generation.add(Chromosome(chromosomeSize))
        }
        return generation
    }

    fun good(solution: Chromosome): Double {
        val punishment = punishment(solution)
        return if (punishment == 0.0) Double.MAX_VALUE else 1 / punishment
    }

    fun punishment(solution: Chromosome): Double {
        return function.calculate(solution.getSolutionData())
    }


}