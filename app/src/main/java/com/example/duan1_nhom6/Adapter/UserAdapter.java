package com.example.duan1_nhom6.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom6.MessageActivity;
import com.example.duan1_nhom6.Model.User;
import com.example.duan1_nhom6.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>{

    private Context context;
    private List<User> mUsers;
    private boolean isChat;

    public UserAdapter(Context context, List<User> mUsers,boolean isChat) {
        this.context = context;
        this.mUsers = mUsers;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent,false);

        return new UserAdapter.UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        final User user = mUsers.get(position);
        holder.username.setText(user.getFullname());
        holder.numberphone.setText(user.getNumberphone());

        if(user.getImageURL().equals("default")){
            holder.image_user.setImageResource(R.drawable.ic_user);
        }else{
            Picasso.get()
                    .load(user.getImageURL())
                    .into(holder.image_user);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, MessageActivity.class);
                i.putExtra("phoneid",user.getId());
                context.startActivity(i);

                ((Activity)context).overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });


        //status check
        if(isChat){
            if(user.getStatus().equals("online")){
                holder.imageViewON.setVisibility(View.VISIBLE);
                holder.imageViewOFF.setVisibility(View.GONE);

            }else{
                holder.imageViewON.setVisibility(View.GONE);
                holder.imageViewOFF.setVisibility(View.VISIBLE);
            }
        }else{

            holder.imageViewON.setVisibility(View.GONE);
            holder.imageViewOFF.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public class UserHolder extends RecyclerView.ViewHolder{
        public TextView username,numberphone;
        public ImageView image_user;
        public ImageView imageViewON;
        public ImageView imageViewOFF;


        public UserHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.fullname);
            numberphone = itemView.findViewById(R.id.numberPhone);
            image_user = itemView.findViewById(R.id.image_user);
            imageViewON = itemView.findViewById(R.id.image_on);
            imageViewOFF = itemView.findViewById(R.id.image_off);

        }
    }

}
