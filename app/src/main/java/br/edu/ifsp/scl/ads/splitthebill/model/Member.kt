package br.edu.ifsp.scl.ads.splitthebill.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Member(
    val name: String, var moneyPaid: Double = 0.0, var purchasedItems: String = ""
): Parcelable {

    fun getMoneyToReceive(members: MutableList<Member>): Double {
        var totalPaid = 0.0
        val membersQuantity = members.size

        members.forEach { member -> totalPaid += member.moneyPaid}
        val moneyPerMember = totalPaid / membersQuantity

        if (moneyPaid < moneyPerMember)
            return 0.0
        return totalPaid - moneyPerMember
    }

    fun getMoneyToPay(members: MutableList<Member>): Double {
        var totalPaid = 0.0
        val membersQuantity = members.size

        members.forEach { member -> totalPaid += member.moneyPaid}
        val moneyPerMember = totalPaid / membersQuantity

        if (moneyPaid > moneyPerMember)
            return 0.0
        return totalPaid - moneyPaid
    }
}