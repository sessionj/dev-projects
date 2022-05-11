package kr.co.mdaesin.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
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
import kr.co.mdaesin.models.ReceiptWayPointModelView;

public class ReceptWaypointDetailsAdapter extends RecyclerView.Adapter<ReceptWaypointDetailsAdapter.ViewHolder> {

    private Context context;
    private List<ReceiptWayPointModelView> receiptWayPointModelViewList = new ArrayList<ReceiptWayPointModelView>();
    private StringBuffer sb;

    public ReceptWaypointDetailsAdapter(List<ReceiptWayPointModelView> _receiptWayPointModelViewList){
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
        sb.append(receiptWayPointModelViewList.get(position).getDet_agencyname());
        holder.waypoint_item_1.setText(sb.toString());

        sb = new StringBuffer();
        sb.append(receiptWayPointModelViewList.get(position).getDet_pojang());
        holder.waypoint_item_2.setText(sb.toString());

        sb = new StringBuffer();
        sb.append(receiptWayPointModelViewList.get(position).getDet_goods());
        holder.waypoint_item_3.setText(sb.toString());

        sb = new StringBuffer();
        sb.append(BasicUtils.addStringComma(receiptWayPointModelViewList.get(position).getDet_qty()));
        holder.waypoint_item_4.setText(sb.toString());

        sb = new StringBuffer();
        sb.append(BasicUtils.addStringComma(receiptWayPointModelViewList.get(position).getDet_fare()));
        holder.waypoint_item_5.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return receiptWayPointModelViewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView waypoint_item_1, waypoint_item_2, waypoint_item_3, waypoint_item_4, waypoint_item_5;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            waypoint_item_1 = (TextView) itemView.findViewById(R.id.waypoint_dt_adapter_item_1);
            waypoint_item_2 = (TextView) itemView.findViewById(R.id.waypoint_dt_adapter_item_2);
            waypoint_item_3 = (TextView) itemView.findViewById(R.id.waypoint_dt_adapter_item_3);
            waypoint_item_4 = (TextView) itemView.findViewById(R.id.waypoint_dt_adapter_item_4);
            waypoint_item_5 = (TextView) itemView.findViewById(R.id.waypoint_dt_adapter_item_5);

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

    // 여기서는 한줄을 만들어서 보낸다.
    public String standardSum(){

        long fare = 0;
        long qty = 0;

        try{

            if ( this.receiptWayPointModelViewList != null && this.receiptWayPointModelViewList.size() > 0){
                for ( int i=0; i <this.receiptWayPointModelViewList.size(); i++){
                    Log.d("===================================>", "standardSum: " + this.receiptWayPointModelViewList.get(i).getDet_fare());
                    Log.d("===================================>", "standardSum: " + this.receiptWayPointModelViewList.get(i).getDet_qty());
                    fare += Long.valueOf(this.receiptWayPointModelViewList.get(i).getDet_fare());
                    qty += Long.valueOf(this.receiptWayPointModelViewList.get(i).getDet_qty());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // 건수:0건, 수량:22개, 운임:60,000원
        return "건수:"+getItemCount()+"건, 수량:"+BasicUtils.addComma(qty)+"개, 운임:"+BasicUtils.addComma(fare)+"원";
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
