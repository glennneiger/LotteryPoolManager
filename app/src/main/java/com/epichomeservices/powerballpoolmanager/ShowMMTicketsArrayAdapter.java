package com.epichomeservices.powerballpoolmanager;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ShowMMTicketsArrayAdapter extends ArrayAdapter<MegaMillionsTickets>{

	private final Activity context;
	private final List<MegaMillionsTickets> mmTickets;
	
	public ShowMMTicketsArrayAdapter(Activity context, List<MegaMillionsTickets> mmTickets) {
		super(context, R.layout.show_tickets_list, mmTickets);
		this.context = context;
		this.mmTickets = mmTickets;
		
	}
	
	
	static class ViewHolder {
		
		protected TextView num1TextView;
		protected TextView num2TextView;
		protected TextView num3TextView;
		protected TextView num4TextView;
		protected TextView num5TextView;
		protected TextView numMBTextView;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.show_tickets_list, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.num1TextView = (TextView) rowView.findViewById(R.id.number1TextView);
			viewHolder.num2TextView = (TextView) rowView.findViewById(R.id.number2TextView);
			viewHolder.num3TextView = (TextView) rowView.findViewById(R.id.number3TextView);
			viewHolder.num4TextView = (TextView) rowView.findViewById(R.id.number4TextView);
			viewHolder.num5TextView = (TextView) rowView.findViewById(R.id.number5TextView);
			viewHolder.numMBTextView = (TextView) rowView.findViewById(R.id.numberPBTextView);
			
			rowView.setTag(viewHolder);
			//viewHolder.checkBox.setTag(contacts.get(position));
			
		} 
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.num1TextView.setText(String.format("%02d", mmTickets.get(position).getNumber1()));
		holder.num2TextView.setText(String.format("%02d", mmTickets.get(position).getNumber2()));
		holder.num3TextView.setText(String.format("%02d", mmTickets.get(position).getNumber3()));
		holder.num4TextView.setText(String.format("%02d", mmTickets.get(position).getNumber4()));
		holder.num5TextView.setText(String.format("%02d", mmTickets.get(position).getNumber5()));
		holder.numMBTextView.setText(String.format("%02d", mmTickets.get(position).getNumberMB()));
		
		return rowView;
		
	}

}
