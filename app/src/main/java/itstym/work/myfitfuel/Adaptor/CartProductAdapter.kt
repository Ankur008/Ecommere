package itstym.work.myfitfuel.Adaptor

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import itstym.work.myfitfuel.DataBase.DbHelper
import itstym.work.myfitfuel.Interface.onQuantityChanged
import itstym.work.myfitfuel.Model.Product
import itstym.work.myfitfuel.R
import kotlinx.android.synthetic.main.cart_product_item.view.*

/**
 * Created by itstym on 24/9/17.
 */

class CartProductAdapter(var  mContext: Context, var productList:ArrayList<Product>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemCount(): Int {

        return productList.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        (holder as Item).bindData(mContext,productList.get(position))

        holder.itemView.removeProduct.setOnClickListener {

            Log.v("Position ",(holder as Item).adapterPosition.toString())

            val unit=(holder as Item).itemView.totalUnit.text.toString().toInt();

            dialogOpen(productList.get(position),mContext,(holder as Item).adapterPosition,unit)
        }
    }

    private fun  dialogOpen(product: Product,context: Context,position: Int, totalUnit:Int) {

        val simpleAlert = AlertDialog.Builder(context).create()
                simpleAlert.setTitle("Alert")
                simpleAlert.setMessage("Are you sure you want to remove product?")

                simpleAlert.setButton(AlertDialog.BUTTON_POSITIVE, "Remove", {
                    dialogInterface, i ->
                        removeItemFromCart(product,context,position,totalUnit)
                })

                simpleAlert.setButton(AlertDialog.BUTTON_NEGATIVE,"Leave",{

                    dialogInterface, i ->
                        simpleAlert.dismiss()
                })

        simpleAlert.show()

    }

    private fun  removeItemFromCart(product: Product, context: Context,position: Int,totalUnit: Int) {

        DbHelper.getInstance(context).removeProductFromCart(product.productId)

        val amountToDeduct=totalUnit*product.productPrice

        val activity= context as Activity

        (activity as onQuantityChanged).onButtonClicked(amountToDeduct, false)

        productList.removeAt(position)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(mContext).inflate(R.layout.cart_product_item, parent, false)
        return Item(v)
    }



    class Item(itemView: View): RecyclerView.ViewHolder(itemView){



        fun bindData(context: Context, productDetails: Product){


            try{
                Log.v("Product image ",productDetails.productImageUrl.toString())
                itemView.productImage.setImageDrawable(ContextCompat.getDrawable(context,productDetails.productImageUrl))
            }catch (v:Exception){
                v.printStackTrace()
            }

            itemView.productName.text = productDetails.productName
            itemView.productPrice.text = productDetails.productPrice.toString()

            itemView.unitAdd.setOnClickListener {
                itemView.totalUnit.text = (itemView.totalUnit.text.toString().toInt()+1).toString()
                itemView.productPrice.text = (productDetails.productPrice*itemView.totalUnit.text.toString().toInt()).toString()

                val activity= context as Activity

                (activity as onQuantityChanged).onButtonClicked(productDetails.productPrice, true)

            }

            itemView.unitSubtract.setOnClickListener {

                if (itemView.totalUnit.text.toString().toInt()>1){
                    itemView.totalUnit.text = (itemView.totalUnit.text.toString().toInt()-1).toString()
                    itemView.productPrice.text = (productDetails.productPrice*itemView.totalUnit.text.toString().toInt()).toString()
                    val activity= context as Activity

                    (activity as onQuantityChanged).onButtonClicked(productDetails.productPrice, false)

                }
            }


        }



    }


}