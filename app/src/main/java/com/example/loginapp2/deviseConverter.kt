package com.example.loginapp2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class deviseConverterActivity : AppCompatActivity() {
    private lateinit var spinnerFrom: Spinner
    var spinnerTo: Spinner? = null
    var convertButton: Button? = null
    var convertFromText: EditText? = null
    var convertToText: EditText? = null
    var baseCurrency = "EUR"
    var convertedToCurrency = "USD"
    var conversionRate = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devise_converter)

        initFromView()
        convertButtonSetup()
        spinnerSetup()
        textChanged()
    }

    private fun initFromView(){
        spinnerFrom = findViewById<View>(R.id.spinnerFrom) as Spinner
        spinnerTo = findViewById<View>(R.id.spinnerTo) as Spinner
        convertButton = findViewById<View>(R.id.convertDevise) as Button
        convertFromText = findViewById<View>(R.id.inputDeviseFrom) as EditText
        convertToText = findViewById<View>(R.id.inputDeviseTo) as EditText
    }


    private fun convertButtonSetup(){
        convertButton!!.setOnClickListener {
            val stringDeviseValue = convertFromText!!.text.toString().trim { it <= ' ' }
            if(stringDeviseValue != ""){
                val deviseValue = Integer.valueOf(stringDeviseValue)
                Log.d("Main", deviseValue.toString())
            }
            else{
                Log.d("Main", "no value to convert")
            }
        }
    }

    private fun spinnerSetup(){
        ArrayAdapter.createFromResource(
                this,
                R.array.devisesFrom,
                android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFrom.adapter = adapter
        }

        ArrayAdapter.createFromResource(
                this,
                R.array.devisesTo,
                android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTo?.adapter = adapter
        }

        spinnerFrom.onItemSelectedListener = (object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                baseCurrency = parent?.getItemAtPosition(position).toString()
                conversionResult()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("Main","nothing selected in spinner From")
            }

        })

        spinnerTo?.onItemSelectedListener = (object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertedToCurrency = parent?.getItemAtPosition(position).toString()
                conversionResult()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("Main","nothing selected in spinner To")
            }

        })
    }

    private fun textChanged(){
        convertFromText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                try{
                    conversionResult()
                }
                catch(e: Exception){
                    Log.e("Main", "$e")
                }
            }

        })
    }

    private fun conversionResult(){
        val APIurl = "https://api.ratesapi.io/api/latest?base=$baseCurrency&symbols=$convertedToCurrency"

        if (baseCurrency === convertedToCurrency) {
            Toast.makeText(this, "cannot convert the same currency", Toast.LENGTH_SHORT).show()
        }
        else {
            GlobalScope.launch(Dispatchers.IO){
                try{
                    //read text from url
                    val apiResult = URL(APIurl).readText()
                    //get the json from the api
                    val jsonObject = JSONObject(apiResult)
                    //get the rates data from the json
                    //then convert it to float
                    conversionRate = jsonObject.getJSONObject("rates").getString(convertedToCurrency).toFloat()

                    Log.d("Main", apiResult)

                    withContext(Dispatchers.Main){
                        //convert the text of conversion input to string then to float and back to string
                        val text = ((convertFromText?.text.toString().toFloat()) * conversionRate).toString()
                        convertToText?.setText(text)
                    }
                }
                catch (e:Exception){

                }
            }
        }
    }
}