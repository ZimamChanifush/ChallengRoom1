package com.example.challengroom1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.challengroom1.Room.Constant
import com.example.challengroom1.Room.dbsmksa
import com.example.challengroom1.Room.tbsiswa
import com.example.challengroom1.Room.tbsiswaDao
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    private val db by lazy { dbsmksa(this) }
    private var tbsisnis: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        tombolperintah()
        setupView()
        tbsisnis = intent.getIntExtra("intent_nis", tbsisnis)
        Toast.makeText(this, tbsisnis.toString(), Toast.LENGTH_SHORT).show()
    }

    fun setupView() {
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {

            }

            Constant.TYPE_READ -> {
                btnsimpan.visibility = View.GONE
                btnupdate.visibility = View.GONE
                ETNis.visibility = View.GONE
                tampilsiswa()
            }
            Constant.TYPE_UPDATE -> {
                btnsimpan.visibility = View.GONE
                ETNis.visibility = View.GONE
                tampilsiswa()
            }
        }
    }

    fun tombolperintah() {
        btnsimpan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbsiswaDao().addtbsiswa(
                    tbsiswa(
                        ETNis.text.toString().toInt(),
                        ETNama.text.toString(),
                        ETKelas.text.toString(),
                        ETAlamat.text.toString())
                )
                finish()
            }
        }
        btnupdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbsiswaDao().Updatetbsiswa(
                    tbsiswa(tbsisnis,
                        ETNama.text.toString(),
                        ETKelas.text.toString(),
                        ETAlamat.text.toString())
                )
            }
        }
    }
            fun tampilsiswa() {
                tbsisnis = intent.getIntExtra("tbsis_nis", 0)
                CoroutineScope(Dispatchers.IO).launch {
                    val siswa = db.tbsiswaDao().tampilId(tbsisnis)[0]
                    ETNama.setText(siswa.nama)
                    ETKelas.setText(siswa.kelas)
                    ETAlamat.setText(siswa.alamat)
                }
            }
}


