package com.example.medi_app;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.medi_app.HomepageActivity;
import com.example.medi_app.R;

import java.util.List;


public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> { // recyclerview adapter for homepage

    private List<String> the_data;
    private List<Integer> the_image;
    private Context the_context;
    private LayoutInflater the_inflater;
    private ItemClickListener mClickListener;

    // pass data into the constructor
    RecyclerviewAdapter(HomepageActivity context, List<String> data, List<Integer> image) {
        this.the_inflater = LayoutInflater.from(context);
        this.the_data = data;
        this.the_image = image;
        this.the_context =context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = the_inflater.inflate(R.layout.cardrow, parent, false);
        return new ViewHolder(view);
    }

    // bind data to the TextView for each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String wonder = the_data.get(position);
        Integer image_url= the_image.get(position);
        holder.recycleview_textview.setText(wonder);
        holder.recycleview_imgview.setImageResource(image_url);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return the_data.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recycleview_textview;
        ImageView recycleview_imgview;

        ViewHolder(View itemView) {
            super(itemView);
            recycleview_textview = itemView.findViewById(R.id.homepage_textview);
            recycleview_imgview = itemView.findViewById(R.id.homepage_Imageview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // get data at click position
    String getItem(int id) {
        return the_data.get(id);
    }



    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

