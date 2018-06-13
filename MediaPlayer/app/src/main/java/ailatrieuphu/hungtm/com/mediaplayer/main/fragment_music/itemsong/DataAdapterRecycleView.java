package ailatrieuphu.hungtm.com.mediaplayer.main.fragment_music.itemsong;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import ailatrieuphu.hungtm.com.mediaplayer.R;

public class DataAdapterRecycleView extends RecyclerView.Adapter<DataAdapterRecycleView.RecycleViewHolder>{
    private IDataAdapter iDataAdapter;
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("mm:ss");
    public DataAdapterRecycleView(IDataAdapter iDataAdapter) {
        this.iDataAdapter=iDataAdapter;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_music,parent,false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, final int position) {
        ItemData itemData=iDataAdapter.getItem(position);
        holder.tvNameSong.setText(itemData.getNameSong());
       holder.tvTime.setText(simpleDateFormat.format(new Date(itemData.getTime())));
        holder.tvNameSinger.setText(itemData.getNameSinger());
//        if(itemData.getPathImage()==null){
//            holder.ivSinger.setImageResource(R.drawable.not_nhac);
//        }
//        else {
//            Bitmap bm= BitmapFactory.decodeFile(itemData.getPathImage());
//            holder.ivSinger.setImageBitmap(bm);
//        }
        if(itemData.getPathImage()!=null){
            Picasso.with(holder.itemView.getContext()).load(new File(itemData.getPathImage())).into(holder.ivSinger);
        }
        else {
            Picasso.with(holder.itemView.getContext()).load(R.drawable.not_nhac).into(holder.ivSinger);
        }
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iDataAdapter.clickItem(position);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return iDataAdapter.getCount();
    }
    public  interface IDataAdapter{
        void clickItem(int position);
        int getCount();
        ItemData getItem(int position);
    }
    public class RecycleViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameSong,tvNameSinger,tvTime;
        ImageView ivSinger;
        private RelativeLayout line;
        public RecycleViewHolder(View itemView) {
            super(itemView);
            tvNameSong=itemView.findViewById(R.id.tv_name_music);
            tvNameSinger=itemView.findViewById(R.id.tv_name_singer);
             tvTime=itemView.findViewById(R.id.tv_time);
             line=itemView.findViewById(R.id.line);
             ivSinger=itemView.findViewById(R.id.iv_singer);
        }


    }
}
