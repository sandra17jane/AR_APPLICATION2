package com.example.ar_application.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ar_application.R;
import com.example.ar_application.TopicDetailActivity;
import com.example.ar_application.database.DatabaseHelper;
import com.example.ar_application.models.Task;
import com.example.ar_application.models.Topic;

import java.util.List;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.ViewHolder> {

    private List<Topic> topicsList;
    private Context context;
    private DatabaseHelper db; // ✅ DatabaseHelper Instance

    public TopicsAdapter(List<Topic> topicsList, Context context) {
        this.topicsList = topicsList;
        this.context = context;
        this.db = new DatabaseHelper(context); // ✅ Initialize DatabaseHelper
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Topic topic = topicsList.get(position);
        holder.topicTitle.setText(topic.getComponent());
        holder.topicDescription.setText(topic.getContent());

        // ✅ Correct ImageView ID
        if (topic.getImageUrl() != null && !topic.getImageUrl().isEmpty()) {
            Glide.with(context).load(topic.getImageUrl()).into(holder.topicImage);
        } else {
            holder.topicImage.setImageResource(R.drawable.placeholder_image);
        }

        // ✅ Click Listener to open TopicDetailsActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TopicDetailActivity.class);
            intent.putExtra("component", topic.getComponent());
            intent.putExtra("content", topic.getContent());
            intent.putExtra("imageUrl", topic.getImageUrl()); // ✅ Pass Image URL
            context.startActivity(intent);

            // ✅ Check if the topic already exists in the Task list before adding
            if (!db.isTaskAlreadyAdded(topic.getComponent())) {
                boolean success = db.insertTask(new Task(topic.getComponent(), topic.getContent(), topic.getImageUrl()));

                if (success) {
                    Toast.makeText(context, "Added to tasks!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to add!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Task already exists!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return topicsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView topicTitle, topicDescription; // ✅ Correct variable names
        ImageView topicImage; // ✅ Correct ImageView reference

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            topicTitle = itemView.findViewById(R.id.topicTitle); // ✅ Fix
            topicDescription = itemView.findViewById(R.id.topicDescription); // ✅ Fix
            topicImage = itemView.findViewById(R.id.topicImage); // ✅ Fix
        }

    }

}
