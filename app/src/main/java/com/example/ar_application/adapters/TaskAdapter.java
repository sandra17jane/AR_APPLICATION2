package com.example.ar_application.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ar_application.R;
import com.example.ar_application.TopicDetailActivity;
import com.example.ar_application.database.DatabaseHelper;
import com.example.ar_application.models.Task;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> taskList;
    private Context context;
    private DatabaseHelper db; // ✅ DatabaseHelper Instance

    public TaskAdapter(List<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
        this.db = new DatabaseHelper(context); // ✅ Initialize DatabaseHelper
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskTitle.setText(task.getTitle());

        // ✅ Load Image Dynamically
        if (task.getImageUrl() != null && !task.getImageUrl().isEmpty()) {
            Glide.with(context).load(task.getImageUrl()).into(holder.taskImage);
        } else {
            holder.taskImage.setImageResource(R.drawable.placeholder_image);
        }

        // ✅ Open TopicDetailActivity when clicking on a Task
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TopicDetailActivity.class);
            intent.putExtra("component", task.getTitle());
            intent.putExtra("content", task.getDescription());
            intent.putExtra("imageUrl", task.getImageUrl());
            context.startActivity(intent);
        });

        // ✅ Remove Task Button Click
        holder.btnRemoveTask.setOnClickListener(v -> {
            boolean success = db.deleteTask(task.getTitle());
            if (success) {
                taskList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, taskList.size());
                Toast.makeText(context, "Task removed!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to remove task!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle;
        Button btnRemoveTask;
        ImageView taskImage; // ✅ Add ImageView

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            btnRemoveTask = itemView.findViewById(R.id.btnRemoveTask);
            taskImage = itemView.findViewById(R.id.taskImage); // ✅ Fix here
        }
    }

}
