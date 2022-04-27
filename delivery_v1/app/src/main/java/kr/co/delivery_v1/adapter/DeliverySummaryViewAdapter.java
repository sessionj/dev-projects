package kr.co.delivery_v1.adapter;

import android.annotation.SuppressLint;
import android.content.ClipData;
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

import net.daum.mf.map.task.MainQueueHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import kr.co.delivery_v1.MainActivity;
import kr.co.delivery_v1.R;
import kr.co.delivery_v1.models.DeliveryListViewItem;
import kr.co.delivery_v1.models.DeliveryModelView;


public class DeliverySummaryViewAdapter extends RecyclerView.Adapter<DeliverySummaryViewAdapter.ViewHolder> {

    View view;
    Context context;
    ArrayList<String> btnCheckList;
    QuanitityListener quanitityListener;

    private List<DeliveryListViewItem> deliveryModelViewList = new ArrayList<DeliveryListViewItem>();
    StringBuffer sb;

    /**
     * 생성자로 리스트를 전달 받음
     * @param _deliveryModelViewList
     */
    public DeliverySummaryViewAdapter(Context context, List<DeliveryListViewItem> _deliveryModelViewList, QuanitityListener quanitityListener){
        this.context = context;
        this.deliveryModelViewList = _deliveryModelViewList;
        this.quanitityListener = quanitityListener;
        btnCheckList = new ArrayList<String >();
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

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
            sb.append(" 코스명없음");
        }
        holder.summary_row_item_1.setText(sb.toString());

        sb = new StringBuffer();
        sb.append(" "+deliveryModelViewList.get(itemposition).getDelivery_course_cnt() + "건 ");
        holder.summary_row_item_2.setText(sb.toString());

        holder.summary_row_item_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( holder.summary_row_item_check.isChecked()){
                    btnCheckList.add(deliveryModelViewList.get(position).getDelivery_course() );
                }else{
                    btnCheckList.remove(deliveryModelViewList.get(position).getDelivery_course());
                }
                quanitityListener.onQuanitityChange(btnCheckList);
            }
        });

        /*if (deliveryModelViewList.get(position).isSelected()){
            holder.summary_row_item_check.isChecked();
        }*/
    }

    /**
     * 선택된 아이템 배달 코스 목록
     * @return
     */
    public ArrayList<String> getBtnCheckList(){return btnCheckList; }

    /**
     * 전체 아이템 체크박스 클릭
     */
    public void allCheck(){

        quanitityListener.onQuanitityChange(btnCheckList);
    }
    @Override
    public int getItemCount() {
        return deliveryModelViewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView summary_row_item_1, summary_row_item_2;
        public CheckBox summary_row_item_check;
        public Button summary_row_item_btn;
        CheckBox checkBox;

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
