package br.edu.ifsp.scl.ads.splitthebill.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Member::class], version = 1)
abstract class MemberDaoRoom: RoomDatabase() {
    companion object Constants {
        const val CONTACT_DATABASE_FILE = "members_room"
    }
    abstract fun getMemberDao(): MemberDao
}