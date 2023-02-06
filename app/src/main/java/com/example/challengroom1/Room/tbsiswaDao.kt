package com.example.challengroom1.Room

import androidx.room.*

@Dao
interface tbsiswaDao {
    @Insert
    fun addtbsiswa(tbsis : tbsiswa)

    @Update
    fun Updatetbsiswa(tbsis : tbsiswa)

    @Delete
    fun deltbsiswa(tbsis : tbsiswa)

    @Query ("SELECT * FROM tbsiswa")
    fun tampilsemua(): List<tbsiswa>

    @Query("SELECT* FROM tbsiswa WHERE nis =:tbsis_nis")
    fun tampilId(tbsis_nis:Int): List<tbsiswa>




}