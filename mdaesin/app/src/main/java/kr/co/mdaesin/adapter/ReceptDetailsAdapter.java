package kr.co.mdaesin.adapter;

import android.content.Context;
import android.text.TextUtils;
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
import kr.co.mdaesin.models.ReceiptDetailsModelView;

public class ReceptDetailsAdapter extends RecyclerView.Adapter<ReceptDetailsAdapter.ViewHolder> {

    private Context context;
    private List<ReceiptDetailsModelView> receiptDetailsModelViewList = new ArrayList<ReceiptDetailsModelView>();
    private StringBuffer sb;

    public ReceptDetailsAdapter(List<ReceiptDetailsModelView> _receiptDetailsModelViewList){
        if(_receiptDetailsModelViewList != null && _receiptDetailsModelViewList.size() > 0){
            this.receiptDetailsModelViewList = _receiptDetailsModelViewList;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_list_3, parent, false) ;
        ViewHolder vh = new ViewHolder(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // 발송지명
        sb = new StringBuffer();
        sb.append(receiptDetailsModelViewList.get(position).getAgencyname());
        sb.append("/"+(receiptDetailsModelViewList.get(position).getMd()=="1"?"연계":"중계"));

        holder.details_item_1.setText(sb.toString());

        sb = new StringBuffer();
        sb.append(receiptDetailsModelViewList.get(position).getStd_departuretime()+"/"+receiptDetailsModelViewList.get(position).getStd_deadlinetime());
        holder.details_item_2.setText(sb.toString());

        // 건수
        sb = new StringBuffer();
        sb.append(receiptDetailsModelViewList.get(position).getCnt()+"/"+BasicUtils.addStringComma(receiptDetailsModelViewList.get(position).getQty()));
        holder.details_item_3.setText(sb.toString());

        // 운임
        sb = new StringBuffer();
        sb.append(BasicUtils.addStringComma(receiptDetailsModelViewList.get(position).getFare()));
        holder.details_item_4.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return receiptDetailsModelViewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView details_item_1, details_item_2, details_item_3, details_item_4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            details_item_1 = (TextView) itemView.findViewById(R.id.details_adapter_item_1);
            details_item_2 = (TextView) itemView.findViewById(R.id.details_adapter_item_2);
            details_item_3 = (TextView) itemView.findViewById(R.id.details_adapter_item_3);
            details_item_4 = (TextView) itemView.findViewById(R.id.details_adapter_item_4);


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
    private OnitemClickListener mListener = null;
    public void setOnitemClickListener(OnitemClickListener listener){
        this.mListener = listener;
    }

}
