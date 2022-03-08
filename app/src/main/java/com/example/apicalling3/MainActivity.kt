package com.example.apicalling3

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.apicalling3.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnPost.setOnClickListener {
            if (binding.idEdtName.text.toString().isEmpty() && binding.idEdtJob.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "Please enter both the values",
                    Toast.LENGTH_SHORT
                ).show()
            }
            postData(binding.idEdtName.text.toString(), binding.idEdtJob.text.toString());

        }
    }

    private fun postData(name: String, job: String) {

        binding.idLoadingPB.visibility = View.VISIBLE

        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitAPI = retrofit.create(RetrofitAPI::class.java)
        val modal = DataModal(name, job)
        val call: Call<DataModal?>? = retrofitAPI.createPost(modal)

        call!!.enqueue(object : Callback<DataModal?> {
            override fun onResponse(call: Call<DataModal?>, response: Response<DataModal?>) {
                Toast.makeText(this@MainActivity, "Data added to API", Toast.LENGTH_SHORT).show()
                binding.idLoadingPB.setVisibility(View.GONE)
                binding.idEdtName.text.clear()
                binding.idEdtJob.text.clear()
                val responseFromAPI = response.body()
                val responseString =
                    """ Response Code : ${response.code()} 
                        Name : ${responseFromAPI?.name} 
                        Job : ${responseFromAPI?.job} """.trimIndent()

                binding.idTVResponse.text = responseString
            }

            override fun onFailure(call: Call<DataModal?>, t: Throwable) {
                binding.idTVResponse!!.text
            }
        })
    }

}