package kr.co.mdaesin.adapter;

import android.content.Context;
import android.database.DatabaseUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.co.mdaesin.R;
import kr.co.mdaesin.comm.BasicUtils;
import kr.co.mdaesin.models.ReceiptHistoryModelView;
import kr.co.mdaesin.models.ReceiptWayPointModelView;

public class ReceptWaypointAdapter extends RecyclerView.Adapter<ReceptWaypointAdapter.ViewHolder> {

    private Context context;
    private List<ReceiptWayPointModelView> receiptWayPointModelViewList = new ArrayList<ReceiptWayPointModelView>();
    private StringBuffer sb;

    public ReceptWaypointAdapter(List<ReceiptWayPointModelView> _receiptWayPointModelViewList){
        if(_receiptWayPointModelViewList != null && _receiptWayPointModelViewList.size() > 0){
            this.receiptWayPointModelViewList = _receiptWayPointModelViewList;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_list_6, parent, false) ;
        ViewHolder vh = new ViewHolder(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // 출발지
        sb = new StringBuffer();
        sb.append(receiptWayPointModelViewList.get(position).getStagencyname());
        holder.waypoint_item_1.setText(sb.toString());

        sb = new StringBuffer();
        sb.append(receiptWayPointModelViewList.get(position).getEdagencyname());
        holder.waypoint_item_2.setText(sb.toString());

        sb = new StringBuffer();
        sb.append(BasicUtils.addStringComma(receiptWayPointModelViewList.get(position).getSendfare()));
        holder.waypoint_item_3.setText(sb.toString());

        sb = new StringBuffer();
        sb.append(BasicUtils.addStringComma(receiptWayPointModelViewList.get(position).getArrivefare()));
        holder.waypoint_item_4.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return receiptWayPointModelViewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView waypoint_item_1, waypoint_item_2, waypoint_item_3, waypoint_item_4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            waypoint_item_1 = (TextView) itemView.findViewById(R.id.waypoint_adapter_item_1);
            waypoint_item_2 = (TextView) itemView.findViewById(R.id.waypoint_adapter_item_2);
            waypoint_item_3 = (TextView) itemView.findViewById(R.id.waypoint_adapter_item_3);
            waypoint_item_4 = (TextView) itemView.findViewById(R.id.waypoint_adapter_item_4);

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

    public String standardSum(int cateCode){

        long result = 0;

        try{
            if ( TextUtils.isEmpty(String.valueOf(cateCode))){
                return "0";
            }

            if ( this.receiptWayPointModelViewList != null && this.receiptWayPointModelViewList.size() > 0){
                for ( int i=0; i <this.receiptWayPointModelViewList.size(); i++){

                    if ( cateCode == 1){
                        result += Long.valueOf(this.receiptWayPointModelViewList.get(i).getSendfare().replaceAll(",", ""));
                    }else if ( cateCode == 2) {
                        result += Long.valueOf(this.receiptWayPointModelViewList.get(i).getArrivefare().replaceAll(",", ""));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if ( result > 0){
            return BasicUtils.addComma(result);
        }
        return "0";

    }

    // rows 클릭
    public interface OnitemClickListener{
        void onItemClick(View v, int pos);
    }
    private ReceptDetailsAdapter.OnitemClickListener mListener = null;
    public void setOnitemClickListener(ReceptDetailsAdapter.OnitemClickListener listener){
        this.mListener = listener;
    }
}
