package com.example.challengroom1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengroom1.Room.Constant
import com.example.challengroom1.Room.dbsmksa
import com.example.challengroom1.Room.tbsiswa
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { dbsmksa(this) }
    lateinit var siswaAdapter: SiswaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        halEdit()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
       loaddata()
        }
        fun loaddata(){
            CoroutineScope(Dispatchers.IO).launch {
                val siswa = db.tbsiswaDao().tampilsemua()
                Log.d("MainActivity","dbResponse:$siswa")
                withContext(Dispatchers.Main){
                    siswaAdapter.setdata(siswa)
                }

        }
    }
    private fun halEdit(){
        btnInput.setOnClickListener {
           intenEdit(0,Constant.TYPE_CREATE)
        }

    }

    fun intenEdit(tbsisnis:Int,intentType: Int){
        startActivity(Intent(applicationContext,EditActivity::class.java)
            .putExtra("tbsis_nis",tbsisnis)
            .putExtra("intent_Type",intentType)
        )
    }
    fun setupRecyclerView(){
        siswaAdapter = SiswaAdapter(arrayListOf(),object :SiswaAdapter.OnAdapterListener{
            override fun onClick(tbsis: tbsiswa) {
                intenEdit(tbsis.nis,Constant.TYPE_READ)
            }
            
            override fun onUpdate(tbsis : tbsiswa){
                intenEdit(tbsis.nis,Constant.TYPE_UPDATE)
            }

            override fun onDelete(tbsis: tbsiswa) {
               deleteAlert(tbsis)
            }

        })
        //id recyclerview
        Listdatasiswa.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = siswaAdapter
        }
    }
    private fun deleteAlert(tbsis : tbsiswa){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus")
            setMessage("Yakin Mau Hapus ${tbsis.nama}?")
            setNegativeButton("Batal") {dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") {dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbsiswaDao().deltbsiswa(tbsis)
                    dialogInterface.dismiss()
                    loaddata()
                }
            }
        }
        dialog.show()
    }
}