package br.edu.ifsp.scl.ads.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.ads.splitthebill.R
import br.edu.ifsp.scl.ads.splitthebill.databinding.TileSplitBillBinding
import br.edu.ifsp.scl.ads.splitthebill.model.Member

class SplitBillAdapter(
    context: Context,
    private val memberList: MutableList<Member>
): ArrayAdapter<Member>(context, R.layout.tile_member, memberList) {

    private lateinit var tsbb: TileSplitBillBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val member: Member = memberList[position]

        var tileSplitBillView = convertView
        if (tileSplitBillView == null) {
            tsbb = TileSplitBillBinding.inflate(
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )
            tileSplitBillView = tsbb.root

            val tsbvh = TileSplitBillViewHolder(tsbb.nameTv, tsbb.moneyToReceiveTv, tsbb.moneyToPayTv)

            tileSplitBillView.tag = tsbvh
        }

        with (tileSplitBillView.tag as TileSplitBillViewHolder) {
            nameTv.text = member.name
            moneyToPayTv.text = context.getString(R.string.money_to_pay, member.getMoneyToPay(memberList))
            moneyToReceiveTv.text = context.getString(R.string.money_to_receive, member.getMoneyToReceive(memberList))
        }

        return tileSplitBillView
    }

    private data class TileSplitBillViewHolder (
        val nameTv: TextView,
        val moneyToReceiveTv: TextView,
        val moneyToPayTv: TextView
    )
}