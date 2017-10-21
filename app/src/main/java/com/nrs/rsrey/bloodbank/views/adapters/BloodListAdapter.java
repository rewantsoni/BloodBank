package com.nrs.rsrey.bloodbank.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nrs.rsrey.bloodbank.R;
import com.nrs.rsrey.bloodbank.data.BloodGroupEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BloodListAdapter extends RecyclerView.Adapter<BloodListAdapter.MyViewHolder> {

    private final Context mContext;
    private List<BloodGroupEntity> mBloodList;

    public BloodListAdapter(Context context, List<BloodGroupEntity> bloodList) {
        this.mContext = context;
        this.mBloodList = bloodList;
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

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
