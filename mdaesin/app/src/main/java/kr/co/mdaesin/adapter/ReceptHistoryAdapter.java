package kr.co.mdaesin.adapter;

import android.content.Context;
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
        sb.append(receiptHistoryModelViewList.get(position).getBillNo());
        holder.history_item_1.setText(sb.toString());

        // 영업소명
        sb = new StringBuffer();
        sb.append(receiptHistoryModelViewList.get(position).getAgencyname());
        holder.history_item_2.setText(sb.toString());

        //
        sb = new StringBuffer();
        sb.append(receiptHistoryModelViewList.get(position).getUpdatedate()+":"+receiptHistoryModelViewList.get(position).getUpdatetime());
        holder.history_item_3.setText(sb.toString());

        sb = new StringBuffer();
        sb.append(receiptHistoryModelViewList.get(position).getCategory()+")"+receiptHistoryModelViewList.get(position).getBefcontent()+"=>"+receiptHistoryModelViewList.get(position).getAftcontent());
        holder.history_item_4.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return receiptHistoryModelViewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView history_item_1, history_item_2, history_item_3, history_item_4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            history_item_1 = (TextView) itemView.findViewById(R.id.history_adapter_item_1);
            history_item_2 = (TextView) itemView.findViewById(R.id.history_adapter_item_2);
            history_item_3 = (TextView) itemView.findViewById(R.id.history_adapter_item_3);
            history_item_4 = (TextView) itemView.findViewById(R.id.history_adapter_item_4);

        }
    }
}
