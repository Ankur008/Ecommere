package itstym.work.myfitfuel

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.image_item.view.*
import kotlin.properties.Delegates

/**
 * Created by itstym on 24/9/17.
 */


class ImagesSlider: Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view=inflater!!.inflate(R.layout.image_item,container,false)

        view.productImage.setImageDrawable(ContextCompat.getDrawable(context,imagePath))

        return  view;
    }

    var imagePath:Int by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments!=null){
            imagePath=arguments.getInt("image")
        }
    }



}