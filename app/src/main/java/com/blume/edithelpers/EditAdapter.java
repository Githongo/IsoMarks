package com.blume.edithelpers;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.blume.isomarks.R;

import java.util.ArrayList;

public class EditAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<EditModel> editModelArrayList;

    public EditAdapter(Context context, ArrayList<EditModel> editModelArrayList) {

        this.context = context;
        this.editModelArrayList = editModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }
    @Override
    public int getCount() {
        return editModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return editModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_editmarks, null, true);

            holder.editText = (EditText) convertView.findViewById(R.id.editid);
            holder.admTextView = (TextView) convertView.findViewById(R.id.adm_no);
            holder.nameTextView = (TextView) convertView.findViewById(R.id.nameText);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.editText.setText(editModelArrayList.get(position).getEditTextValue());
        holder.admTextView.setText(editModelArrayList.get(position).getAdmTextValue());
        holder.nameTextView.setText(editModelArrayList.get(position).getNameTextValue());

        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editModelArrayList.get(position).setEditTextValue(holder.editText.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        return convertView;
    }

    private class ViewHolder {

        public TextView admTextView;
        public TextView nameTextView;
        protected EditText editText;

    }
}
