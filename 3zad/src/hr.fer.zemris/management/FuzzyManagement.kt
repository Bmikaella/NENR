package hr.fer.zemris.management

/**
 * Created by bmihaela.
 */
interface FuzzyManagement{

    fun getNewOutputValue(rightAngledDistance: Int, leftAngledDistance: Int,rightDistance: Int, leftDistance: Int, speed: Int, oke : Int):Int

}