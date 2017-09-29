package itstym.work.myfitfuel.Adaptor

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import itstym.work.myfitfuel.Model.Product
import itstym.work.myfitfuel.ProductDetails
import itstym.work.myfitfuel.R
import kotlinx.android.synthetic.main.product_item.view.*

/**
 * Created by itstym on 23/9/17.
 */
class ProductClassAdaptor(var  mContext: Context, var productList:ArrayList<Product>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemCount(): Int {

        return productList.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        (holder as Item).bindData(mContext,productList.get(position))

        holder.itemView.setOnClickListener {

            //open the product details activity

            val intent: Intent =Intent(mContext,ProductDetails::class.java)
            intent.putExtra("selected_product_id",productList.get(position).productId)
            mContext.startActivity(intent)

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false)
        return Item(v)
    }



    class Item(itemView: View):RecyclerView.ViewHolder(itemView){

        fun bindData(context: Context, productDetails:Product){

            try{
                Log.v("Product image ",productDetails.productImageUrl.toString())
                itemView.productImage.setImageDrawable(ContextCompat.getDrawable(context,productDetails.productImageUrl!!))
            }catch (v:Exception){
                v.printStackTrace()
            }

            itemView.productName.text = productDetails.productName
            itemView.productPrice.text = productDetails.productPrice.toString()
        }

    }


}