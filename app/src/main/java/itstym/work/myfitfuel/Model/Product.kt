package itstym.work.myfitfuel.Model

import android.content.Context
import kotlin.properties.Delegates

/**
 * Created by itstym on 23/9/17.
 */


data class Product(var context:Context){

    var productImageUrl:Int by Delegates.notNull<Int>()
    var productId:Int by Delegates.notNull<Int>()
    var productName:String by Delegates.notNull<String>()
    var productPrice:Int by Delegates.notNull<Int>()
    var productDeliveredBy:String by Delegates.notNull<String>()

}