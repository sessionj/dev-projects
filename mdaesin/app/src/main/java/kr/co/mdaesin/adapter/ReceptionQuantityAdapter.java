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
        View view = inflater.inflate(R.layout.layout_list_1, parent, false) ;
        ViewHolder vh = new ViewHolder(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // 노선코드, 노선명
        sb = new StringBuffer();
        sb.append(receptionQuantityModelViewList.get(position).getLinecode()+"("+receptionQuantityModelViewList.get(position).getLinename()+")");
        holder.rowItem_1.setText(sb.toString());

        // 차량코드, 차량명
        sb = new StringBuffer();
        sb.append(receptionQuantityModelViewList.get(position).getCarcode()+"("+receptionQuantityModelViewList.get(position).getCarname()+")");
        holder.rowItem_2.setText(sb.toString());

        sb = new StringBuffer();
        sb.append(BasicUtils.addComma(receptionQuantityModelViewList.get(position).getChong())+", "+BasicUtils.addComma(receptionQuantityModelViewList.get(position).getGugan()));
        holder.rowItem_3.setText(sb.toString());

        sb = new StringBuffer();
        sb.append(receptionQuantityModelViewList.get(position).getCnt()+"건, "+receptionQuantityModelViewList.get(position).getQty()+"ea");
        holder.rowItem_4.setText(sb.toString());

    }

    @Override
    public int getItemCount() {
        return receptionQuantityModelViewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView rowItem_1, rowItem_2, rowItem_3, rowItem_4;

        public ViewHolder(@NonNull View itemView) {
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
