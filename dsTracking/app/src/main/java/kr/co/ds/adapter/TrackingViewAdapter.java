package kr.co.ds.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.co.ds.R;
import kr.co.ds.comm.BasicUtils;
import kr.co.ds.models.TrackingModelView;

public class TrackingViewAdapter extends RecyclerView.Adapter<TrackingViewAdapter.ViewHolder> {

    private Context context;
    private List<TrackingModelView> itemList = new ArrayList<TrackingModelView>();
    private StringBuffer sb;

    public TrackingViewAdapter(List<TrackingModelView> _itemList){
        if(_itemList != null && _itemList.size() > 0){
            this.itemList = _itemList;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_view_table_item, parent, false) ;
        ViewHolder vh = new ViewHolder(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TrackingModelView model = new TrackingModelView();
        model = itemList.get(position);

        holder.rowItem_1.setText(model.getItem_gubun().toString());
        holder.rowItem_1.setText(model.getItem_agencyname().toString());
        holder.rowItem_1.setText(model.getItem_tel().toString());
        holder.rowItem_1.setText(model.getItem_inputday().toString());
        holder.rowItem_1.setText(model.getItem_outputday().toString());
        holder.rowItem_1.setText(model.getItem_location().toString());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView rowItem_1, rowItem_2, rowItem_3, rowItem_4, rowItem_5, rowItem_6;
        public LinearLayout tracking_linerlayout_1,tracking_linerlayout_2,tracking_linerlayout_3,tracking_linerlayout_4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowItem_1 = (TextView) itemView.findViewById(R.id.view_adapter_item1);
            rowItem_2 = (TextView) itemView.findViewById(R.id.view_adapter_item2);
            rowItem_3 = (TextView) itemView.findViewById(R.id.view_adapter_item3);
            rowItem_4 = (TextView) itemView.findViewById(R.id.view_adapter_item4);
            rowItem_5 = (TextView) itemView.findViewById(R.id.view_adapter_item5);
            rowItem_6 = (TextView) itemView.findViewById(R.id.view_adapter_item6);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        if ( mListener != null){
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }
    }

    public String totalInformation(){
        if ( itemList != null && itemList.size() > 0){

            int success = 0;

            for ( int i=0; i < itemList.size(); i ++){
                if ( itemList.get(i).getBillstate().equals("44")){
                    success ++;
                }
            }
            return "총 "+ itemList.size() +"/"+success + "처리";
        }
        return "";
    }

    // rows 클릭
    public interface OnitemClickListener{
        void onItemClick(View v, int pos);
    }
    private OnitemClickListener mListener = null;
    public void setOnitemClickListener(OnitemClickListener listener){
        this.mListener = listener;
    }

}
