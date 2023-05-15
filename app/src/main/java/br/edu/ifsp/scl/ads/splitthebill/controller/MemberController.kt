package br.edu.ifsp.scl.ads.splitthebill.controller

import androidx.room.Room
import br.edu.ifsp.scl.ads.splitthebill.model.Member
import br.edu.ifsp.scl.ads.splitthebill.model.MemberDao
import br.edu.ifsp.scl.ads.splitthebill.model.MemberDaoRoom
import br.edu.ifsp.scl.ads.splitthebill.view.MainActivity
import kotlinx.coroutines.Runnable
import kotlin.concurrent.thread

class MemberController(private val mainActivity: MainActivity) {

    private val memberDaoImpl: MemberDao = Room.databaseBuilder(
        mainActivity, MemberDaoRoom::class.java, MemberDaoRoom.CONTACT_DATABASE_FILE
    ).build().getMemberDao()

    fun insertMember(member: Member) {
        val thread = Thread { memberDaoImpl.createMember(member) }
        thread.start()
        thread.join()
    }

    fun getMembers() {
        Thread {
            val members = memberDaoImpl.retrieveMembers()
            mainActivity.runOnUiThread {
                mainActivity.updateMembersList(members)
            }
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