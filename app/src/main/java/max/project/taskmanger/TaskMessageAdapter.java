package max.project.taskmanger;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

public class TaskMessageAdapter extends ArrayAdapter<TaskMessage> {


    private List<TaskMessage> messages;
    private Activity activity;


    public TaskMessageAdapter( Activity context , int resource, List<TaskMessage> messages) {
        super(context, resource, messages);
        this.messages = messages;
        this.activity = context;
    }


    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);




        TaskMessage taskMessage = getItem(position);
        int layoutResource = 0;
        int viewType = getItemViewType(position);

        if(viewType == 0){
            layoutResource = R.layout.my_message_item;
        }else{
            layoutResource = R.layout.you_message_item;
        }


        if(convertView != null){
            viewHolder = (ViewHolder)convertView.getTag();
        }else{
            convertView = layoutInflater.inflate(layoutResource , parent , false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }


        boolean isText = taskMessage.getImageUrl() == null;

        if(isText){
            viewHolder.messageTextView.setVisibility(View.VISIBLE);
            viewHolder.photoImageVew.setVisibility(View.GONE);
            viewHolder.messageTextView.setText(taskMessage.getText());
        }else{
            viewHolder.messageTextView.setVisibility(View.GONE);
            viewHolder.photoImageVew.setVisibility(View.VISIBLE);
            Glide.with(viewHolder.photoImageVew.getContext())
                    .load(taskMessage.getImageUrl())
                    .into(viewHolder.photoImageVew);
        }
        viewHolder.messageDataTextView.setText(taskMessage.getDate());
        return convertView;
    }


    @Override
    public int getItemViewType(int position) {

        int flag;
        TaskMessage taskMessage = messages.get(position);
        if(taskMessage.isMine()){
            flag = 0;
        }else{
            flag = 1;
        }

        return flag;
    }

    @Override
    public int getViewTypeCount() {

        return 2;


    }

    private class ViewHolder{

        private ImageView photoImageVew;
        private TextView messageTextView;
        private TextView messageDataTextView;

        public ViewHolder(View view){
            photoImageVew = view.findViewById( R.id.photoImageVew);
            messageTextView = view.findViewById(R.id.messageTextView);
            messageDataTextView = view.findViewById(R.id.messageDataTextView);


       }



    }

}
