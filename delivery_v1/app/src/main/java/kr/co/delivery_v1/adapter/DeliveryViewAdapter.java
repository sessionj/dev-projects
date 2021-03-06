package kr.co.delivery_v1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.co.delivery_v1.R;
import kr.co.delivery_v1.comm.BasicUtils;
import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.models.DeliveryModelView;


public class DeliveryViewAdapter extends RecyclerView.Adapter<DeliveryViewAdapter.ViewHolder> {


    private Context context;
    private List<DeliveryModelView> deliveryModelViewList = new ArrayList<DeliveryModelView>();
    StringBuffer sb;

    /**
     * 생성자로 리스트를 전달 받음
     * @param _deliveryModelViewList
     */
    public DeliveryViewAdapter(List<DeliveryModelView> _deliveryModelViewList){
        if ( _deliveryModelViewList != null){
            deliveryModelViewList = _deliveryModelViewList;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.listview_delivery_item, parent, false) ;
        ViewHolder vh = new ViewHolder(view) ;

        return vh;
    }

    /**
     * row1 : 수화주 | 포장 | (수량) | 물품명
    * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int itemposition = position;

        /**`
         *  row1 : 주소
         */
        sb = new StringBuffer();
        /*if (deliveryModelViewList.get(itemposition).getAdress().replaceAll("(\r\n|\r|\n|\n\r)", " ").getBytes().length >= 30 ){
            Log.d("byte : ", deliveryModelViewList.get(itemposition).getAdress());
            sb.append(BasicUtils.getStrCut(deliveryModelViewList.get(itemposition).getAdress().replaceAll("(\r\n|\r|\n|\n\r)", " "), 30, false));
        }else{
            sb.append(deliveryModelViewList.get(itemposition).getAdress().replaceAll("(\r\n|\r|\n|\n\r)", " ") + " ");
        }*/
        sb.append(deliveryModelViewList.get(itemposition).getAdress().replaceAll("(\r\n|\r|\n|\n\r)", " ") + " ");
        holder.rowItem_1.setText(sb.toString());

        // 수화주, 수화주 전화번호
        sb = new StringBuffer();
        sb.append(deliveryModelViewList.get(itemposition).getArrivalman() + " ");
        sb.append("(");
        sb.append(deliveryModelViewList.get(itemposition).getArrivalmantel() + " ");
        sb.append(")");
        holder.rowItem_2.setText(sb.toString());

        // 운송장번호, 상품명, 포장, 수량
        sb = new StringBuffer();
        sb.append(deliveryModelViewList.get(itemposition).getBillno() + " ");
        sb.append(deliveryModelViewList.get(itemposition).getGoods() + " ");
        sb.append(deliveryModelViewList.get(itemposition).getPojang() + " ");
        sb.append(deliveryModelViewList.get(itemposition).getQty() + " ");
        holder.rowItem_3.setText(sb.toString());

        //배달 처리 여부
        sb = new StringBuffer();
        if ( deliveryModelViewList.get(itemposition).getDelivery_state().equals("N")){
            sb.append(Label.DELIVERY_DELIVERY_STATUS_N);
        }else{
            sb.append(Label.DELIVERY_DELIVERY_STATUS_Y);
        }
        //sb.append(deliveryModelViewList.get(itemposition).getDeliverycourse() + " ");
        holder.rowItem_4.setText(sb.toString());
        Log.e("StudyApp", "onBindViewHolder" + itemposition);
    }

    @Override
    public int getItemCount() {
        return deliveryModelViewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView rowItem_1, rowItem_2, rowItem_3, rowItem_4;

        ViewHolder(View itemView){
            super(itemView);
            rowItem_1 = (TextView) itemView.findViewById(R.id.row_item_1);
            rowItem_2 = (TextView) itemView.findViewById(R.id.row_item_2);
            rowItem_3 = (TextView) itemView.findViewById(R.id.row_item_3);
            rowItem_4 = (TextView) itemView.findViewById(R.id.row_item_4);

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

    public interface OnitemClickListener{
        void onItemClick(View v, int pos);
    }

    private OnitemClickListener mListener = null;

    public void setOnitemClickListener(OnitemClickListener listener){
        this.mListener = listener;
    }
}
