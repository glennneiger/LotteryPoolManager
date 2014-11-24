package com.epichomeservices.powerballpoolmanager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class ContactsArrayAdapter extends ArrayAdapter<Contacts> {

	private final List<Contacts> contacts;
	private final Activity context;

	public ContactsArrayAdapter(Activity context, List<Contacts> contacts) {
		super(context, R.layout.show_contacts, contacts);
		this.context = context;
		this.contacts = contacts;
	}

	static class ViewHolder {
		protected TextView textView;
		protected CheckBox checkBox;
	}

	@Override
	public View getView(int position, View concertView, ViewGroup parent) {
		View view = null;
		if (concertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			view = inflater.inflate(R.layout.show_contacts, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) view.findViewById(R.id.label);
			viewHolder.checkBox = (CheckBox) view.findViewById(R.id.check);
			viewHolder.checkBox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							Contacts element = (Contacts) viewHolder.checkBox
									.getTag();
							element.setSelected(buttonView.isChecked());

						}
					});
			view.setTag(viewHolder);
			viewHolder.checkBox.setTag(contacts.get(position));
			
		} else {
			
			view = concertView;
			((ViewHolder) view.getTag()).checkBox.setTag(contacts.get(position));
			
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.textView.setText(contacts.get(position).getContactName());
		holder.checkBox.setChecked(contacts.get(position).isSelected());
		
		return view;
		
	}

}
