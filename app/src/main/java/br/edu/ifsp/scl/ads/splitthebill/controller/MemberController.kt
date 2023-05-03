package br.edu.ifsp.scl.ads.splitthebill.controller

import androidx.room.Room
import br.edu.ifsp.scl.ads.splitthebill.model.Member
import br.edu.ifsp.scl.ads.splitthebill.model.MemberDao
import br.edu.ifsp.scl.ads.splitthebill.model.MemberDaoRoom
import br.edu.ifsp.scl.ads.splitthebill.view.MainActivity
import kotlin.concurrent.thread

class MemberController(private val mainActivity: MainActivity) {

    private val memberDaoImpl: MemberDao = Room.databaseBuilder(
        mainActivity, MemberDaoRoom::class.java, MemberDaoRoom.CONTACT_DATABASE_FILE
    ).build().getMemberDao()

    fun insertMember(member: Member) = memberDaoImpl.createMember(member)
    fun getMember(name: String) = memberDaoImpl.retrieveMember(name)
    fun getMembers() {
        thread {
            mainActivity.updateMembersList(memberDaoImpl.retrieveMembers())
        }
    }
    fun editMember(member: Member) = memberDaoImpl.updateMember(member)
    fun removeMember(member: Member) = memberDaoImpl.deleteMember(member)
}