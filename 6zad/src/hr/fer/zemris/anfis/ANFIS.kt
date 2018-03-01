package hr.fer.zemris.anfis

import hr.fer.zemris.Util
import hr.fer.zemris.data.Data

/**
 * Created by bmihaela.
 */


class ANFIS(numberOfRules: Int) {


    val rules: MutableList<Rule> = mutableListOf()
    var currentPassWeights = mutableListOf<XYWeight>()
    var currentPassKonzequensusWeight = mutableListOf<Double>()

    init {

        for (x in 1..numberOfRules) {
            rules.add(Rule())
        }
    }

    fun forwardPass(x: Double, y: Double): Double {
        currentPassWeights.clear()
        currentPassKonzequensusWeight.clear()

        for (rule in rules) {
            val ruleWeights = rule.getAntecedensWeights(x, y)
            currentPassWeights.add(ruleWeights)
            currentPassKonzequensusWeight.add(rule.getRuleWeight(ruleWeights))

        }

        val konzWeightsSum = currentPassKonzequensusWeight.sum()

        var result = 0.0
        for (index in 0 until rules.size) {
            val output = rules[index].howMuchLoveIsThere(x, y)
            result += currentPassKonzequensusWeight[index] * output
        }

        result /= konzWeightsSum

        return result
    }

    fun getError(data: MutableList<Data>): Double {
        var error = 0.0
        for (unit in data) {
            error += Math.pow(unit.output - forwardPass(unit.dot.x, unit.dot.y), 2.0)
        }
        return (0.5 * error) / data.size
    }

    fun train(data: MutableList<Data>, givenLearningRateKonzeqvensus: Double, batchSize: Int, maxIterations: Int, status: Boolean,
              statusIteration: Int, givenLearningRateCompatibility: Double, decayLearningRate: Double, decayIteration: Int) {
        var learningRateCompatibility = givenLearningRateCompatibility
        var learningRateKonzeqvensus = givenLearningRateKonzeqvensus
        var currentIndex = 0
        var iterations = 0
        do {
            val batchData = Util.takeNNextElements(data, batchSize, currentIndex)
            currentIndex = batchData.second


            for (unit in batchData.first) {
                backwardPass(unit)
            }
            learn(learningRateCompatibility, learningRateKonzeqvensus)

            if (iterations % decayIteration == 0) {
                learningRateKonzeqvensus *= decayLearningRate
                learningRateCompatibility *= decayLearningRate
            }
            if (status && iterations % statusIteration == 0) {
                println("${getError(data)},")
            }

            iterations++
        } while (iterations < maxIterations)
    }

    private fun learn(learningRateCompatibility: Double, learningRateKonzeqvensus: Double) {
        for (rule in rules) {
            rule.addKonzekvensGradient(learningRateKonzeqvensus)
            rule.addCompatibilityGradient(learningRateCompatibility)
        }
    }

    fun backwardPass(data: Data) {
        val x = data.dot.x
        val y = data.dot.y

        val realY = data.output

        val my = forwardPass(x, y)
        val diff = (realY - my)
        val wSum = currentPassKonzequensusWeight.sum()


        for (index in 0 until rules.size) {
            val rule = rules[index]
            val w = currentPassKonzequensusWeight[index]
            val thisW = currentPassWeights[index]
            rule.xUndYAmor.gradientP += (diff) * (w / wSum) * x
            rule.xUndYAmor.gradientQ += (diff) * (w / wSum) * y
            rule.xUndYAmor.gradientR += (diff) * (w / wSum)

            var wf = 0.0
            val currentRuleOutput = rule.howMuchLoveIsThere(x, y)
            for (j in 0 until rules.size) {
                wf += currentPassKonzequensusWeight[j] * (currentRuleOutput - rules[j].howMuchLoveIsThere(x, y))
            }

            rule.xCompatibility.gradientA += diff * (thisW.wy * rule.xCompatibility.b * thisW.wx * (1 - thisW.wx)) * (wf / Math.pow(wSum, 2.0))
            rule.yCompatibility.gradientA += diff * (thisW.wx * rule.yCompatibility.b * thisW.wy * (1 - thisW.wy)) * (wf / Math.pow(wSum, 2.0))

            rule.xCompatibility.gradientB += diff * (thisW.wy * (rule.xCompatibility.a - x) * thisW.wx * (1 - thisW.wx)) * (wf / Math.pow(wSum, 2.0))
            rule.yCompatibility.gradientB += diff * (thisW.wx * (rule.yCompatibility.a - y) * thisW.wy * (1 - thisW.wy)) * (wf / Math.pow(wSum, 2.0))

        }

    }


}

