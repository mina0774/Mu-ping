package com.google.sample.cloudvision;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.ViewHolder> {
    Context context;
    List<SongItem> items;
    int item_layout;


    public RecommendListAdapter(Context context, List<SongItem> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    // 리스너 객체 참조를 저장하는 변수
    private RecommendListAdapter.OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(RecommendListAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }


    @Override
    public RecommendListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_item, parent, false);
        return new RecommendListAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(RecommendListAdapter.ViewHolder holder, int position) {
        final SongItem item = items.get(position);

        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.onItemClick(v, position);
            }
        });


        holder.song_title_tv.setText(item.getTitle());
        holder.song_performer_tv.setText(item.getPerformer());
        holder.song_genre_tv.setText(item.getGenre());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView song_title_tv;
        private TextView song_performer_tv;
        private TextView song_genre_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            song_title_tv = (TextView) itemView.findViewById(R.id.song_title);
            song_performer_tv = (TextView) itemView.findViewById(R.id.song_performer);
            song_genre_tv = (TextView) itemView.findViewById(R.id.song_genre);

        }
    }

}
