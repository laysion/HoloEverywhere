
package org.holoeverywhere.demo.widget;

import org.holoeverywhere.FontLoader;

import android.view.View;
import android.view.ViewGroup;

public class DemoItem {
    public CharSequence label;
    public View lastView;
    public boolean longClickable = false;
    public int selectionHandlerColor = 0;
    public boolean selectionHandlerVisible = false;

    public DemoItem() {

    }

    public int getItemViewType() {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        DemoItemView view = makeView(convertView, parent);
        view.setLabel(label);
        view.setSelectionHandlerVisiblity(selectionHandlerVisible);
        if (selectionHandlerVisible) {
            view.setSelectionHandlerColor(selectionHandlerColor);
        }
        return view;
    }

    protected DemoItemView makeView(View convertView, ViewGroup parent) {
        if (convertView == null) {
            return FontLoader.apply(new DemoItemView(parent.getContext()));
        } else {
            return (DemoItemView) convertView;
        }
    }

    public void onClick() {

    }

    public boolean onLongClick() {
        return false;
    }
}
