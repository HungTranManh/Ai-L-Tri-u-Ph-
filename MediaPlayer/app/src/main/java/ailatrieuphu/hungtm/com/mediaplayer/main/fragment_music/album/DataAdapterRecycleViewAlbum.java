package ailatrieuphu.hungtm.com.mediaplayer.main.fragment_music.album;

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

import ailatrieuphu.hungtm.com.mediaplayer.R;

public class DataAdapterRecycleViewAlbum extends RecyclerView.Adapter<DataAdapterRecycleViewAlbum.RecycleViewAlbum>{
    private IDataRecycleViewAlbum iDataRecycleViewAlbum;
    public DataAdapterRecycleViewAlbum(IDataRecycleViewAlbum iDataRecycleViewAlbum) {
        this.iDataRecycleViewAlbum = iDataRecycleViewAlbum;
    }

    @NonNull
    @Override
    public RecycleViewAlbum onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_album,parent,false);
        return new DataAdapterRecycleViewAlbum.RecycleViewAlbum(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAlbum holder, final int position) {
        ItemDataAlbum itemDataAlbum=iDataRecycleViewAlbum.getItemDataAlbum(position);
        if(itemDataAlbum.getPathImageAlbum()!=null){
            Picasso.with(holder.itemView.getContext()).load(new File(itemDataAlbum.getPathImageAlbum())).into(holder.ivAvatar);
        }
        else {
            Picasso.with(holder.itemView.getContext()).load(R.drawable.not_nhac).into(holder.ivAvatar);
      }
        holder.tvNameAlBum.setText(itemDataAlbum.getNameAlbum()+"");
        holder.tvNameSinger.setText(itemDataAlbum.getNameSinger()+"");
        holder.tvNumberSize.setText(itemDataAlbum.getSizeItems()+"");
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iDataRecycleViewAlbum.clickItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return iDataRecycleViewAlbum.getCount();
    }
    public interface IDataRecycleViewAlbum{
        void clickItem(int position);
        ItemDataAlbum getItemDataAlbum(int position);
        int getCount();

    }

    public class RecycleViewAlbum extends RecyclerView.ViewHolder{
        private ImageView ivAvatar;
        private TextView tvNameAlBum, tvNameSinger,tvNumberSize;
        private RelativeLayout line;
        public RecycleViewAlbum(View itemView) {
            super(itemView);
            ivAvatar=itemView.findViewById(R.id.iv_avatar);
            tvNameAlBum=itemView.findViewById(R.id.tv_name_album);
            tvNameSinger =itemView.findViewById(R.id.tv_name_singer_album);
            tvNumberSize=itemView.findViewById(R.id.tv_number_size);
            line=itemView.findViewById(R.id.line);
        }
    }
}
