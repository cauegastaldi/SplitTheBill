package br.edu.ifsp.scl.ads.splitthebill.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class Member(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @NonNull val name: String,
    var moneyPaid: Double,
    var purchasedItems: String
): Parcelable {

    fun getMoneyToReceive(members: MutableList<Member>): Double {
        var totalPaid = 0.0
        val membersQuantity = members.size

        members.forEach { member -> totalPaid += member.moneyPaid}
        val moneyPerMember = totalPaid / membersQuantity

        if (moneyPaid < moneyPerMember)
            return 0.0
        return moneyPaid - moneyPerMember
    }

    fun getMoneyToPay(members: MutableList<Member>): Double {
        var totalPaid = 0.0
        val membersQuantity = members.size

        members.forEach { member -> totalPaid += member.moneyPaid}
        val moneyPerMember = totalPaid / membersQuantity

        if (moneyPaid > moneyPerMember)
            return 0.0
        return moneyPerMember - moneyPaid
    }
}