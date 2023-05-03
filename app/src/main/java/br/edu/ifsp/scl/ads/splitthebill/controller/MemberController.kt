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

    fun insertMember(member: Member) {
        Thread {
            memberDaoImpl.createMember(member)
        }.start()
    }
    fun getMember(name: String) {
        Thread {
            memberDaoImpl.retrieveMember(name)
        }.start()
    }

    fun getMembers() {
        Thread {
            mainActivity.updateMembersList(memberDaoImpl.retrieveMembers())
        }.start()
    }
    fun editMember(member: Member) {
        Thread {
            memberDaoImpl.updateMember(member)
        }.start()
    }
    fun removeMember(member: Member) {
        Thread {
            memberDaoImpl.deleteMember(member)
        }.start()
    }
}