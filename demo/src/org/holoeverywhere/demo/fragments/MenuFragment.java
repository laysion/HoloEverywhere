
package org.holoeverywhere.demo.fragments;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.addon.AddonSlider.AddonSliderA;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.app.ListFragment;
import org.holoeverywhere.demo.DemoActivity;
import org.holoeverywhere.demo.R;
import org.holoeverywhere.demo.fragments.about.AboutFragment;
import org.holoeverywhere.demo.widget.DemoAdapter;
import org.holoeverywhere.demo.widget.DemoItem;
import org.holoeverywhere.demo.widget.DemoThemePicker;
import org.holoeverywhere.widget.ListView;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

public class MenuFragment extends ListFragment {
    private final class MenuItem extends DemoItem {
        public Class<? extends Fragment> clazz;
    }

    public static interface OnMenuClickListener {
        public void onMenuClick(int position);
    }

    private static final String KEY_PAGE = "page";
    private DemoAdapter<MenuItem> mAdapter;
    private int mCurrentPage = -1;
    private Handler mHandler;
    private OnMenuClickListener mOnMenuClickListener;

    public void add(Class<? extends Fragment> clazz, int selectionHandlerColor, CharSequence label) {
        MenuItem item = new MenuItem();
        item.clazz = clazz;
        item.label = label;
        item.selectionHandlerColor = getSupportActivity().getResources().getColor(
                selectionHandlerColor);
        mAdapter.add(item);
    }

    public void add(Class<? extends Fragment> clazz, int selectionHandlerColor, int label) {
        add(clazz, selectionHandlerColor, getSupportActivity().getText(label));
    }

    private void fillAdapter() {
        add(MainFragment.class, R.color.holo_blue_dark, R.string.demo);
        add(SettingsFragment.class, R.color.holo_green_dark, R.string.settings);
        add(OtherFragment.class, R.color.holo_orange_dark, R.string.other);
        add(AboutFragment.class, R.color.holo_purple, R.string.about);
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(getSupportActionBarContext());
    }

    @Override
    public DemoActivity getSupportActivity() {
        return (DemoActivity) super.getSupportActivity();
    }

    @Override
    public void onAttach(Activity activity) {
        mHandler = new Handler(activity.getMainLooper());
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentPage = savedInstanceState.getInt(KEY_PAGE, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu, container, false);
    }

    @Override
    public void onDetach() {
        mHandler = null;
        super.onDetach();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        setMenuSelection(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_PAGE, mCurrentPage);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((DemoThemePicker) view.findViewById(R.id.themePicker)).setActivity(getSupportActivity());
        setListAdapter(mAdapter = new DemoAdapter<MenuItem>(getSupportActionBarContext()));
        fillAdapter();
        if (getSupportActivity().isFirstRun()) {
            setMenuSelection(0);
        }
    }

    public void setMenuSelection(int position) {
        if (mAdapter == null) {
            return;
        }
        final FragmentManager fm = getFragmentManager();
        final MenuItem item = mAdapter.getItem(position);
        if (mCurrentPage != position || fm.getBackStackEntryCount() > 0) {
            if (mCurrentPage >= 0) {
                mAdapter.getItem(mCurrentPage).selectionHandlerVisible = false;
            }
            mCurrentPage = position;
            while (fm.popBackStackImmediate()) {
            }
            if (item != null) {
                item.selectionHandlerVisible = true;
                getSupportActivity().replaceFragment(Fragment.instantiate(item.clazz));
            }
            final AddonSliderA slider = getSupportActivity().addonSlider();
            if (slider.isAddonEnabled() && mHandler != null) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        slider.openContentView();
                    }
                }, 50);
            }
            mAdapter.notifyDataSetInvalidated();
            if (mOnMenuClickListener != null) {
                mOnMenuClickListener.onMenuClick(position);
            }
        }
    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        mOnMenuClickListener = onMenuClickListener;
    }

}
