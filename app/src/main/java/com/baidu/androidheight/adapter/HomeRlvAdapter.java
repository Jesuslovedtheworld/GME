package com.baidu.androidheight.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.androidheight.R;
import com.baidu.androidheight.bean.HomeBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class HomeRlvAdapter extends RecyclerView.Adapter<HomeRlvAdapter.VH>{
	private String a = "在记事本上制造的冲突";
    private String a = "在github上修改的数据   制造冲突";
    private ArrayList<HomeBean.DataBean.DatasBean> arr;
    private Context context;

    public HomeRlvAdapter(ArrayList<HomeBean.DataBean.DatasBean> arr, Context context) {
        this.arr = arr;
        this.context = context;
    }

    public void setArr(ArrayList<HomeBean.DataBean.DatasBean> arr) {
        this.arr = arr;
    }

    @NonNull

    @Override
    public HomeRlvAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_item, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRlvAdapter.VH vh, int i) {
        HomeBean.DataBean.DatasBean bean = arr.get(i);
        String imageUrl = bean.getEnvelopePic();
        if (imageUrl != null){
            RequestOptions crop = new RequestOptions()
                    .placeholder(R.drawable.godt)
                    .centerCrop();

            Glide.with(context).load(imageUrl).apply(crop).into(vh.img);
        }
        vh.tv.setText(bean.getDesc());

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final TextView tv;

        public VH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
