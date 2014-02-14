package com.tsmsogn.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private final static int MIN_YEAR = 1970;
    private final static int MAX_YEAR = 2170;
    public static final String TAG = MainActivity.class.getCanonicalName();

    public class PhotoPagerAdapter extends PagerAdapter {

        private ArrayList<Integer> mList;

        public PhotoPagerAdapter(ArrayList<Integer> list) {
            mList = list;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Integer current = mList.get(position);

            Pattern pattern = Pattern.compile("^(\\d{4})(\\d{2})$");
            Matcher matcher = pattern.matcher(String.valueOf(current));

            int year = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));

            View view = getLayoutInflater().inflate(R.layout.view_photo_pager,
                    null);

            GridView calendarView = (GridView) view.findViewById(R.id.calendar);
            TextView textView1 = (TextView) view.findViewById(R.id.textView1);

            // Initialised
            GridCellAdapter adapter = new GridCellAdapter(
                    getApplicationContext(), R.id.calendar_day_gridcell, month,
                    year);
            adapter.notifyDataSetChanged();
            calendarView.setAdapter(adapter);

            textView1.setText(String.valueOf(current));

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0.equals(arg1);
        }

    }

    private ViewPager mViewPager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        mViewPager1 = (ViewPager) findViewById(R.id.viewPager1);

        // Create year/month list
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = MIN_YEAR; i <= MAX_YEAR; i++) {
            for (int j = 1; j <= 12; j++) {
                list.add(Integer.parseInt(String.format("%04d%02d", i, j)));
            }
        }

        mViewPager1.setAdapter(new PhotoPagerAdapter(list));
        mViewPager1.setCurrentItem(list.indexOf(Integer.parseInt(String.format(
                "%04d%02d", year, month))));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
