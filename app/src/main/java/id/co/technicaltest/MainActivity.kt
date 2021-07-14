package id.co.technicaltest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.technicaltest.adapter.UangAdapter
import id.co.technicaltest.databinding.ActivityMainBinding
import id.co.technicaltest.db.DatabaseHelper
import id.co.technicaltest.model.Uang
import id.co.technicaltest.presenter.IUangView
import id.co.technicaltest.presenter.UangPresenter

class MainActivity : AppCompatActivity(), IUangView {

    private lateinit var dataBinding: ActivityMainBinding
    private val adapter : UangAdapter by lazy {
        UangAdapter()
    }

    private var presenter: UangPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val db= DatabaseHelper(this)
        presenter = UangPresenter(this, db)

        setupAdapter()

        dataBinding.fbAdd.setOnClickListener {
            val intent = Intent(this, AddUangActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupAdapter() {
        dataBinding.rvUang.layoutManager = LinearLayoutManager(this)
        dataBinding.rvUang.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        presenter?.getAllData()
    }


    override fun setData(listUang: List<Uang>) {
        adapter.setListUang(listUang)
    }

    override fun setEmpty() {
    }

    override fun setResult(message: String) {
    }

    override fun onLoad(isLoad: Boolean) {
    }
}