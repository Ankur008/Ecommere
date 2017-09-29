package itstym.work.myfitfuel

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import itstym.work.myfitfuel.DataBase.DbHelper
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlin.properties.Delegates

class ProductDetails : AppCompatActivity() {

    var productImageUrls:IntArray by Delegates.notNull<IntArray>()
    var selectedProductId:Int by Delegates.notNull<Int>()
    private var dots: Array<TextView?>? = null
    var myViewPagerAdapter:MyViewPagerAdapter by Delegates.notNull<MyViewPagerAdapter>()


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id=item?.itemId

        if (id==R.id.cart){
            val intent: Intent = Intent(this@ProductDetails,CartActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item);
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home,menu)
        return true;
    }

    override fun onResume() {
        super.onResume()



        buyNow.setOnClickListener {

            DbHelper.getInstance(this@ProductDetails).addProductToCart(selectedProductId)
            //go to cart page
            val intent: Intent = Intent(this@ProductDetails,CartActivity::class.java)
            startActivity(intent)

        }

        addToCart.setOnClickListener {

            Toast.makeText(this@ProductDetails,"Product added in the cart",Toast.LENGTH_LONG).show()

            DbHelper.getInstance(this@ProductDetails).addProductToCart(selectedProductId)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)


        supportActionBar?.title="Product Details"

        Log.v("Selected Product Id ",intent.getIntExtra("selected_product_id",1).toString())
        selectedProductId=intent.getIntExtra("selected_product_id",1)

        //product images
        productImageUrls=kotlin.IntArray(3)
        productImageUrls[0] = DbHelper.getInstance(this@ProductDetails).getProductById(selectedProductId,this@ProductDetails)!!.productImageUrl
        productImageUrls[1] = R.drawable.whey_protein_powder_sport_formulacanister_chocolate
        productImageUrls[2] = R.drawable.bbp_chocolate_frontrendering

        //add bottom dots
        addBottomDots(0)

        //setup image
        myViewPagerAdapter= MyViewPagerAdapter(supportFragmentManager,productImageUrls)
        imageViewPager.adapter=myViewPagerAdapter
        imageViewPager.addOnPageChangeListener(viewPagerPageChangeListener)

        ProductName.text=DbHelper.getInstance(this@ProductDetails).getProductById(selectedProductId,this@ProductDetails)?.productName
        productPrice.text=DbHelper.getInstance(this@ProductDetails).getProductById(selectedProductId,this@ProductDetails)?.productPrice.toString()
        deliveredBy.text=DbHelper.getInstance(this@ProductDetails).getProductById(selectedProductId,this@ProductDetails)?.productDeliveredBy
    }

    private fun  addBottomDots(currentPage: Int) {

        dots= arrayOfNulls<TextView>(3)

        var colorActive=resources.getIntArray(R.array.array_dot_active)
        var colorInActive=resources.getIntArray(R.array.array_dot_inactive)

        dotsLayout.removeAllViews()

        for (i in 0..dots!!.size - 1) {
            dots!![i] = TextView(this)
            dots!![i]!!.text = Html.fromHtml("&#8226;")
            dots!![i]!!.textSize = 35f
            dots!![i]!!.setTextColor(colorInActive[currentPage])
            dotsLayout.addView(dots!![i])
        }

        if (dots!!.isNotEmpty())
            dots!![currentPage]!!.setTextColor(colorActive[currentPage]);



    }

    //  viewpager change listener
    var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            addBottomDots(position)
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

        }

        override fun onPageScrollStateChanged(arg0: Int) {

        }
    }


    class MyViewPagerAdapter: FragmentStatePagerAdapter{

        override fun getCount(): Int {
            return images.size
        }

        var images:IntArray by Delegates.notNull<IntArray>()

        constructor(fragmentManager: FragmentManager,images:IntArray) : super(fragmentManager) {
            this.images=images;
        }

        override fun getItem(position: Int): Fragment {
            return callFragment(images[position])
        }

        private fun  callFragment(imageDrawablePath: Int): Fragment {

            var fragment=ImagesSlider()
            var bundle=Bundle()
            bundle.putInt("image",imageDrawablePath)
            fragment.arguments=bundle

            return fragment;

        }
    }
}

