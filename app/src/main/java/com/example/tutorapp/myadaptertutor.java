package com.example.tutorapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadaptertutor extends FirebaseRecyclerAdapter<User,myadaptertutor.myviewholder>
{
    public myadaptertutor(@NonNull FirebaseRecyclerOptions<User> option) {
        super(option);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder myviewholder, int i, @NonNull User user) {

        myviewholder.name.setText(user.getFirtName());
        myviewholder.course.setText(user.getLastName());
        myviewholder.email.setText(user.getEmail());
   //     myviewholder.bio.setText(user.getBio());
        //   Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_singlerowtutor,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView name,course,email, bio;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            //   img=(CircleImageView)itemView.findViewById(R.id.img1);
            name=(TextView)itemView.findViewById(R.id.nametext);
            course=(TextView)itemView.findViewById(R.id.coursetext);
            email=(TextView)itemView.findViewById(R.id.emailtext);
            bio = (TextView)itemView.findViewById(R.id.biotext);
        }
    }
}
