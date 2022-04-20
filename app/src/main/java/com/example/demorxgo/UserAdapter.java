package com.example.demorxgo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.demorxgo.ContactD;
import com.example.demorxgo.Chat;
import com.example.demorxgo.User;
import com.example.demorxgo.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<patients> mUsers;
    //private boolean ischat;

    //String theLastMessage;

    public UserAdapter(Context mContext, ArrayList<patients> mUsers){
        this.mUsers = mUsers;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        patients user = mUsers.get(position);
        holder.username.setText(user.getFirstName());

        //holder.itemView.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Intent intent = new Intent(mContext, ChatMessage.class);
        //        intent.putExtra("userid", user.getId());
        //        mContext.startActivity(intent);
        //    }
        //});
    }

    @Override
    public int getItemCount() {

        return mUsers.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        //private TextView last_msg;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            //last_msg = itemView.findViewById(R.id.last_msg);
        }
    }

    //check for last message
   //private void lastMessage(final String userid, final TextView last_msg){
   //    theLastMessage = "default";
   //    final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
   //    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

   //    reference.addValueEventListener(new ValueEventListener() {
   //        @Override
   //        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
   //            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
   //                Chat chat = snapshot.getValue(Chat.class);
   //                if (firebaseUser != null && chat != null) {
   //                    if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
   //                            chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
   //                        theLastMessage = chat.getMessage();
   //                    }
   //                }
   //            }

   //            switch (theLastMessage){
   //                case  "default":
   //                    last_msg.setText("No Message");
   //                    break;

   //                default:
   //                    last_msg.setText(theLastMessage);
   //                    break;
   //            }

   //            theLastMessage = "default";
   //        }
//
    //        @Override
    //        public void onCancelled(@NonNull DatabaseError databaseError) {
//
    //        }
    //    });
    //}
}
