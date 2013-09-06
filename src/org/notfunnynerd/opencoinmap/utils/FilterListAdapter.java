package org.notfunnynerd.opencoinmap.utils;

/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative
 * Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
 * 
 * Largely inspired by Prusnak's work : https://github.com/prusnak/coinmap
 * Map Data CC-BY-SA by OpenStreetMap http://openstreetmap.org/
 * Icons CC-0 by Brian Quinion http://www.sjjb.co.uk/mapicons/
 * 
 * @author NotFunnyNerd <notfunnynerd@gmail.com> - 2013
 * 
 */

import java.util.ArrayList;
import java.util.List;

import org.notfunnynerd.opencoinmap.R;
import org.notfunnynerd.opencoinmap.models.Filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class FilterListAdapter extends BaseAdapter implements OnClickListener {
	private LayoutInflater inflator;

	private List<Filter> dataList;

	public FilterListAdapter(LayoutInflater inflator,
			ArrayList<Filter> filterList) {
		super();
		this.inflator = inflator;

		dataList = filterList;
	}

	@Override
	public int getCount() {
		if (dataList == null) {
			return 0;
		}
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {

		if (view == null) {
			view = inflator.inflate(R.layout.filter_item, null);
			view.findViewById(R.id.checkbox).setOnClickListener(this);
		}

		Filter filter = (Filter) getItem(position);

		// Set the example text and the state of the checkbox
		CheckBox cb = (CheckBox) view.findViewById(R.id.checkbox);
		cb.setChecked(filter.isSelected());
		// We tag the data object to retrieve it on the click listener.
		cb.setTag(filter);

		TextView tv = (TextView) view.findViewById(R.id.caption);
		tv.setText(filter.getBeautifulName());

		return view;
	}

	@Override
	public void onClick(View v) {
		Filter filter = (Filter) v.getTag();
		filter.setSelected(((CheckBox) v).isChecked());
	}
}
