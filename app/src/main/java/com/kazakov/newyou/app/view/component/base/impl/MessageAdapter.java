package com.kazakov.newyou.app.view.component.base.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kazakov.newyou.app.R;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessageAdapter extends BaseAdapter {

    private static final int MAX_MESSAGES_TO_DISPLAY = 10;

    private List<String> messages;
    private LayoutInflater inflater;

    public MessageAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        this.messages = new CopyOnWriteArrayList<>();
    }

    public Runnable addMessageTask(String msg) {
        return () -> {
            if (messages.size() == MAX_MESSAGES_TO_DISPLAY) {
                messages.remove(0);
                messages.add(msg);
            } else {
                messages.add(msg);
            }
            notifyDataSetChanged();
        };
    }

    public void clear() {
        messages.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View messageRecordView = null;
        if (inflater != null) {
            messageRecordView = inflater.inflate(R.layout.message, null);
            TextView tvData = messageRecordView.findViewById(R.id.tvData);
            String message = (String) getItem(position);
            tvData.setText(message);
        }
        return messageRecordView;
    }
}
