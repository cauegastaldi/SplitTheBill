package br.edu.ifsp.scl.ads.splitthebill.model

import androidx.room.*

@Dao
interface MemberDao {
    @Insert
    fun createMember(member: Member)
    @Query("SELECT * FROM Member WHERE name = :name")
    fun retrieveMember(name: String): Member?
    @Query("SELECT * FROM Member")
    fun retrieveMembers(): MutableList<Member>
    @Update
    fun updateMember(member: Member): Int
    @Delete
    fun deleteMember(member: Member): Int
}