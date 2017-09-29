package itstym.work.myfitfuel.Model

import android.content.Context
import kotlin.properties.Delegates

/**
 * Created by itstym on 24/9/17.
 */
data class Address(val context: Context) {

    var name:String by Delegates.notNull<String>()
    var cellPhone:String by Delegates.notNull<String>()
    var address:String by Delegates.notNull<String>()
    var state:String by Delegates.notNull<String>()


}