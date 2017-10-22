package com.nrs.rsrey.bloodbank.views.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.nrs.rsrey.bloodbank.R;
import com.nrs.rsrey.bloodbank.data.BloodGroupEntity;
import com.nrs.rsrey.bloodbank.views.listeners.ItemClickListener;
import com.nrs.rsrey.bloodbank.views.listeners.PopUpMenuClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BloodListAdapter extends RecyclerView.Adapter<BloodListAdapter.MyViewHolder> {

    private final Context mContext;
    private final ItemClickListener mItemClickListener;
    private final PopUpMenuClickListener mPopUpMenuClickListener;
    private List<BloodGroupEntity> mBloodList;

    public BloodListAdapter(Context context, List<BloodGroupEntity> bloodList, ItemClickListener itemClickListener, PopUpMenuClickListener popUpMenuClickListener) {
        this.mContext = context;
        this.mBloodList = bloodList;
        this.mItemClickListener = itemClickListener;
        this.mPopUpMenuClickListener = popUpMenuClickListener;
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
        if (bloodGroupEntity.getApproved() == 1) {
            holder.mStatus.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_check_circle_black_48px));
        } else {
            holder.mStatus.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_help_outline_black_48px));
        }
    }

    @Override
    public int getItemCount() {
        return mBloodList == null ? 0 : mBloodList.size();
    }

    public void swapItem(List<BloodGroupEntity> bloodGroupEntityList) {
        this.mBloodList = bloodGroupEntityList;
    }

    private void inflatePopUpMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(mContext, view, Gravity.END);
        popupMenu.inflate(R.menu.list_pop_up_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuPopUpListEdit:
                    mPopUpMenuClickListener.edit(position);
                    break;
                case R.id.menuPopUpListDelete:
                    mPopUpMenuClickListener.delete(position);
                    break;
            }
            return false;
        });
        popupMenu.show();
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
            itemView.setOnClickListener(v -> {
                mItemClickListener.onClick(getAdapterPosition());
            });
            itemView.setOnLongClickListener(v -> {
                inflatePopUpMenu(v, getAdapterPosition());
                return false;
            });
        }
    }
}
