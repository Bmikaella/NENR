package hr.fer.zemris.boat

import hr.fer.zemris.fuzzy.IIntUnaryFunction
import hr.fer.zemris.fuzzy.StandardFuzzySets
import hr.fer.zemris.fuzzy.impl.CalculatedFuzzySet

/**
 * Created by bmihaela.
 */

class BoatSets {

    companion object {

        //inputSets
        @JvmStatic
        val SHORE_TOO_CLOSE = CalculatedFuzzySet(BoatDataDomains.DISANCE_DOMAIN, StandardFuzzySets.IFunction(30, 50))

        @JvmStatic
        val SHORE_TOO_CLOSE_SPEED_SLOW = CalculatedFuzzySet(BoatDataDomains.DISANCE_DOMAIN, StandardFuzzySets.IFunction(20, 35))

        @JvmStatic
        val SHORE_ALL_GOES = CalculatedFuzzySet(BoatDataDomains.DISANCE_DOMAIN, object : IIntUnaryFunction {
            override fun valueAt(index: Int): Double {
                return 1.0
            }
        })

        @JvmStatic
        val SPEED_SLOW = CalculatedFuzzySet(BoatDataDomains.SPEED_DOMAIN, StandardFuzzySets.IFunction(20, 25))

        @JvmStatic
        val RUDDER_OFFSET = 90
        //OUTPUT sets
        @JvmStatic
        val RUDDER_STEEP_RIGHT = CalculatedFuzzySet(BoatDataDomains.RUDDER_DOMAIN, StandardFuzzySets.IFunction(-45 + RUDDER_OFFSET, -15 + RUDDER_OFFSET))

        @JvmStatic
        val RUDDER_STEEP_LEFT = CalculatedFuzzySet(BoatDataDomains.RUDDER_DOMAIN, StandardFuzzySets.gammaFunction(15 + RUDDER_OFFSET, 45 + RUDDER_OFFSET))

        @JvmStatic
        val AXLE_FAST = CalculatedFuzzySet(BoatDataDomains.AXLE_DOMAIN, StandardFuzzySets.gammaFunction(1, 10))

    }
}