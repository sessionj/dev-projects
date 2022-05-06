package kr.co.mdaesin.adapter;

import android.content.Context;
import android.graphics.Paint;
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
import kr.co.mdaesin.models.ReceptionQuantityModelView;

public class ReceptDetailsUnsongAdapter extends RecyclerView.Adapter<ReceptDetailsUnsongAdapter.ViewHolder> {

    private Context context;
    private List<ReceptionQuantityModelView> receiptDetailsUnsongModelViewList = new ArrayList<ReceptionQuantityModelView>();
    private StringBuffer sb;

    public ReceptDetailsUnsongAdapter(List<ReceptionQuantityModelView> _receiptDetailsModelViewList){
        if(_receiptDetailsModelViewList != null && _receiptDetailsModelViewList.size() > 0){
            this.receiptDetailsUnsongModelViewList = _receiptDetailsModelViewList;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_list_4, parent, false) ;
        ViewHolder vh = new ViewHolder(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // 발송지명
        sb = new StringBuffer();
        sb.append(receiptDetailsUnsongModelViewList.get(position).getBillNo());
        holder.row_unsong_item_1.setText(sb.toString());

        sb = new StringBuffer();
        sb.append(receiptDetailsUnsongModelViewList.get(position).getSendingagencyname() + "->" + receiptDetailsUnsongModelViewList.get(position).getArrivalagencyname());
        sb.append(" / " + receiptDetailsUnsongModelViewList.get(position).getArrivalman() + " / " + receiptDetailsUnsongModelViewList.get(position).getGoods());
        sb.append(" / " + receiptDetailsUnsongModelViewList.get(position).getQty()+"개");
        holder.row_unsong_item_2.setText(sb.toString());

        sb = new StringBuffer();
        sb.append("￦"+BasicUtils.addStringComma(receiptDetailsUnsongModelViewList.get(position).getPrefare()));
        sb.append(" / ￦" + BasicUtils.addStringComma(receiptDetailsUnsongModelViewList.get(position).getFare()));
        sb.append(" / ￦" + BasicUtils.addStringComma(receiptDetailsUnsongModelViewList.get(position).getDeliveryfare()));
        sb.append(" / " + receiptDetailsUnsongModelViewList.get(position).getPayway()=="1"?"(현불)":"(착불)" );

        holder.row_unsong_item_3.setText(sb.toString());

    }

    @Override
    public int getItemCount() {
        return receiptDetailsUnsongModelViewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView row_unsong_item_1, row_unsong_item_2, row_unsong_item_3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            row_unsong_item_1 = (TextView) itemView.findViewById(R.id.row_unsong_item_1);
            row_unsong_item_2 = (TextView) itemView.findViewById(R.id.row_unsong_item_2);
            row_unsong_item_3 = (TextView) itemView.findViewById(R.id.row_unsong_item_3);

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
