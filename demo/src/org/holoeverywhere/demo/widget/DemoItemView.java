
package org.holoeverywhere.demo.widget;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.demo.R;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.TextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

public class DemoItemView extends LinearLayout {
    private final TextView label;
    private final View selectionHandler;

    public DemoItemView(Context context) {
        this(context, null);
    }

    public DemoItemView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.demoListRowViewStyle);
    }

    @SuppressWarnings("deprecation")
    public DemoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DemoItemView,
                defStyleAttr, R.style.Holo_Demo_ListRowView);
        final int gravity = a.getInt(R.styleable.DemoItemView_android_gravity, Gravity.LEFT), layout;
        if ((gravity & Gravity.LEFT) != 0) {
            layout = R.layout.demo_list_row_view_left;
        } else {
            layout = R.layout.demo_list_row_view_bottom;
        }
        LayoutInflater.inflate(context, layout, this, true);
        selectionHandler = findViewById(R.id.selectionHandler);
        label = (TextView) findViewById(android.R.id.text1);
        if (a.hasValue(R.styleable.DemoItemView_android_text)) {
            setLabel(a.getText(R.styleable.DemoItemView_android_text));
        }
        if (a.hasValue(R.styleable.DemoItemView_selectionHandler)) {
            setSelectionHandlerDrawable(a.getDrawable(R.styleable.DemoItemView_selectionHandler));
        }
        if (a.hasValue(R.styleable.DemoItemView_selectionHandlerVisiblity)) {
            setSelectionHandlerVisiblity(a.getInt(
                    R.styleable.DemoItemView_selectionHandlerVisiblity, 0) == 0);
        }
        if (a.hasValue(R.styleable.DemoItemView_android_background)) {
            setBackgroundDrawable(a.getDrawable(R.styleable.DemoItemView_android_background));
        }
        a.recycle();
    }

    public void setLabel(CharSequence label) {
        this.label.setText(label);
    }

    public void setLabel(int resId) {
        setLabel(getResources().getText(resId));
    }

    public void setSelectionHandlerColor(int color) {
        setSelectionHandlerDrawable(new ColorDrawable(color));
    }

    public void setSelectionHandlerColorResource(int resId) {
        setSelectionHandlerColor(getResources().getColor(resId));
    }

    @SuppressWarnings("deprecation")
    public void setSelectionHandlerDrawable(Drawable drawable) {
        selectionHandler.setBackgroundDrawable(drawable);
    }

    public void setSelectionHandlerDrawableResource(int resId) {
        setSelectionHandlerDrawable(getResources().getDrawable(resId));
    }

    public void setSelectionHandlerVisiblity(boolean visible) {
        setSelectionHandlerVisiblity(visible ? VISIBLE : INVISIBLE);
    }

    public void setSelectionHandlerVisiblity(int visiblity) {
        selectionHandler.setVisibility(visiblity);
    }
}
