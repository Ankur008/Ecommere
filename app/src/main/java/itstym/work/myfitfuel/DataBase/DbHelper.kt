package itstym.work.myfitfuel.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import itstym.work.myfitfuel.Model.Address
import itstym.work.myfitfuel.Model.Product



/**
 * Created by itstym on 24/9/17.
 */


class DbHelper private constructor(context: Context) : SQLiteOpenHelper(context, dataBaseName, null, 1) {


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }


    companion object {

        var sInstance:DbHelper?=null
        val dataBaseName="myFitFuel"

        val productTableName="productList"
        val productId="product_id"
        val productName="name"
        val productPricePerUnit="price_per_unit"
        val productPhotos="product_image"
        val productDeliveredBy="product_delivered_by"

        val userAddressTableName="userAddress"
        val userAddressFullName="userFullName"
        val userAddressMobileNumber="userMobileNumber"
        val userAddressAddress="userAddress"
        val userAddressAddressState="userAddressState"


        val  cartTableName="cart"



        fun getInstance(context: Context):DbHelper{

            if (sInstance==null)
                sInstance= DbHelper(context)

            return sInstance!!
        }
    }


    override fun onCreate(sqlLiteDatabase: SQLiteDatabase?) {

        Log.e("On create ","call")

        sqlLiteDatabase?.execSQL(
                "create table if not exists productList " +
                        "(" + productId + " INTEGER PRIMARY KEY AUTOINCREMENT, " + productName + " TEXT NOT NULL, " + productPhotos + " INTEGER NOT NULL, " +
                        " " + productPricePerUnit + " INTEGER NOT NULL, " + productDeliveredBy + " TEXT NOT NULL )"
        )

        sqlLiteDatabase?.execSQL(
                "create table if not exists cart "+
                        "("+ productId + " INTEGER NOT NULL )"
        )

        sqlLiteDatabase?.execSQL(
                "create table if not exists userAddress " +
                        "(" + userAddressFullName + " TEXT NOT NULL, " + userAddressMobileNumber + " TEXT NOT NULL, " + userAddressAddress + " TEXT  NOT NULL, " +
                        " " + userAddressAddressState + " TEXT  NOT NULL )"
        )



    }

    fun addProduct(product: Product){

        val database=this.writableDatabase

        val contentValues=ContentValues()
        contentValues.put(productName,product.productName)
        contentValues.put(productPhotos,product.productImageUrl)
        contentValues.put(productPricePerUnit,product.productPrice)
        contentValues.put(productDeliveredBy,product.productDeliveredBy)

        database.insert(productTableName,null,contentValues)

    }

    fun getProduct(context: Context):ArrayList<Product>{

        val database = this.readableDatabase

        val productList = ArrayList<Product>()
        val cursor = database.rawQuery("select * from " + productTableName, null)
        cursor.moveToFirst()

        while (!cursor.isAfterLast) {

            val product=Product(context)
            product.productName=cursor.getString(cursor.getColumnIndex(productName))
            product.productPrice=cursor.getInt(cursor.getColumnIndex(productPricePerUnit))
            product.productImageUrl=cursor.getInt(cursor.getColumnIndex(productPhotos))
            product.productDeliveredBy=cursor.getString(cursor.getColumnIndex(productDeliveredBy))
            product.productId=cursor.getInt(cursor.getColumnIndex(productId))

            productList.add(product)

            cursor.moveToNext()
        }

        cursor.close()

        return productList

    }


    fun addProductToCart(product_id:Int){

        val database=this.writableDatabase

        val contentValues=ContentValues()
        contentValues.put(productId,product_id)

        database.insert(cartTableName,null,contentValues)

    }

    fun getProductById(product_id: Int,context: Context):Product?{

        val database = this.readableDatabase

        val cursor = database.rawQuery("select * from $productTableName where $productId = $product_id", null)
        cursor.moveToFirst()

        if (!cursor.isAfterLast){

            val product=Product(context)
            product.productName=cursor.getString(cursor.getColumnIndex(productName))
            product.productPrice=cursor.getInt(cursor.getColumnIndex(productPricePerUnit))
            product.productImageUrl=cursor.getInt(cursor.getColumnIndex(productPhotos))
            product.productDeliveredBy=cursor.getString(cursor.getColumnIndex(productDeliveredBy))
            product.productId=cursor.getInt(cursor.getColumnIndex(productId))

            cursor.close()

            return product;

        }else
            return null

    }

    fun removeProductFromCart(product_id: Int){

        val database = this.readableDatabase

        database.delete(cartTableName, productId+" = "+product_id, null)

    }

    fun getProductFromCart():ArrayList<Int>{

        val database = this.readableDatabase

        val productList = ArrayList<Int>()
        val cursor = database.rawQuery("select * from " + cartTableName, null)
        cursor.moveToFirst()

        while (!cursor.isAfterLast) {

            productList.add(cursor.getInt(cursor.getColumnIndex(productId)))

            cursor.moveToNext()
        }

        cursor.close()

        return productList


    }

    fun  addAddress(name: String, mobileNumber: String, address: String, state: String) {

        val database=this.writableDatabase

        val contentValues=ContentValues()
        contentValues.put(userAddressFullName,name)
        contentValues.put(userAddressMobileNumber,mobileNumber)
        contentValues.put(userAddressAddress,address)
        contentValues.put(userAddressAddressState,state)

        database.insert(userAddressTableName,null,contentValues)
    }

    fun getAddress(context: Context):Address?{

        val database = this.readableDatabase

        val cursor = database.rawQuery("select * from " + userAddressTableName, null)
        cursor.moveToFirst()

        if (!cursor.isAfterLast) {

            var address=Address(context)
            address.name=cursor.getString(cursor.getColumnIndex(userAddressFullName))
            address.cellPhone=cursor.getString(cursor.getColumnIndex(userAddressMobileNumber))
            address.address=cursor.getString(cursor.getColumnIndex(userAddressAddress))
            address.state=cursor.getString(cursor.getColumnIndex(userAddressAddressState))

            return address
        }else
            return null

    }

    fun removeAllProductFromCart() {

        val database = this.writableDatabase

        database.execSQL("delete from "+ cartTableName)


    }


}