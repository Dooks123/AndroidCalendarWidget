package com.dooks123.androidcalendarwidget.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.dooks123.androidcalendarwidget.R;
import com.dooks123.androidcalendarwidget.object.CalendarEvent;
import com.dooks123.androidcalendarwidget.queries.CalendarQuery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventListAdapter extends ArrayAdapter<CalendarEvent> {
    private @ColorInt int textColor;

    public EventListAdapter(@NonNull Context context) {
        super(context, R.layout.event_remote_view);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_remote_view, parent, false);
            viewHolder = new EventViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (EventViewHolder) convertView.getTag();
        }

        viewHolder.set(getItem(position), textColor);

        return convertView;
    }

    public void setItems(@ColorInt int textColor) {
        this.textColor = textColor;

        clear();
        addAll(CalendarQuery.getCalendarEventsForToday());
        sort((a, b) -> {
            if (a.isALL_DAY()) {
                return -1;
            }
            return Long.compare(a.getDTSTART(), b.getDTSTART());
        });

        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getID();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    public static class EventViewHolder {
        private CalendarEvent item;
        @ColorInt
        int textColor;

        private final LinearLayout eventContainer;
        private final LinearLayout eventColor;
        private final TextView eventTitle;
        private final TextView eventDuration;

        public EventViewHolder(@NonNull View view) {
            eventContainer = view.findViewById(R.id.eventContainer);
            eventColor = view.findViewById(R.id.eventColor);
            eventTitle = view.findViewById(R.id.eventTitle);
            eventDuration = view.findViewById(R.id.eventDuration);
        }

        public void set(CalendarEvent event, @ColorInt int textColor) {
            item = event;
            this.textColor = textColor;

            int color = eventColor();
            eventContainer.setBackgroundColor(backgroundColor(color));
            eventColor.setBackgroundColor(color);
            eventTitle.setText(item.getTITLE());
            eventTitle.setTextColor(color);
            eventDuration.setText(eventDuration());
            eventDuration.setTextColor(color);
        }

        @ColorInt
        private int eventColor() {
            if (item.getEVENT_COLOR() == 0) {
                return textColor;
            }
            return item.getEVENT_COLOR();
        }

        @ColorInt
        private int backgroundColor(@ColorInt int color) {
            int alpha = Math.round(Color.alpha(color) * 0.1f);
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            return Color.argb(alpha, red, green, blue);
        }

        private String eventDuration() {
            if (item.isALL_DAY()) {
                return "All day";
            } else if (item.getDTSTART() > 0 && item.getDTEND() > 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String start = sdf.format(new Date(item.getDTSTART()));
                String end = sdf.format(new Date(item.getDTEND()));
                return start + " - " + end;
            } else {
                return item.getDURATION();
            }
        }
    }
}
