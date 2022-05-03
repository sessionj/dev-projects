package kr.co.mdaesin.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.co.mdaesin.R;
import kr.co.mdaesin.comm.BasicUtils;
import kr.co.mdaesin.models.ReceptionQuantityModelView;

public class ReceptionQuantityAdapter extends RecyclerView.Adapter<ReceptionQuantityAdapter.ViewHolder> {

    private Context context;
    private List<ReceptionQuantityModelView> receptionQuantityModelViewList = new ArrayList<ReceptionQuantityModelView>();
    private StringBuffer sb;

    public ReceptionQuantityAdapter(List<ReceptionQuantityModelView> _receptionQuantityModelViewList){
        if(_receptionQuantityModelViewList != null && _receptionQuantityModelViewList.size() > 0){
            this.receptionQuantityModelViewList = _receptionQuantityModelViewList;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_list_2, parent, false) ;
        ViewHolder vh = new ViewHolder(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // 노선명 + 노선코드
        sb = new StringBuffer();
        sb.append(receptionQuantityModelViewList.get(position).getLinecode()+" ("+receptionQuantityModelViewList.get(position).getLinename()+")");
        holder.rowItem_1.setText(sb.toString());

        // 차량코드, 차량명
        sb = new StringBuffer();
        sb.append(receptionQuantityModelViewList.get(position).getCarcode()+" 『"+receptionQuantityModelViewList.get(position).getCarname()+"』");
        holder.rowItem_2.setText(sb.toString());

        sb = new StringBuffer();
        sb.append(BasicUtils.addComma("총 : ￦"+receptionQuantityModelViewList.get(position).getChong()));
        sb.append(", 구간 : ￦"+BasicUtils.addComma(receptionQuantityModelViewList.get(position).getGugan()));
        sb.append(" / "+receptionQuantityModelViewList.get(position).getCnt()+"건, "+receptionQuantityModelViewList.get(position).getQty()+"개");
        holder.rowItem_3.setText(sb.toString());

    }

    @Override
    public int getItemCount() {
        return receptionQuantityModelViewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView rowItem_1, rowItem_2, rowItem_3;
        public LinearLayout receiptDetails,wayPoint,history,carControl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowItem_1 = (TextView) itemView.findViewById(R.id.row_item_1);
            rowItem_2 = (TextView) itemView.findViewById(R.id.row_item_2);
            rowItem_3 = (TextView) itemView.findViewById(R.id.row_item_3);

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

            receiptDetails = (LinearLayout) itemView.findViewById(R.id.linerlayout_1);
            wayPoint = (LinearLayout) itemView.findViewById(R.id.linerlayout_2);
            history = (LinearLayout) itemView.findViewById(R.id.linerlayout_3);
            carControl = (LinearLayout) itemView.findViewById(R.id.linerlayout_4);

            receiptDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        if ( receiptListener != null){
                            receiptListener.onReceiptDetails(v, pos);
                        }
                    }
                }
            });

            wayPoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        if ( wayPointListener != null){
                            wayPointListener.onWayPoint(v, pos);
                        }
                    }
                }
            });

            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        if ( historyListener != null){
                            historyListener.onhistory(v, pos);
                        }
                    }
                }
            });

            carControl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        if ( carControlListener != null){
                            carControlListener.onCarControl(v, pos);
                        }
                    }
                }
            });
        }
    }

    public int listTotalCount(){
        return this.receptionQuantityModelViewList.size();
    }
    /*public int listFareSum(){
        int resultFare = 0;
        if ( this.receptionQuantityModelViewList != null && this.receptionQuantityModelViewList.size() > 0){
            for ( int i=0; i <this.receptionQuantityModelViewList.size(); i++){

            }
        }
    }*/
    // rows 클릭
    public interface OnitemClickListener{
        void onItemClick(View v, int pos);
    }
    private OnitemClickListener mListener = null;
    public void setOnitemClickListener(OnitemClickListener listener){
        this.mListener = listener;
    }

    // 접수 상세내역 리스너
    public interface OnReceiptDetailsListener{
        void onReceiptDetails(View v, int pos);
    }
    private OnReceiptDetailsListener receiptListener = null;
    public void setReceiptDetailsClickListener(OnReceiptDetailsListener listener){
        this.receiptListener = listener;
    }

    // 경유지별 상세 리스너
    public interface OnWayPointListenerlickListener{
        void onWayPoint(View v, int pos);
    }
    private OnWayPointListenerlickListener wayPointListener = null;
    public void setWayPointClickListener(OnWayPointListenerlickListener listener){
        this.wayPointListener = listener;
    }

    // 수정내역
    public interface OnhistoryClickListener{
        void onhistory(View v, int pos);
    }
    private OnhistoryClickListener historyListener = null;
    public void setHistoryClickListener(OnhistoryClickListener listener){
        this.historyListener = listener;
    }

    // 관제
    public interface OncarControlClickListener{
        void onCarControl(View v, int pos);
    }
    private OncarControlClickListener carControlListener = null;
    public void setCarControlClickListener(OncarControlClickListener listener){
        this.carControlListener = listener;
    }

}
