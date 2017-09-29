package itstym.work.myfitfuel

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import itstym.work.myfitfuel.DataBase.DbHelper
import kotlinx.android.synthetic.main.activity_check_out.*

class CheckOutActivity : AppCompatActivity() {


    override fun onResume() {
        super.onResume()

        proceed.setOnClickListener {

            //remove all item from Cart
            DbHelper.getInstance(this@CheckOutActivity).removeAllProductFromCart();

            val intent: Intent = Intent(this@CheckOutActivity,ThanksActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        supportActionBar?.title="CheckOut"


        var userAddress=DbHelper.getInstance(this@CheckOutActivity).getAddress(this@CheckOutActivity)

        userName.text=userAddress?.name
        userPhoneNumber.text=userAddress?.cellPhone
        userAddressTextView.text= "${userAddress?.address} ${userAddress?.state}"

        proceed.text="Proceed to pay "+Constant.totalPaymentAmount

    }

}
