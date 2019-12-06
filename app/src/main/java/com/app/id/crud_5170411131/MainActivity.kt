package com.app.id.crud_5170411131

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    var arrayList = ArrayList<fakultas>()

    var ID : String = ""
    var Kode : String = ""
    var Nama : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFakultas()

        lvFakultas.setOnItemLongClickListener { parent, view, position, id ->
            ID = (view.findViewById<View>(R.id.tvID) as TextView).text.toString()
            Kode = (view.findViewById<View>(R.id.tvKodeFakultas) as TextView).text.toString()
            Nama = (view.findViewById<View>(R.id.tvNamaFakultas) as TextView).text.toString()

            editFakultas()

            return@setOnItemLongClickListener true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(
            R.menu.add,
            menu
        )

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.getItemId() == R.id.addUser) {
            startActivity(Intent(this@MainActivity, add_fakultas::class.java))
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadFakultas(){
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.getAll_fakultas)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{

                override fun onResponse(response: JSONObject?) {
                    arrayList.clear()

                    val jsonArray = response?.optJSONArray("data")

                    if(jsonArray?.length() == 0){
                        loading.dismiss()
                        Toast.makeText(applicationContext,"fakultas Kosong", Toast.LENGTH_SHORT).show()
                        lvFakultas.requestLayout()
                    }

                    for(i in 0 until jsonArray?.length()!!){

                        val jsonObject = jsonArray?.optJSONObject(i)
                        arrayList.add(
                            fakultas(jsonObject.getInt("id_fakultas"),
                                jsonObject.getString("kode_fakultas"),
                                jsonObject.getString("nama_fakultas"))
                        )

                        if(jsonArray?.length() - 1 == i){

                            loading.dismiss()
                            val adapter = adapter(applicationContext,arrayList)
                            adapter.notifyDataSetChanged()
                            lvFakultas.adapter = adapter

                        }

                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun editFakultas(){
        val builder = AlertDialog.Builder(this@MainActivity)
        val inflater = this@MainActivity.layoutInflater
        val view = inflater.inflate(R.layout.popup_edit, null)
        builder.setTitle("Edit Fakultas")

        val id = view.findViewById<TextView> (R.id.tvID_popup)
        val kd = view.findViewById<EditText> (R.id.txtKodePopup)
        val nm = view.findViewById<EditText> (R.id.txtNamaFakultasPopup)

        id.setText(ID)
        kd.setText(Kode)
        nm.setText(Nama)

        builder.setPositiveButton("Ubah"){dialog, which ->

            AndroidNetworking.post(ApiEndPoint.updateFakultas)
                .addBodyParameter("id_fakultas",ID)
                .addBodyParameter("kode_fakultas",kd.text.toString())
                .addBodyParameter("nama_fakultas", nm.text.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {

                    override fun onResponse(response: JSONObject?) {

                        loadFakultas()

                        Toast.makeText(this@MainActivity, "sukses Ubah Fakultas", Toast.LENGTH_SHORT).show()

                        if(response?.getString("message")?.contains("successfully")!!){
                            this@MainActivity.finish()
                        }


                    }

                    override fun onError(anError: ANError?) {
                        Log.d("ONERROR",anError?.errorDetail?.toString())
                        Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()
                    }


                })
        }

        builder.setNegativeButton("Hapus"){dialog, which ->
            AndroidNetworking.post(ApiEndPoint.deleteFakultas)
                .addBodyParameter("id_fakultas",ID)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {

                    override fun onResponse(response: JSONObject?) {

                        loadFakultas()

                        Toast.makeText(this@MainActivity, "sukses hapus Fakultas", Toast.LENGTH_SHORT).show()

                        if(response?.getString("message")?.contains("successfully")!!){
                            this@MainActivity.finish()
                        }


                    }

                    override fun onError(anError: ANError?) {
                        Log.d("ONERROR",anError?.errorDetail?.toString())
                        Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()
                    }


                })
        }

        builder.setView(view)
        val al = builder.create()
        al.show()
    }

}
