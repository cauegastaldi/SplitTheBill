package br.edu.ifsp.scl.ads.splitthebill.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.core.view.isVisible
import br.edu.ifsp.scl.ads.splitthebill.R
import br.edu.ifsp.scl.ads.splitthebill.databinding.ActivityMemberBinding
import br.edu.ifsp.scl.ads.splitthebill.model.Member
import br.edu.ifsp.scl.ads.splitthebill.validator.MemberValidator

class MemberActivity: BaseActivity() {
    private val amb: ActivityMemberBinding by lazy {
        ActivityMemberBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        supportActionBar?.subtitle = getString(R.string.member_info)

        val receivedMember = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_MEMBER, Member::class.java)
        } else {
            intent.getParcelableExtra<Member>(EXTRA_MEMBER)
        }
        receivedMember?.let { _received_member ->
            with (amb) {
                with (_received_member) {
                    nameEt.setText(name)
                    moneyPaidEt.setText(moneyPaid.toString())
                    purchasedItemsEt.setText(purchasedItems)
                }
            }
            val viewMember = intent.getBooleanExtra(EXTRA_VIEW_MEMBER, false)
            with (amb) {
                nameEt.isEnabled = false
                moneyPaidEt.isEnabled = !viewMember
                purchasedItemsEt.isEnabled = !viewMember
                saveBt.isVisible = !viewMember
            }
        }

        amb.saveBt.setOnClickListener {
            val memberList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.extras?.getParcelableArrayList(BUNDLE_LIST_MEMBER, Member::class.java)
            } else {
                intent.extras?.getParcelableArrayList<Member>(BUNDLE_LIST_MEMBER)
            }
            val formErrors = MemberValidator.getFormErrors(amb, memberList)

            if (formErrors.isEmpty()) {
                with (amb) {
                    val name = nameEt.text.toString().trim()
                    val moneyPaid = if (moneyPaidEt.text.toString().toDoubleOrNull() != null) {
                        moneyPaidEt.text.toString().toDouble()
                    } else {
                        0.0
                    }
                    val purchasedItems = purchasedItemsEt.text.toString().ifEmpty {
                        PURCHASED_ITEM_DEFAULT_VALUE
                    }
                    val member = Member(
                        id = receivedMember?.id,
                        name = name,
                        moneyPaid = moneyPaid,
                        purchasedItems = purchasedItems
                    )

                    val resultIntent = Intent()
                    resultIntent.putExtra(EXTRA_MEMBER, member)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            } else {
                Toast.makeText(this, formErrors, Toast.LENGTH_SHORT).show()
            }
        }
    }
}