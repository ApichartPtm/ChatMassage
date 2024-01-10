package com.example.chatapplication.views

import com.example.chatapplication.ChatMessage
import com.example.chatapplication.R
import com.example.chatapplication.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latest_message_row.view.*

class LatestMessageRow(val chatMessage: ChatMessage): Item<ViewHolder>(){

    var chartPartnerUser: User? = null

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_latest_message.text = chatMessage.text

        val chartPartnerId: String
        if (chatMessage.fromId == FirebaseAuth.getInstance().uid){
            chartPartnerId= chatMessage.toId
        }else{
            chartPartnerId = chatMessage.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chartPartnerId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)
                chartPartnerUser = p0.getValue(User::class.java)
                viewHolder.itemView.textView_username.text =chartPartnerUser?.username

                val targetImageView = viewHolder.itemView.imageView_latest_message
                Picasso.get().load(user?.profileImageUrl).into(targetImageView)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }
}
