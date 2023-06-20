package com.example.bledi;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bledi.model.Reclamation;
import com.squareup.picasso.Picasso;

import java.util.List;



public class ReclamationsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "HeadsetPlayer_Memory_PlaylistID";
    private static final String TAG1 = "TrackListAdapter_search_header";
    private final int VIEW_TYPE_LOADING = 0;
    private final int VIEW_TYPE_SIMPLE_ITEM = 1;
    private final int VIEW_TYPE_ADD_APARTMENT = 2;
    private final int VIEW_TYPE_LOGOUT = 3;

    private final LayoutInflater mInflater;
    private final Activity mActivity;
    private List<Reclamation> mReclamationsList;


    public ReclamationsListAdapter(Activity aParentActivity, List<Reclamation> aReclamationsList) {

        mActivity = aParentActivity;
        mInflater = LayoutInflater.from(aParentActivity);
        this.mReclamationsList = aReclamationsList;
    }


    public void updateReclamationList(List<Reclamation> aReclamationsList) {
        this.mReclamationsList = aReclamationsList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int aViewType) {

        if (aViewType == VIEW_TYPE_SIMPLE_ITEM) {
            View itemView = mInflater
                    .inflate(R.layout.story_list_row, parent, false);
            return new ReclamationViewHolder(itemView);
        }
        {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReclamationViewHolder) {

            ReclamationViewHolder theHolder = (ReclamationViewHolder) holder;
            Reclamation theReclamation = mReclamationsList.get(position);

            theHolder.mTitle.setText(theReclamation.anomalie);
            theHolder.mSubTitle.setText(theReclamation.date);
            theHolder.mcommentaires.setText(theReclamation.commentaires);
            theHolder.metat.setText(theReclamation.etat);


            Picasso.get().load(theReclamation.getImageUrl()).into(theHolder.mIcon);




        }
    }

    @Override
    public int getItemCount() {
        return mReclamationsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_SIMPLE_ITEM;
    }





    public class ReclamationViewHolder extends RecyclerView.ViewHolder {
        public View mItemView;
        public TextView mTitle, mSubTitle ,mcommentaires , metat;
        public ImageView mIcon;

        public ReclamationViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mTitle = (TextView) itemView.findViewById(R.id.title_tv);
            mSubTitle = (TextView) itemView.findViewById(R.id.subtitle_tv);
            mcommentaires = (TextView) itemView.findViewById(R.id.commentaires_tv);
            metat = (TextView) itemView.findViewById(R.id.etat_tv);
            mIcon = (ImageView) itemView.findViewById(R.id.row_icon);
        }


    }

}
