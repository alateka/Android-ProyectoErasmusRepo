// Author ==> Alberto Pérez Fructuoso
// File   ==> DocumentListAdapter.java
// Date   ==> 2022/05/29

package net.iescierva.erasmus.View;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import net.iescierva.erasmus.Model.Document;
import net.iescierva.erasmus.R;
import net.iescierva.erasmus.UseCase.Actions;

/**
 * Clase encargada de instanciar el adaptador para el recycler view donde se representarán los documentos.
 */
public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.ViewHolder>{
    private final Document[] listData;
    private final Actions activity;

    public DocumentListAdapter(Document[] listData, Actions activity) {
        this.listData = listData;
        this.activity = activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(listItem);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Document document = listData[position];

        holder.documentName.setText(document.getDocumentName());
        holder.typeFile.setImageResource(android.R.drawable.checkbox_off_background);

        if (document.getDocumentName().contains(".pdf"))
            holder.typeFile.setImageResource(R.drawable.pdf_type);

        if (document.getDocumentName().contains(".txt"))
            holder.typeFile.setImageResource(R.drawable.txt_type);

        if (document.getDocumentName().contains(".png")
                || document.getDocumentName().contains(".jpg")
                || document.getDocumentName().contains(".jpeg"))
            holder.typeFile.setImageResource(R.drawable.img_type);

        if (document.getDocumentName().contains(".odt")
                || document.getDocumentName().contains(".docx")
                || document.getDocumentName().contains(".doc"))
            holder.typeFile.setImageResource(R.drawable.office_document_type);

        if (document.getDocumentName().contains(".tar")
                || document.getDocumentName().contains(".zip")
                || document.getDocumentName().contains(".rar")
                || document.getDocumentName().contains(".7z"))
            holder.typeFile.setImageResource(R.drawable.compress_type);


        final RelativeLayout relativeLayout = holder.relativeLayout;
        holder.relativeLayout.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(v.getContext(), relativeLayout);
            popup.inflate(R.menu.document_option_menu);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.show_document:
                        activity.downloadAndOpen(document.getId(), document.getDocumentName());
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
        return listData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView documentID;
        public TextView documentName;

        public ImageView typeFile;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            documentID = itemView.findViewById(R.id.document_id);
            documentName = itemView.findViewById(R.id.document_name);
            typeFile = itemView.findViewById(R.id.type_file);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}  
