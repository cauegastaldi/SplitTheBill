package br.edu.ifsp.scl.ads.splitthebill.validator

import br.edu.ifsp.scl.ads.splitthebill.controller.MemberController
import br.edu.ifsp.scl.ads.splitthebill.databinding.ActivityMemberBinding
import br.edu.ifsp.scl.ads.splitthebill.model.Member
import java.util.*
import kotlin.collections.ArrayList

class MemberValidator {
    companion object {
        private val NAME_FORM_ERROR = "Nome deve ser preenchido"
        private val NAME_ALREADY_EXISTS_ERROR = "O nome escolhido já existe"
        private val MONEY_PAID_NOT_NUMBER_ERROR = "Dinheiro Pago deve ser um número"
        private val MONEY_PAID_NEGATIVE_NUMBER_ERROR = "Dinheiro Pago deve ser um número positivo"

        fun getFormErrors(amb: ActivityMemberBinding, memberList: ArrayList<Member>?): String {
            val formErrors = mutableListOf<String>()

            with (amb) {
                if (nameEt.text.isEmpty())
                    formErrors.add(NAME_FORM_ERROR)
                else if (!memberList.isNullOrEmpty() && !memberNameIsUnique(memberList, nameEt.text.toString()))
                    formErrors.add(NAME_ALREADY_EXISTS_ERROR)
                if (moneyPaidEt.text.toString().isNotEmpty()) {
                    if (moneyPaidEt.text.toString().toDoubleOrNull() == null)
                        formErrors.add(MONEY_PAID_NOT_NUMBER_ERROR)
                    else if (moneyPaidEt.text.toString().toDouble() < 0)
                        formErrors.add(MONEY_PAID_NEGATIVE_NUMBER_ERROR)
                }
            }

            return formErrors.joinToString(", ")
        }

        private fun memberNameIsUnique(memberList: ArrayList<Member>, name: String): Boolean {
            return memberList.firstOrNull { member -> member.name.lowercase() == name.lowercase() } == null
        }
    }
}