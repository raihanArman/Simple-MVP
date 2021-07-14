package id.co.technicaltest.presenter

import id.co.technicaltest.db.DatabaseHelper
import id.co.technicaltest.model.Uang

class UangPresenter(
    val uangView: IUangView,
    db: DatabaseHelper
):  IUangPresenter{
    val dbHelper = db

    override fun insertData(uang: Uang) {
        if(dbHelper.insertUangMasuk(uang) > 0){
            uangView.setResult("Berhasil insert")
        }else{
            uangView.setResult("Gagal insert")
        }
    }

    override fun getAllData() {
        val listUang: List<Uang> = dbHelper.getAllUangMasuk()
        if(listUang.size > 0){
            uangView.setData(listUang)
        }else{
            uangView.setEmpty()
        }
    }

    override fun getLastId(): String {
        return dbHelper.getLastIdUangMasuk()
    }


}