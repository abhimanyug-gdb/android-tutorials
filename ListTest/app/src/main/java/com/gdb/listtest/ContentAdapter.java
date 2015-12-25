package com.gdb.listtest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devdatta on 24/12/15.
 */
public class ContentAdapter extends BaseAdapter implements Filterable{
    private List<ListContent> list = null;
    private LayoutInflater inflater;

    public ContentAdapter(Context context, List<ListContent> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ListContent currentList = list.get(position);

        viewHolder.textViewTitle.setText(currentList.getTitle());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<ListContent> filteredList= new ArrayList<>();

                constraint = constraint.toString().toLowerCase();
                for (int i=0; i < list.size() ;i++) {
                    String name = list.get(i).getTitle();
                    if (name.toLowerCase().startsWith(constraint.toString())) {
                        filteredList.add(list.get(i));
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
                Log.d("Filter Result : ",results.values.toString());
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<ListContent>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }


    public class ViewHolder{
        TextView textViewTitle;

        public ViewHolder(View view) {
            textViewTitle = (TextView) view.findViewById(R.id.list_item_text);
        }
    }

}
