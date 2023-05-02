package br.edu.ifsp.scl.ads.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.ads.splitthebill.R
import br.edu.ifsp.scl.ads.splitthebill.databinding.TileMemberBinding
import br.edu.ifsp.scl.ads.splitthebill.model.Member

class MemberAdapter(
    context: Context,
    private val memberList: MutableList<Member>
): ArrayAdapter<Member>(context, R.layout.tile_member, memberList) {

    private lateinit var tmb: TileMemberBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val member: Member = memberList[position]

        var tileMemberView = convertView
        if (tileMemberView == null) {
            tmb = TileMemberBinding.inflate(
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )
            tileMemberView = tmb.root

            val tmvh = TileMemberViewHolder(tmb.nameTv, tmb.moneyPaidTv)

            tileMemberView.tag = tmvh
        }

        with (tileMemberView.tag as TileMemberViewHolder) {
            nameTv.text = member.name
            moneyPaidTv.text = member.moneyPaid.toString()
        }

        return tileMemberView
    }

    private data class TileMemberViewHolder (
        val nameTv: TextView,
        val moneyPaidTv: TextView
    )
}