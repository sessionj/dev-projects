package kr.co.mdaesin.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.co.mdaesin.R;
import kr.co.mdaesin.comm.BasicUtils;
import kr.co.mdaesin.models.ReceptionQuantityModelView;

public class ReceiptLoadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "ReceiptLoadAdapter ";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<ReceptionQuantityModelView> itemList = new ArrayList<ReceptionQuantityModelView>();
    private ReceptionQuantityModelView item;
    private StringBuffer sb;

    public ReceiptLoadAdapter(List<ReceptionQuantityModelView> _itemList){

        if(_itemList != null && _itemList.size() > 0){
            this.itemList = _itemList;
        }

        item = new ReceptionQuantityModelView();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_2, parent, false);
            return new ItemViewHolder(view);
        } else {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_progress, parent, false);
            return new LoadingViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {

    }

    private void populateItemRows(ItemViewHolder holder, int position) {

        ReceptionQuantityModelView item = itemList.get(position);
        holder.setItem(item);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView rowItem_1, rowItem_2, rowItem_3;
        public LinearLayout receiptDetails,wayPoint,history,carControl;

        public ItemViewHolder(@NonNull View itemView) {
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

        public void setItem(ReceptionQuantityModelView item) {
            Log.d(TAG, "setItem: " + item.getLinename());
            // 노선명 + 노선코드
            sb = new StringBuffer();
            sb.append(item.getLinecode()+" ("+item.getLinename()+")");
            rowItem_1.setText(sb.toString());

            // 차량코드, 차량명
            sb = new StringBuffer();
            sb.append(item.getCarcode()+" 『"+item.getCarname()+"』");
            rowItem_2.setText(sb.toString());

            sb = new StringBuffer();
            sb.append("총 : ￦"+ BasicUtils.addStringComma(item.getChong()));
            sb.append(", 구간 : ￦"+BasicUtils.addStringComma(item.getGugan()));
            sb.append(" / "+item.getCnt()+"건, "+item.getQty()+"개");
            rowItem_3.setText(sb.toString());

        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.null_progressBar);
        }
    }

    public String standardSum(int cateCode){

        long result = 0;

        try{
            if ( TextUtils.isEmpty(String.valueOf(cateCode))){
                return "0";
            }

            if ( this.itemList != null && this.itemList.size() > 0){
                for ( int i=0; i <this.itemList.size(); i++){

                    if ( cateCode == 1){
                        result += Long.valueOf(this.itemList.get(i).getCnt());
                    }else if ( cateCode == 2) {
                        result += Long.valueOf(this.itemList.get(i).getQty());
                    }else if ( cateCode == 3){
                        result += Long.valueOf(this.itemList.get(i).getChong());
                    }else if ( cateCode == 4){
                        result += Long.valueOf(this.itemList.get(i).getGugan());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if ( result > 0){
            return BasicUtils.addStringComma(String.valueOf(result));
        }
        return "0";

    }

    // rows 클릭
    public interface OnitemClickListener{
        void onItemClick(View v, int pos);
    }
    private ReceptListAdapter.OnitemClickListener mListener = null;
    public void setOnitemClickListener(ReceptListAdapter.OnitemClickListener listener){
        this.mListener = listener;
    }

    // 접수 상세내역 리스너
    public interface OnReceiptDetailsListener{
        void onReceiptDetails(View v, int pos);
    }
    private ReceptListAdapter.OnReceiptDetailsListener receiptListener = null;
    public void setReceiptDetailsClickListener(ReceptListAdapter.OnReceiptDetailsListener listener){
        this.receiptListener = listener;
    }

    // 경유지별 상세 리스너
    public interface OnWayPointListenerlickListener{
        void onWayPoint(View v, int pos);
    }
    private ReceptListAdapter.OnWayPointListenerlickListener wayPointListener = null;
    public void setWayPointClickListener(ReceptListAdapter.OnWayPointListenerlickListener listener){
        this.wayPointListener = listener;
    }

    // 수정내역
    public interface OnhistoryClickListener{
        void onhistory(View v, int pos);
    }
    private ReceptListAdapter.OnhistoryClickListener historyListener = null;
    public void setHistoryClickListener(ReceptListAdapter.OnhistoryClickListener listener){
        this.historyListener = listener;
    }

    // 관제
    public interface OncarControlClickListener{
        void onCarControl(View v, int pos);
    }
    private ReceptListAdapter.OncarControlClickListener carControlListener = null;
    public void setCarControlClickListener(ReceptListAdapter.OncarControlClickListener listener){
        this.carControlListener = listener;
    }
}
