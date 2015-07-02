package com.example.simpletodo.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletodo.R;
import com.example.simpletodo.activities.DetailsActivity;
import com.example.simpletodo.models.ToDoTitle;

import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    private List<ToDoTitle> titles;

    public ToDoListAdapter(List<ToDoTitle> titles) {
        this.titles = titles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_text, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(titles.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public void setData(List<ToDoTitle> titles){
        this.titles = titles;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        View parent;

        public ViewHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            parent.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.text);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(v.getContext(), DetailsActivity.class);
            intent.putExtra("TITLE", titles.get(position));
            v.getContext().startActivity(intent);
        }
    }

}
