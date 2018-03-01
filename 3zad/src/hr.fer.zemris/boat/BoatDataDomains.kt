package hr.fer.zemris.boat

import hr.fer.zemris.domain.impl.SimpleDomain

/**
 * Created by bmihaela.
 */

class BoatDataDomains {

    companion object{

        @JvmStatic
        val DISANCE_DOMAIN = SimpleDomain(0,1301)

        @JvmStatic
        val SPEED_DOMAIN = SimpleDomain(0,60)

        @JvmStatic
        val RUDDER_DOMAIN = SimpleDomain(0,180)

        @JvmStatic
        val AXLE_DOMAIN = SimpleDomain(0,10)
    }
}