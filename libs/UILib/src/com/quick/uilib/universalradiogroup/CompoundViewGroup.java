package com.quick.uilib.universalradiogroup;

/**
 * Marker interface to create type hierarchy of the compound view groups.
 * @author KeithYokoma
 * @since 2014/03/16
 */
public interface CompoundViewGroup {
	public interface OnCheckedChangeListener {
	    public void onCheckedChanged(CompoundViewGroup compoundView, boolean isChecked);
	}
	void setOnCheckedChangeWidgetListener(OnCheckedChangeListener listener) ;
	int getId();
	boolean isChecked();
	void setChecked(boolean checked);
}