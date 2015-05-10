package com.pshegde.instagramclient;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Prajakta on 5/7/2015.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    //what data we need from activity
    //context, datasource
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }
    //what out item looks like
    //Use template to display each photo

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        //get the data item for this position
        InstagramPhoto photo = getItem(position);
        //check if we are using a recycled view, if not we need to inflate
        if (convertView == null){
            //create new view from template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo,parent,false);
        }
        //lookup views for popukating data (image,caption)
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivUserPic = (ImageView) convertView.findViewById(R.id.ivUserPic);
        TextView tvRelativePostingTime = (TextView) convertView.findViewById(R.id.tvRelativePostingTime);
        Button btnLikesCount = (Button) convertView.findViewById(R.id.btnLikesCount);
        TextView tvComments = (TextView) convertView.findViewById(R.id.tvComments);
        //insert the model data into each of the view items
        tvCaption.setText(photo.getCaption());
        //clear the imageview
        ivPhoto.setImageResource(0);
        //insert image using picasso
        Picasso.with(getContext()).load(photo.getImageUrl()).placeholder(R.drawable.placeholder).into(ivPhoto);
        //Picasso.with(getContext()).load(photo.userProfilePic).into(ivUserPic);
        //if(photo.getUserProfilePic()!="")
        Picasso.with(getContext()).load(photo.getUserProfilePic()).placeholder(R.drawable.placeholder).transform(new CircleTransform()).into(ivUserPic);
        tvRelativePostingTime.setText(photo.getRelativePostingTime());
        if(photo.getLikeCount() > 0) {
            btnLikesCount.setText(photo.getLikeCount() + " likes");
            btnLikesCount.setVisibility(View.VISIBLE);
        } else {
            btnLikesCount.setVisibility(View.INVISIBLE);
        }
        //display comments
        StringBuffer commentText=new StringBuffer();
        if(photo.getComments().size() > 0) {
            tvComments.setVisibility(View.VISIBLE);
            for(InstagramComment comment:photo.getComments()) {
                commentText.append("<font color='#407399'>" + comment.getUsername() + "</font>&nbsp;" + comment.getText() + "<br>");
                break;
            }
            commentText.append("<br><font color='#407399'>View more comments</font><br>");
            //tvComments.setTextColor();
            tvComments.setText(Html.fromHtml(commentText.toString()));
           // String styledText =""; cache.timeView.setText(Html.fromHtml(styledText) )
        } else {
            tvComments.setVisibility(View.INVISIBLE);
        }
        //return the created item as a view
        return convertView;
    }
}
