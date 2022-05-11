package kr.co.mdaesin.adapter;

import android.content.Context;
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
import kr.co.mdaesin.models.ReceiptDetailsModelView;
import kr.co.mdaesin.models.ReceiptHistoryModelView;

public class ReceptHistoryAdapter extends RecyclerView.Adapter<ReceptHistoryAdapter.ViewHolder> {

    private Context context;
    private List<ReceiptHistoryModelView> receiptHistoryModelViewList = new ArrayList<ReceiptHistoryModelView>();
    private StringBuffer sb;

    public ReceptHistoryAdapter(List<ReceiptHistoryModelView> _receiptHistoryModelViewList){
        if(_receiptHistoryModelViewList != null && _receiptHistoryModelViewList.size() > 0){
            this.receiptHistoryModelViewList = _receiptHistoryModelViewList;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_list_5, parent, false) ;
        ViewHolder vh = new ViewHolder(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // 운송장 번호
        sb = new StringBuffer();
        sb.append(getStringSplit(receiptHistoryModelViewList.get(position).getBillNo()));
        holder.history_item_1.setText(sb.toString());

        // 수정영업소(시간)
        sb = new StringBuffer();
        sb.append(receiptHistoryModelViewList.get(position).getAgencyname());
        sb.append("("+getDateSplit(receiptHistoryModelViewList.get(position).getUpdatedate())+":"+getTimeSplit(receiptHistoryModelViewList.get(position).getUpdatetime())+")");
        holder.history_item_2.setText(sb.toString());

        // 변동항목) 내역
        sb = new StringBuffer();
        sb.append("["+receiptHistoryModelViewList.get(position).getCategory()+"] ");
        sb.append(receiptHistoryModelViewList.get(position).getBefcontent()+"=>"+receiptHistoryModelViewList.get(position).getAftcontent());
        holder.history_item_3.setText(sb.toString());

    }

    @Override
    public int getItemCount() {
        return receiptHistoryModelViewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView history_item_1, history_item_2, history_item_3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            history_item_1 = (TextView) itemView.findViewById(R.id.history_adapter_item_1);
            history_item_2 = (TextView) itemView.findViewById(R.id.history_adapter_item_2);
            history_item_3 = (TextView) itemView.findViewById(R.id.history_adapter_item_3);

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

    // rows 클릭
    public interface OnitemClickListener{
        void onItemClick(View v, int pos);
    }
    private ReceptDetailsAdapter.OnitemClickListener mListener = null;
    public void setOnitemClickListener(ReceptDetailsAdapter.OnitemClickListener listener){
        this.mListener = listener;
    }

    private String getStringSplit(String billNo){
        if (TextUtils.isEmpty(billNo) || billNo.length() != 13){
            return "";
        }
        return billNo.substring(0,4) + "-" + billNo.substring(4,7) + "-" + billNo.substring(7,13);
    }
    // 시간 분리
    private String getTimeSplit(String time){
        if (TextUtils.isEmpty(time) || time.length() != 4){
            return "";
        }
        return time.substring(0,2) + ":" + time.substring(2,4) ;
    }
    // 날짜 분리
    private String getDateSplit(String date){
        if (TextUtils.isEmpty(date) || date.length() != 8){
            return "";
        }
        return date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6,8) ;
    }
}
