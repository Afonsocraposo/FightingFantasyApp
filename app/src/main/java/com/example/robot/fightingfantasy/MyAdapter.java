package com.example.robot.fightingfantasy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.robot.fightingfantasy.classes.Item;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerTesteViewHolder> {

    public static ClickRecyclerView_Interface clickRecyclerViewInterface;
    Context mctx;
    private List<Item> mList;

    public MyAdapter(Context ctx, List<Item> list, ClickRecyclerView_Interface clickRecyclerViewInterface) {
        this.mctx = ctx;
        this.mList = list;
        this.clickRecyclerViewInterface = clickRecyclerViewInterface;
    }

    @Override
    public RecyclerTesteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new RecyclerTesteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerTesteViewHolder viewHolder, int i) {
        Item pessoa = mList.get(i);

        viewHolder.viewNome.setText(pessoa.getItem());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    protected class RecyclerTesteViewHolder extends RecyclerView.ViewHolder {

        protected TextView viewNome;

        public RecyclerTesteViewHolder(final View itemView) {
            super(itemView);

            viewNome = itemView.findViewById(R.id.textview_nome);

            //Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickRecyclerViewInterface.onCustomClick(mList.get(getLayoutPosition()));

                }
            });
        }
    }
}