package hr.fer.zemris.function

import hr.fer.zemris.contract.Preconditions
import hr.fer.zemris.data.Dot

/**
 * Created by bmihaela.
 */
abstract class Function {

    abstract fun calculate(dot: Dot): Double

    companion object {

        @JvmStatic
        val ROSENBROCK_FUNCTION = object : Function() {

            override fun calculate(dot: Dot): Double {
                Preconditions.requireFunctionTo(dot.getSize() == 2, "This functionWithParameters only works with two variables")

                return 100 * Math.pow(dot.getElementAt(1) - Math.pow(dot.getElementAt(0), 2.0), 2.0) +
                        Math.pow(1 - dot.getElementAt(0), 2.0)
            }
        }

        @JvmStatic
        val SIMPLE_FUNCTION = object : Function() {
            override fun calculate(dot: Dot): Double {
                var sum = 0.0
                dot.forEachIndexed { index, d -> sum += Math.pow(d - (index + 1), 2.0) }
                return sum
            }
        }


        @JvmStatic
        val ALMOST_SCHAFFERS_FUNCTION = object : Function() {
            override fun calculate(dot: Dot): Double {
                var sqSum = 0.0
                for (elem in dot) {
                    sqSum += Math.pow(elem, 2.0)
                }
                var result = Math.sin(50 * Math.pow(sqSum, 0.1))
                result = Math.pow(result, 2.0) + 1
                result *= Math.pow(sqSum, 0.25)
                return result
            }

        }

        @JvmStatic
        val SCHAFFERS_FUNCTION = object : Function() {
            override fun calculate(dot: Dot): Double {
                var sqSum = 0.0
                for (elem in dot) {
                    sqSum += Math.pow(elem, 2.0)
                }
                var result = Math.pow(Math.sin(Math.sqrt(sqSum)), 2.0)
                result -= 0.5
                result /= Math.pow(1 + 0.001 * sqSum, 2.0)
                result += 0.5
                return result
            }

        }
    }

}