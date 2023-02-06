package com.example.challengroom1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challengroom1.Room.tbsiswa
import kotlinx.android.synthetic.main.activity_siswa_adapter.view.*

class SiswaAdapter (private var siswa: ArrayList<tbsiswa>, private val listener:OnAdapterListener): RecyclerView.Adapter<SiswaAdapter.SiswaViewHolder>(){
    class SiswaViewHolder (val view: View): RecyclerView.ViewHolder(view)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiswaViewHolder {
        return SiswaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_siswa_adapter,parent,false)
        )
    }

    override fun onBindViewHolder(holder: SiswaViewHolder, position: Int) {
val sis = siswa[position]
    holder.view.t_nama.text = sis.nama
        holder.view.t_nama.setOnClickListener { listener.onClick(sis) }
        holder.view.imageEdit.setOnClickListener{listener.onUpdate(sis)}
        holder.view.imageDelete.setOnClickListener{listener.onDelete(sis)}
    }


    override fun getItemCount() = siswa.size

    fun setdata(list: List<tbsiswa>){
        siswa.clear()
        siswa.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener{
        fun onClick(tbsis: tbsiswa)
        fun onUpdate(tbsis: tbsiswa)
        fun onDelete(tbsis: tbsiswa)
    }

}
