package hr.fer.zemris.util

import hr.fer.zemris.fuzzy.exc.DomainException
import hr.fer.zemris.fuzzy.exc.RelationException

/**
 * Created by bmihaela.
 */


class Preconditions {

    companion object {
        @JvmStatic
        fun requireRelationTo(condition: Boolean, message: String, vararg x: String) {
            if (!condition) {
                throw RelationException(message.format(x))
            }
        }

        @JvmStatic
        fun requireDomainTo(condition: Boolean, message: String, vararg x: String) {
            if (!condition) {
                throw DomainException(message.format(x))
            }
        }
    }
}