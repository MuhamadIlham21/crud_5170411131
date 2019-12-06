package com.app.id.crud_5170411131

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_add_fakultas.*
import org.json.JSONObject

class add_fakultas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_fakultas)

        btnInput.setOnClickListener {
            addFakultas()
            startActivity(Intent(this@add_fakultas, MainActivity::class.java))
        }
    }

    fun addFakultas(){


        AndroidNetworking.post(ApiEndPoint.addFakultas)
            .addBodyParameter("kode_fakultas", txtKodeFakultas.text.toString())
            .addBodyParameter("nama_fakultas",txtNamaFakultas.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {


                    Toast.makeText(this@add_fakultas, "Tambah Fakultas sukses", Toast.LENGTH_SHORT).show()

                    if(response?.getString("message")?.contains("successfully")!!){

                        this@add_fakultas.finish()
                    }


                }

                override fun onError(anError: ANError?) {
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()
                }


            })

        }

}
