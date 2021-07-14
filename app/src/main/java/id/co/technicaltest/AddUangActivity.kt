package id.co.technicaltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import id.co.technicaltest.databinding.ActivityAddUangBinding
import id.co.technicaltest.db.DatabaseHelper
import id.co.technicaltest.model.Uang
import id.co.technicaltest.presenter.IUangView
import id.co.technicaltest.presenter.UangPresenter
import java.util.*

class AddUangActivity : AppCompatActivity(), IUangView {

    private lateinit var dataBinding: ActivityAddUangBinding
    private var presenter: UangPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_uang)

        val db= DatabaseHelper(this)
        presenter = UangPresenter(this, db)

        dataBinding.btnAction.setOnClickListener {
            insertData()
        }

    }

    private fun insertData() {
        val uang = Uang(generateId(), dataBinding.etTerima.text.toString(), dataBinding.etKeterangan.text.toString(), dataBinding.etJumlah.text.toString())
        presenter?.insertData(uang)
        finish()
    }

    private fun generateId(): String {
        val id = presenter?.getLastId()
        val calendar = Calendar.getInstance()
        val lastDate = DateFormat.format("ddMMyy", calendar.time).toString()
        if(!id.equals("")){
            val lastIdFromDb = id?.substring(3, 9)
            if(lastDate.equals(lastIdFromDb)) {
                var nextId = id?.replace("UM/$lastIdFromDb/", "")?.toInt()
                return "UM/$lastIdFromDb/${nextId!! + 1}"
            }else{
                return "UM/$lastDate/1"
            }
        }else{
            return "UM/$lastDate/1"
        }
    }

    override fun setData(listUang: List<Uang>) {
    }

    override fun setEmpty() {
    }

    override fun setResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoad(isLoad: Boolean) {
    }
}