package kr.co.delivery_v1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.co.delivery_v1.MainActivity;
import kr.co.delivery_v1.R;
import kr.co.delivery_v1.models.DeliveryListViewItem;
import kr.co.delivery_v1.models.DeliveryModelView;


public class DeliverySummaryViewAdapter extends RecyclerView.Adapter<DeliverySummaryViewAdapter.ViewHolder> {

    private List<DeliveryListViewItem> deliveryModelViewList = new ArrayList<DeliveryListViewItem>();
    StringBuffer sb;

    /**
     * 생성자로 리스트를 전달 받음
     * @param _deliveryModelViewList
     */
    public DeliverySummaryViewAdapter(List<DeliveryListViewItem> _deliveryModelViewList){
        if ( _deliveryModelViewList != null){
            deliveryModelViewList = _deliveryModelViewList;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.listview_delivery_summary_item, parent, false) ;
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
        /**
         *  row1 : 배달 코스명
         */
        sb = new StringBuffer();
        sb.append("["+deliveryModelViewList.get(itemposition).getDelivery_course()+"]");
        if ( deliveryModelViewList.get(itemposition).getDelivery_course_name() != null ){
            if (deliveryModelViewList.get(itemposition).getDelivery_course_name().equals("NOTCOURSE")){
                sb.append(" 코스미등록");
            }else{
                sb.append(deliveryModelViewList.get(itemposition).getDelivery_course_name());
            }
        }else{
            sb.append(" 코스미등록");
        }


        holder.summary_row_item_1.setText(sb.toString());
        sb = new StringBuffer();
        sb.append(" "+deliveryModelViewList.get(itemposition).getDelivery_course_cnt() + "건 ");
        holder.summary_row_item_2.setText(sb.toString());

        Log.e("StudyApp", "onBindViewHolder" + itemposition);
    }

    @Override
    public int getItemCount() {
        return deliveryModelViewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView summary_row_item_check, summary_row_item_1, summary_row_item_2;
        public Button summary_row_item_btn;

        ViewHolder(View itemView){
            super(itemView);
            summary_row_item_check = (CheckBox) itemView.findViewById(R.id.summary_row_item_check);
            summary_row_item_1 = (TextView) itemView.findViewById(R.id.summary_row_item_1);
            summary_row_item_2 = (TextView) itemView.findViewById(R.id.summary_row_item_2);
            summary_row_item_btn = (Button) itemView.findViewById(R.id.summary_row_item_btn);

            /**
             * 버튼 리스너
             */
            summary_row_item_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        if ( deliveryButtonListener != null){
                            deliveryButtonListener.onItemButtonClick(v, pos);
                        }
                    }
                }
            });

            summary_row_item_btn.setOnClickListener(new View.OnClickListener() {
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

    /**
     * 개별 자료 받기 버튼 리스너
     */
    public interface OnitemClickListener{
        void onItemClick(View v, int pos);
    }
    private OnitemClickListener mListener = null;
    public void setOnitemClickListener(OnitemClickListener listener){
        this.mListener = listener;
    }

    /**
     *  로우에 체크박스 체크시 받아올 항목
     */
    public interface OnitemButtonClickListener{
        void onItemButtonClick(View v, int pos);
    }
    private OnitemButtonClickListener deliveryButtonListener = null;
    public void setOnitemButtonClickListener(OnitemButtonClickListener listener){
        this.deliveryButtonListener = listener;
    }
}
