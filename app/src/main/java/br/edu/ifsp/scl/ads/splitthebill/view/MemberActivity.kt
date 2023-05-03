package br.edu.ifsp.scl.ads.splitthebill.view

import android.os.Bundle
import br.edu.ifsp.scl.ads.splitthebill.R
import br.edu.ifsp.scl.ads.splitthebill.databinding.ActivityMemberBinding

class MemberActivity: BaseActivity() {
    private val amb: ActivityMemberBinding by lazy {
        ActivityMemberBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        supportActionBar?.subtitle = getString(R.string.member_info)

    }
}