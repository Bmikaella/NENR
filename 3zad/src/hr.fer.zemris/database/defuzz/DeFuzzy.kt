package hr.fer.zemris.database.defuzz

import hr.fer.zemris.fuzzy.IFuzzySet

/**
 * Created by bmihaela.
 */

interface DeFuzzy {

    fun deFuzzyMe(fuzzySet: IFuzzySet): Int

}