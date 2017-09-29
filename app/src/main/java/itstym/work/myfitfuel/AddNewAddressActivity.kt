package itstym.work.myfitfuel

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import itstym.work.myfitfuel.DataBase.DbHelper
import kotlinx.android.synthetic.main.activity_add_new_address.*

class AddNewAddressActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()

        proceed.setOnClickListener {

            if (checkValues()){

                DbHelper.getInstance(this@AddNewAddressActivity).addAddress(fullName.text.toString(),mobileNumber.text.toString(), completeAddress.text.toString(),addressState.text.toString())

                val intent: Intent = Intent(this@AddNewAddressActivity,CheckOutActivity::class.java)
                startActivity(intent)

            }
        }
    }

    private fun checkValues(): Boolean {

        if (fullName.text.toString().isNullOrEmpty()){
            fullName.error = "Enter Full Name"
            fullName.requestFocus()
            return false;
        }else if (mobileNumber.text.toString().isNullOrEmpty()){
            mobileNumber.error = "Enter mobile number"
            mobileNumber.requestFocus()
            return false;
        }else if (mobileNumber.text.toString().length!=10){
            mobileNumber.error = "Enter 10 digits mobile no"
            mobileNumber.requestFocus()
            return false;
        }else if (completeAddress.text.toString().isNullOrEmpty()){
            completeAddress.error = "Enter address with house no"
            completeAddress.requestFocus()
            return false
        }else if (addressState.text.toString().isNullOrEmpty()){
            addressState.error = "Enter the city name"
            addressState.requestFocus()
            return false
        }else
            return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_address)

        supportActionBar?.title="Address"

        if (DbHelper.getInstance(this@AddNewAddressActivity).getAddress(this@AddNewAddressActivity)!=null){

            val intent: Intent = Intent(this@AddNewAddressActivity,CheckOutActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
