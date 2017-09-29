package itstym.work.myfitfuel

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.facebook.stetho.Stetho
import itstym.work.myfitfuel.Adaptor.ProductClassAdaptor
import itstym.work.myfitfuel.DataBase.DbHelper
import itstym.work.myfitfuel.Model.Product
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onBackPressed() {
        super.onBackPressed()

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id=item?.itemId

        if (id==R.id.cart){
            val intent: Intent = Intent(this@MainActivity,CartActivity::class.java)
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //To fetch database and show on chrome
        Stetho.initializeWithDefaults(this);

        var sharedPreferences:SharedPreferences=PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        var isDummyDataAdded=sharedPreferences.getBoolean("is_Data_add",false)

        if (!isDummyDataAdded){
            addProductInDB()
            sharedPreferences.edit().putBoolean("is_Data_add",true).apply()
        }

        productListing.setHasFixedSize(true)
        productListing.layoutManager= GridLayoutManager(this,2)
        productListing.adapter=ProductClassAdaptor(this,DbHelper.getInstance(this@MainActivity).getProduct(this@MainActivity))
    }

    private fun addProductInDB() {

        val dbHelper: DbHelper = DbHelper.getInstance(this@MainActivity)

        val productOne=Product(this);
        productOne.productName="BBP Chocolate Front Rendering"
        productOne.productPrice= 2378
        productOne.productImageUrl=R.drawable.bbp_chocolate_frontrendering
        productOne.productDeliveredBy="ANC Pvt LTD"

        dbHelper.addProduct(productOne)


        val productTwo=Product(this);
        productTwo.productName="Hi Protein Iced Chocolate"
        productTwo.productPrice= 2443
        productTwo.productImageUrl=R.drawable.hi_protein_iced_choc
        productTwo.productDeliveredBy="ANC Pvt LTD"
        dbHelper.addProduct(productTwo)


        val productThree=Product(this);
        productThree.productName="Low carb protein iced chocolate"
        productThree.productPrice= 2378
        productThree.productImageUrl=R.drawable.low_carb_protein_iced_choc
        productThree.productDeliveredBy="ANC Pvt LTD"
        dbHelper.addProduct(productThree)

        val productFour=Product(this);
        productFour.productName="Whey Chocolate"
        productFour.productPrice= 3336
        productFour.productImageUrl=R.drawable.qhey_chocolate
        productFour.productDeliveredBy="ANC Pvt LTD"
        dbHelper.addProduct(productFour)


        val productFive=Product(this);
        productFive.productName="The New Standard"
        productFive.productPrice= 4883
        productFive.productImageUrl=R.drawable.the_new_standard
        productFive.productDeliveredBy="ANC Pvt LTD"
        dbHelper.addProduct(productFive)


        val productSix=Product(this);
        productSix.productName="Whey Dutch Chocolate Supplements"
        productSix.productPrice= 1788
        productSix.productImageUrl=R.drawable.whey_dutch_chocolate_supplements
        productSix.productDeliveredBy="ANC Pvt LTD"
        dbHelper.addProduct(productSix)



    }

    private fun  getProducts(): ArrayList<Product> {



        val products=ArrayList<Product>()



        val productTwo=Product(this);
        productTwo.productName="Hi Protein Iced Chocolate"
        productTwo.productPrice= 2443
        productTwo.productImageUrl=R.drawable.hi_protein_iced_choc
        products.add(productTwo)


        val productThree=Product(this);
        productThree.productName="Low carb protein iced chocolate"
        productThree.productPrice= 2378
        productThree.productImageUrl=R.drawable.low_carb_protein_iced_choc
        products.add(productThree)

        val productFour=Product(this);
        productFour.productName="Whey Chocolate"
        productFour.productPrice= 3336
        productFour.productImageUrl=R.drawable.qhey_chocolate
        products.add(productFour)


        val productFive=Product(this);
        productFive.productName="The New Standard"
        productFive.productPrice= 4883
        productFive.productImageUrl=R.drawable.the_new_standard
        products.add(productFive)


        val productSix=Product(this);
        productSix.productName="Whey Dutch Chocolate Supplements"
        productSix.productPrice= 1788
        productSix.productImageUrl=R.drawable.whey_dutch_chocolate_supplements
        products.add(productSix)

        return products

    }
}
