package com.dwarf.takeout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.dwarf.takeout.R;
import com.dwarf.takeout.model.bean.Category;
import com.dwarf.takeout.model.bean.HomeInfo;
import com.dwarf.takeout.model.bean.Promotion;
import com.dwarf.takeout.model.bean.Seller;
import com.dwarf.takeout.ui.activty.BusinessActivity;
import com.dwarf.takeout.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by qurongzhen on 2017/7/21.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {
    private static Context mContext;
    private HomeInfo mData;
    private static final int HEAD = 0;
    private static final int BODY_SELLER = 1;
    private static final int BODY_CUT = 2;

    public HomeRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = null;
        switch (viewType) {
            case HEAD:
                view = mInflater.inflate(R.layout.item_title, parent, false);
                viewHolder = new HeadHolder(view);
                break;
            case BODY_CUT:
                view = mInflater.inflate(R.layout.item_division, parent, false);
                viewHolder = new BodyCutHolder(view);
                break;
            case BODY_SELLER:
                view = mInflater.inflate(R.layout.item_seller, parent, false);
                viewHolder = new BodySellerHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        if (position == 0) {
            type = HEAD;
        } else {
            type = mData.body.get(position - 1).type == 1 ? BODY_CUT : BODY_SELLER;
        }
        return type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final int curPosition = position - 1;
        if (getItemViewType(position) == HEAD) {
            HeadHolder viewHolder = (HeadHolder) holder;
            //轮播图
            viewHolder.slider.removeAllSliders();
            for (int i = 0; i < mData.head.promotionList.size(); i++) {
                Promotion promotion = mData.head.promotionList.get(i);
                String url = promotion.pic.replace("172.16.0.116", Constant.ip);
                TextSliderView textSliderView = new TextSliderView(mContext);
                textSliderView
                        .description(promotion.info)
                        .image(url);
                textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        //轮播图点击事件
                        Toast.makeText(mContext, "轮播图条目", Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.slider.addSlider(textSliderView);
            }


            //home的分类部分视图
            viewHolder.catetory_container.removeAllViews();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            for (int i = 0; i < 4; i++) {
                View view = mInflater.inflate(R.layout.item_home_head_category, null, false);
                ImageView topIV = (ImageView) view.findViewById(R.id.top_iv);
                topIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击事件
                        Toast.makeText(mContext, "分类条目上", Toast.LENGTH_SHORT).show();
                    }
                });
                ImageView bottomIV = (ImageView) view.findViewById(R.id.bottom_iv);
                bottomIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击事件
                        Toast.makeText(mContext, "分类条目下", Toast.LENGTH_SHORT).show();
                    }
                });
                TextView topTV = (TextView) view.findViewById(R.id.top_tv);
                TextView bottomTV = (TextView) view.findViewById(R.id.bottom_tv);
                //绑定分类数据
                Category category = mData.head.categorieList.get(i);
                Category category2 = mData.head.categorieList.get(i + 4);

                Picasso.with(mContext).load(category.pic.replace("172.16.0.116", Constant.ip)).into(topIV);
                Picasso.with(mContext).load(category2.pic.replace("172.16.0.116", Constant.ip)).into(bottomIV);

                topTV.setText(category.name);
                bottomTV.setText(category2.name);
                viewHolder.catetory_container.addView(view);
            }
        } else if (getItemViewType(position) == BODY_CUT) {
            BodyCutHolder viewHolder = (BodyCutHolder) holder;
            //分割视图 暂无数据绑定
            List<String> recommendInfos = mData.body.get(curPosition).recommendInfos;
        } else if (getItemViewType(position) == BODY_SELLER) {
            final BodySellerHolder viewHolder = (BodySellerHolder) holder;

            //绑定商家信息
            final Seller seller = mData.body.get(curPosition).seller;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击事件
                    Toast.makeText(mContext, "商家条目"+curPosition, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(mContext, BusinessActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putInt("sellerId", (int) seller.getId());
                    //获取配送起的价格
                    bundle.putInt("startDispatchPrice", seller.getSendPrice());
                    //获取商铺的名称
                    bundle.putString("sellerName", seller.getName());
                    intent.putExtras(bundle);

                    viewHolder.itemView.getContext().startActivity(intent);
                }
            });
            viewHolder.tv_title.setText(seller.name);
            //holder.tv_count.setText(seller.sale);
            int score = 2;
            if (!seller.score.isEmpty()) {
                score = Integer.valueOf(seller.score);
            }
            viewHolder.ratingBar.setProgress(score);
            String url = null;
            if (curPosition == 0) {
                url = seller.pic.replace("172.16.0.116", Constant.ip);
            Picasso.with(mContext).load(url).into(viewHolder.iv_icon_seller);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.body.size() + 1 : 0;
    }

    public void setData(HomeInfo data) {
        mData = data;
    }

    /**
     * 绑定 Item View 和 Holder
     */
    public static class HeadHolder extends RecyclerView.ViewHolder {
        public SliderLayout slider;
        public LinearLayout catetory_container;

        public HeadHolder(View itemView) {
            super(itemView);
            slider = (SliderLayout) itemView.findViewById(R.id.slider);
            catetory_container = (LinearLayout) itemView.findViewById(R.id.catetory_container);
        }
    }

    public static class BodyCutHolder extends RecyclerView.ViewHolder {
        public TextView tv_count;

        public BodyCutHolder(View itemView) {
            super(itemView);
            //暂无
        }
    }

    public static class BodySellerHolder extends RecyclerView.ViewHolder {

        public TextView tv_count;
        public TextView tv_title;
        public ImageView iv_icon_seller;
        public RatingBar ratingBar;

        public BodySellerHolder(View itemView) {
            super(itemView);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count_sale);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_icon_seller = (ImageView) itemView.findViewById(R.id.iv_icon_seller);

        }
    }
}
