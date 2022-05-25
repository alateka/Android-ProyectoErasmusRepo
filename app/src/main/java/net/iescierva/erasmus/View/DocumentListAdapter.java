package net.iescierva.erasmus.View;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import net.iescierva.erasmus.Model.Document;
import net.iescierva.erasmus.R;
import net.iescierva.erasmus.UseCase.OnMainMenuActivity;

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.ViewHolder>{
    private Document[] listdata;
    private OnMainMenuActivity onMainMenu;

    public DocumentListAdapter(Document[] listdata, OnMainMenuActivity onMainMenu) {
        this.listdata = listdata;
        this.onMainMenu = onMainMenu;
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
        final Document document = listdata[position];
        holder.documentName.setText(document.getDocumentName());
        final Button button = holder.documentOptions;
        holder.documentOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), button);
                popup.inflate(R.menu.document_option_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.show_document:
                                System.out.println("Selecionado ==> "+document.getId());
                                return true;
                            case R.id.delete_document:
                                onMainMenu.deleteDocumentByID(document.getId());
                                return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView documentID;
        public TextView documentName;
        public Button documentOptions;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.documentID = itemView.findViewById(R.id.document_id);
            this.documentName = itemView.findViewById(R.id.document_name);
            this.documentOptions = itemView.findViewById(R.id.document_options);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}  
