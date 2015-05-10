package com.pshegde.instagramclient;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Prajakta on 5/10/2015.
 */
public class InstagramCommentAdapter extends ArrayAdapter<InstagramComment> {
    //what data we need from activity
    //context, datasource

    public InstagramCommentAdapter(Context context, List<InstagramComment> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        //get the data item for this position
        final InstagramComment comment = getItem(position);
        //check if we are using a recycled view, if not we need to inflate
        if (convertView == null) {
            //create new view from template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
        }
        TextView tvComment = (TextView) convertView.findViewById(R.id.tvComment);
        ImageView ivUserPic = (ImageView) convertView.findViewById(R.id.ivUserPicForComment);
        TextView tvPostingTime = (TextView) convertView.findViewById(R.id.tvRelativePostingTimeComment);

        Picasso.with(getContext()).load(comment.getProfilePic()).placeholder(R.drawable.placeholder).into(ivUserPic);
        tvComment.setText(Html.fromHtml("&nbsp;<font color='#407399'>" + comment.getUsername() + "</font>&nbsp;" + comment.getText() + "<br>"));
        tvPostingTime.setText(Html.fromHtml("&nbsp;"+ comment.getCreatedTime()));
        return convertView;
    }
}
