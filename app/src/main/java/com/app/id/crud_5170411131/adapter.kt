package com.app.id.crud_5170411131

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RelativeLayout
import android.widget.TextView

class adapter(context: Context, arrayListDetails:ArrayList<fakultas>) : BaseAdapter(){

    private val layoutInflater: LayoutInflater
    private val arrayListDetails:ArrayList<fakultas>

    init {
        this.layoutInflater = LayoutInflater.from(context)
        this.arrayListDetails=arrayListDetails
    }

    override fun getCount(): Int {
        return arrayListDetails.size
    }

    override fun getItem(position: Int): Any {
        return arrayListDetails.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val listRowHolder: ViewHolder
        if (convertView == null) {
            view = this.layoutInflater.inflate(R.layout.list_fakultas, parent, false)
            listRowHolder = ViewHolder(view)
            view.tag = listRowHolder
        } else {
            view = convertView
            listRowHolder = view.tag as ViewHolder
        }

        listRowHolder.tvId.text = arrayListDetails.get(position).id_fakultas.toString()
        listRowHolder.tvKode.text = arrayListDetails.get(position).kode_fakultas
        listRowHolder.tvNama.text = arrayListDetails.get(position).nama_fakultas
        return view
    }
}

private class ViewHolder(row: View?) {
    public val tvId : TextView
    public val tvKode : TextView
    public val tvNama : TextView

    init {
        this.tvId = row?.findViewById<TextView>(R.id.tvID) as TextView
        this.tvKode = row?.findViewById<TextView>(R.id.tvKodeFakultas) as TextView
        this.tvNama = row?.findViewById<TextView>(R.id.tvNamaFakultas) as TextView
    }
}