package br.edu.ifsp.scl.ads.splitthebill.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import br.edu.ifsp.scl.ads.splitthebill.R
import br.edu.ifsp.scl.ads.splitthebill.adapter.MemberAdapter
import br.edu.ifsp.scl.ads.splitthebill.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.splitthebill.model.Member

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val memberList: MutableList<Member> = mutableListOf()

    private val memberAdapter: MemberAdapter by lazy {
        MemberAdapter(this, memberList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        supportActionBar?.subtitle = "Lista de Integrantes"

        amb.membersLv.adapter = memberAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}