package net.iescierva.erasmus.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import net.iescierva.erasmus.Model.Document;
import net.iescierva.erasmus.R;

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.ViewHolder>{
    private Document[] listdata;

    // RecyclerView recycler_view;
    public DocumentListAdapter(Document[] listdata) {
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Document Document = listdata[position];
        holder.documentName.setText(listdata[position].getDocumentName());
        holder.relativeLayout.setOnClickListener(
                view -> Toast.makeText(view.getContext(),"click on item: "+Document.getDocumentName(),Toast.LENGTH_LONG).show()
        );
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView documentID;
        public TextView documentName;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.documentID = itemView.findViewById(R.id.document_id);
            this.documentName = itemView.findViewById(R.id.document_name);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}  
