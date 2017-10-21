package com.nrs.rsrey.bloodbank.views.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nrs.rsrey.bloodbank.R;
import com.nrs.rsrey.bloodbank.data.BloodGroupEntity;
import com.nrs.rsrey.bloodbank.views.listeners.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BloodListAdapter extends RecyclerView.Adapter<BloodListAdapter.MyViewHolder> {

    private final Context mContext;
    private final ItemClickListener mItemClickListener;
    private List<BloodGroupEntity> mBloodList;

    public BloodListAdapter(Context context, List<BloodGroupEntity> bloodList,ItemClickListener itemClickListener) {
        this.mContext = context;
        this.mBloodList = bloodList;
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BloodGroupEntity bloodGroupEntity = mBloodList.get(position);
        holder.mName.setText(bloodGroupEntity.getName());
        holder.mBloodGroup.setText(bloodGroupEntity.getBloodGroup());
        if(bloodGroupEntity.getApproved()==1){
            holder.mStatus.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_check_circle_black_48px));
        }
    }

    @Override
    public int getItemCount() {
        return mBloodList == null ? 0 : mBloodList.size();
    }

    public void swapItem(List<BloodGroupEntity> bloodGroupEntityList){
        this.mBloodList = bloodGroupEntityList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemUserName)
        TextView mName;
        @BindView(R.id.itemUserBloodGroup)
        TextView mBloodGroup;
        @BindView(R.id.itemUserStatus)
        ImageView mStatus;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v->{
                mItemClickListener.onClick(getAdapterPosition());
            });
        }
    }
}
