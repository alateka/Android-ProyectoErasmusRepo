// Author ==> Alberto Pérez Fructuoso
// File   ==> DocumentListAdapter.java
// Date   ==> 2022/05/29

package net.iescierva.erasmus.View;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import net.iescierva.erasmus.Model.Document;
import net.iescierva.erasmus.R;
import net.iescierva.erasmus.UseCase.Actions;

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.ViewHolder>{
    private final Document[] listdata;
    private final Actions activity;

    public DocumentListAdapter(Document[] listdata, Actions activity) {
        this.listdata = listdata;
        this.activity = activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(listItem);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Document document = listdata[position];
        holder.documentName.setText(document.getDocumentName());
        final RelativeLayout relativeLayout = holder.relativeLayout;
        holder.relativeLayout.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(v.getContext(), relativeLayout);
            popup.inflate(R.menu.document_option_menu);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.show_document:
                        activity.downloadAndOpenPDF(document.getId(), document.getDocumentName());
                        return true;
                    case R.id.delete_document:
                        activity.deleteDocumentByID(document.getId());
                        return true;
                }
                return false;
            });
            popup.show();
        });
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
            documentID = itemView.findViewById(R.id.document_id);
            documentName = itemView.findViewById(R.id.document_name);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}  
