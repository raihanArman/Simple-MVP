package id.co.technicaltest.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import id.co.technicaltest.model.Rekening
import id.co.technicaltest.model.Uang
import id.co.technicaltest.utils.Constant.ATAS_NAMA
import id.co.technicaltest.utils.Constant.DB_NAME
import id.co.technicaltest.utils.Constant.ID_REKENING
import id.co.technicaltest.utils.Constant.ID_UANG_MASUK
import id.co.technicaltest.utils.Constant.JUMLAH
import id.co.technicaltest.utils.Constant.KETERANGAN
import id.co.technicaltest.utils.Constant.NAMA_BANK
import id.co.technicaltest.utils.Constant.NOMOR
import id.co.technicaltest.utils.Constant.NOMOR_REKENING
import id.co.technicaltest.utils.Constant.TANGGAL
import id.co.technicaltest.utils.Constant.TB_REKENING
import id.co.technicaltest.utils.Constant.TB_UANG_MASUK
import id.co.technicaltest.utils.Constant.TERIMA_DARI

class DatabaseHelper(
    context: Context,
): SQLiteOpenHelper(context, DB_NAME, null, 1) {

    companion object{
        const val TAG = "DatabaseHelper"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TB_UANG_MASUK (" +
                "$ID_UANG_MASUK VARCHAR(50) PRIMARY KEY, " +
                "$TERIMA_DARI VARCHAR(100), " +
                "$KETERANGAN VARCHAR(255), " +
                "$JUMLAH VARCHAR(30))"
        db?.execSQL(query)
    }

    fun insertRekening(rekening: Rekening): Long{
        val db: SQLiteDatabase = this.writableDatabase
        var content = ContentValues()
        content.put(NAMA_BANK, rekening.namabank)
        content.put(NOMOR_REKENING, rekening.nomorRekening)
        content.put(ATAS_NAMA, rekening.atasNama)

        return db.insert(TB_REKENING, null, content)
    }

    fun insertUangMasuk(uang: Uang): Long{
        val db: SQLiteDatabase = this.writableDatabase
        var content = ContentValues()
        content.put(ID_UANG_MASUK, uang.uangMasukId)
        content.put(TERIMA_DARI, uang.terimaDari)
        content.put(KETERANGAN, uang.keterangan)
        content.put(JUMLAH, uang.jumlah)

        return db.insert(TB_UANG_MASUK, null, content)
    }

    fun getAllUangMasuk(): List<Uang>{
        var listUang = mutableListOf<Uang>()
        val db: SQLiteDatabase = this.readableDatabase
        val query = "SELECT * FROM $TB_UANG_MASUK"
        val cursor: Cursor = db.rawQuery(query, null)

        if(cursor.moveToFirst()){
            do{
                val uang = Uang(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                )
                listUang.add(uang)
            }while(cursor.moveToNext())
        }

        return listUang
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val upgradeAddColumnTanggal = "ALTER TABLE $TB_UANG_MASUK ADD COLUMN $TANGGAL VARCHAR(20)"
        val upgradeAddColumnNomor = "ALTER TABLE $TB_UANG_MASUK ADD COLUMN $NOMOR VARCHAR(20)"
        val addTableRekening = "CREATE TABLE $TB_REKENING (" +
                "$ID_REKENING INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$NAMA_BANK VARCHAR(100), " +
                "$NOMOR_REKENING VARCHAR(255), " +
                "$ATAS_NAMA VARCHAR(30))"
        val upgradeAddColumnRekeningId = "ALTER TABLE $TB_UANG_MASUK ADD COLUMN $ID_REKENING VARCHAR(20)"

        if (newVersion > oldVersion) {
            db!!.execSQL(upgradeAddColumnTanggal)
            db!!.execSQL(upgradeAddColumnNomor)
            db!!.execSQL(addTableRekening)
            db!!.execSQL(upgradeAddColumnRekeningId)
        }
    }

    fun getLastIdUangMasuk(): String{
        var lastId = ""

        val db: SQLiteDatabase = this.readableDatabase
        val query = "SELECT * FROM $TB_UANG_MASUK"
        val cursor: Cursor = db.rawQuery(query, null)
        if(cursor.count > 0){
            if(cursor.moveToLast()) {
                lastId = cursor.getString(0)
                Log.d(TAG, "getLastIdUangMasuk: $lastId")
            }
        }

        return lastId
    }

}