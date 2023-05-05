package br.edu.ifsp.scl.ads.splitthebill.view

import android.os.Build
import android.os.Bundle
import br.edu.ifsp.scl.ads.splitthebill.adapter.SplitBillAdapter
import br.edu.ifsp.scl.ads.splitthebill.databinding.ActivitySplitBillBinding
import br.edu.ifsp.scl.ads.splitthebill.model.Member

class SplitBillActivity: BaseActivity() {

    private val asbb: ActivitySplitBillBinding by lazy {
        ActivitySplitBillBinding.inflate(layoutInflater)
    }

    private lateinit var memberList: MutableList<Member>

    private val splitBillAdapter: SplitBillAdapter by lazy {
        SplitBillAdapter(this, memberList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(asbb.root)
        supportActionBar?.subtitle = "Rachar a conta"

        memberList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelableArrayList(BUNDLE_LIST_MEMBER, Member::class.java) as MutableList<Member>
        } else {
            intent.extras?.getParcelableArrayList<Member>(BUNDLE_LIST_MEMBER) as MutableList<Member>
        }

        asbb.membersLv.adapter = splitBillAdapter
    }
}