package org.notfunnynerd.opencoinmap.utils;

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
	/** The inflator used to inflate the XML layout */
	private LayoutInflater inflator;

	/** A list containing some sample data to show. */
	private List<Filter> dataList;

	public FilterListAdapter(LayoutInflater inflator) {
		super();
		this.inflator = inflator;

		dataList = new ArrayList<Filter>();

		dataList.add(new Filter("Peter", false));
		dataList.add(new Filter("Bob", false));
		dataList.add(new Filter("Sara", true));
		dataList.add(new Filter("Mitch", false));
		dataList.add(new Filter("Tracy", false));
		dataList.add(new Filter("Joe", false));
		dataList.add(new Filter("George", false));
		dataList.add(new Filter("Nancy", false));
		dataList.add(new Filter("Susi", true));
		dataList.add(new Filter("Homer", false));
		dataList.add(new Filter("Lisa", false));
		dataList.add(new Filter("Jack", false));
	}

	@Override
	public int getCount() {
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

		// We only create the view if its needed
		if (view == null) {
			view = inflator.inflate(R.layout.filter_item, null);

			// Set the click listener for the checkbox
			view.findViewById(R.id.checkbox).setOnClickListener(this);

		}

		Filter data = (Filter) getItem(position);

		// Set the example text and the state of the checkbox
		CheckBox cb = (CheckBox) view.findViewById(R.id.checkbox);
		cb.setChecked(data.isSelected());
		// We tag the data object to retrieve it on the click listener.
		cb.setTag(data);

		TextView tv = (TextView) view.findViewById(R.id.textView1);
		tv.setText(data.getName());

		return view;
	}

	@Override
	/** Will be called when a checkbox has been clicked. */
	public void onClick(View v) {
		Filter data = (Filter) v.getTag();
		data.setSelected(((CheckBox) v).isChecked());
	}
}
