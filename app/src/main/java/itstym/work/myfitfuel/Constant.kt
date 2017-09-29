package itstym.work.myfitfuel

import kotlin.properties.Delegates

/**
 * Created by itstym on 24/9/17.
 */

class Constant{

    companion object {

        var totalPaymentAmount:Double by Delegates.notNull<Double>()
    }
}