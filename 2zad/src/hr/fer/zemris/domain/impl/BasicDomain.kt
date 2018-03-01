package hr.fer.zemris.domain.impl

/**
 * Created by bmihaela.
 */

abstract class BasicDomain : Domain() {

    abstract fun getMaxComponent(): Int

    abstract fun getMinComponent(): Int

    override fun getComponent(index: Int): BasicDomain {
        return this
    }

    override fun getNumberOfComponents(): Int {
        return 1
    }
}