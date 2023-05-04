package br.edu.ifsp.scl.ads.splitthebill.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.scl.ads.splitthebill.R
import br.edu.ifsp.scl.ads.splitthebill.adapter.MemberAdapter
import br.edu.ifsp.scl.ads.splitthebill.controller.MemberController
import br.edu.ifsp.scl.ads.splitthebill.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.splitthebill.model.Member

class MainActivity : BaseActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val memberList: MutableList<Member> = mutableListOf()

    private val memberAdapter: MemberAdapter by lazy {
        MemberAdapter(this, memberList)
    }

    private lateinit var marl: ActivityResultLauncher<Intent>

    private val memberController: MemberController by lazy {
        MemberController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        supportActionBar?.subtitle = "Lista de Integrantes"

        memberController.getMembers()
        amb.membersLv.adapter = memberAdapter

        marl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val member = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(EXTRA_MEMBER, Member::class.java)
                } else {
                    result.data?.getParcelableExtra<Member>(EXTRA_MEMBER)
                }

                member?.let {_member ->
                    val position = memberList.indexOfFirst { it.id == _member.id }
                    if (position != -1) {
                        memberList[position] = _member
                        memberController.editMember(_member)
                        Toast.makeText(this, "Membro editado com sucesso!", Toast.LENGTH_SHORT).show()
                    } else {
                        memberList.add(_member)
                        memberController.insertMember(_member)
                        Toast.makeText(this, "Membro adicionado com sucesso!", Toast.LENGTH_SHORT).show()
                    }
                    memberAdapter.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(amb.membersLv)

        amb.membersLv.setOnItemClickListener (object: AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long) {
                    val member = memberList[position]
                    val memberIntent = Intent(this@MainActivity, MemberActivity::class.java)
                    memberIntent.putExtra(EXTRA_MEMBER, member)
                    memberIntent.putExtra(EXTRA_VIEW_MEMBER, true)
                    marl.launch(memberIntent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addMemberMi -> {
                val bundle = Bundle()
                bundle.putParcelableArrayList(BUNDLE_LIST_MEMBER, ArrayList<Member>(memberList))
                val memberIntent = Intent(this, MemberActivity::class.java)
                memberIntent.putExtras(bundle)
                marl.launch(memberIntent)
                true
            }
            else -> false
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position
        val member = memberList[position]

        return when(item.itemId) {
            R.id.removeMemberMi -> {
                memberList.removeAt(position)
                memberController.removeMember(member)
                memberAdapter.notifyDataSetChanged()
                true
            }
            R.id.editMemberMi -> {
                val memberIntent = Intent(this, MemberActivity::class.java)
                memberIntent.putExtra(EXTRA_MEMBER, member)
                marl.launch(memberIntent)
                true
            }
            else -> false
        }
    }

    fun updateMembersList(_memberList: MutableList<Member>) {
        memberList.clear()
        memberList.addAll(_memberList)
        memberAdapter.notifyDataSetChanged()
    }
}