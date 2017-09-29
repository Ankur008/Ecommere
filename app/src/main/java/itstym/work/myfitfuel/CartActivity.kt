package itstym.work.myfitfuel

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import itstym.work.myfitfuel.Adaptor.CartProductAdapter
import itstym.work.myfitfuel.DataBase.DbHelper
import itstym.work.myfitfuel.Interface.onQuantityChanged
import itstym.work.myfitfuel.Model.Product
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() , onQuantityChanged {


    override fun onButtonClicked(amountToAdd: Int, isAdd: Boolean) {

        Log.v("Button clicked ","yes")

        if (isAdd){
            amount+=amountToAdd
            setUpCalc(amount)
        }else{
            amount-=amountToAdd
            setUpCalc(amount)
        }
    }


    override fun onResume() {
        super.onResume()

        setUpCalc(amount)

        proceed.setOnClickListener {


            val intent: Intent = Intent(this@CartActivity,AddNewAddressActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun  setUpCalc(amount_temp: Int) {

        gst=0.18*amount_temp
        totalAmount=amount_temp+gst

        productAmount.text = amount_temp.toString()
        gstAmount.text=gst.toString()
        totalProductValue.text=totalAmount.toString()

        Constant.totalPaymentAmount=totalAmount

    }

    var amount:Int=0
    var gst:Double =0.0
    var totalAmount:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)


        supportActionBar?.title="My Cart"

        if (DbHelper.getInstance(this@CartActivity).getProductFromCart().size==0){
            //no item available

            scrollView.visibility=View.GONE
            proceed.visibility=View.GONE
            noItem.visibility=View.VISIBLE
        }else{
            scrollView.visibility=View.VISIBLE
            proceed.visibility=View.VISIBLE
            noItem.visibility=View.GONE
        }

        cartProductRecyclerView.setHasFixedSize(true)
        cartProductRecyclerView.layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        cartProductRecyclerView.adapter= CartProductAdapter(this,getProductDetails())

    }

    fun getProductDetails():ArrayList<Product>{

        var dbHelper:DbHelper=DbHelper.getInstance(this@CartActivity)

        var prodcutIds:ArrayList<Int> = dbHelper.getProductFromCart()

        var productDetails:ArrayList<Product> = ArrayList()
        for (i in 0..prodcutIds.size-1){
            val singleProductDetails=dbHelper.getProductById(prodcutIds.get(i),this@CartActivity);
            amount += singleProductDetails!!.productPrice
            productDetails.add(singleProductDetails)
        }

        return productDetails
    }
}
