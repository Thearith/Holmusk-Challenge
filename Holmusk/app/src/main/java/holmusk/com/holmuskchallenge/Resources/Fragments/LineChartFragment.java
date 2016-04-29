package holmusk.com.holmuskchallenge.Resources.Fragments;

import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.LineChartView;
import com.db.chart.view.Tooltip;
import com.db.chart.view.animation.Animation;
import com.db.chart.view.animation.easing.BounceEase;

import holmusk.com.holmuskchallenge.R;

/**
 * Created by thearith on 25/4/16.
 */

public class LineChartFragment extends Fragment {

    // constants
    private static final String CHART_TITLE         = "chart_title";
    private static final String CHART_LABELS        = "chart_labels";
    private static final String CHART_VALUES        = "chart_values";


    // widgets
    private Tooltip mTip;

    private String chartTitle;
    private String[] chartLabels;
    private float[] chartValues;

    /*
    * public methods
    * */

    public static LineChartFragment newInstance(String title, String[] labels, float[] values) {
        Bundle bundle = new Bundle();
        bundle.putString(CHART_TITLE, title);
        bundle.putStringArray(CHART_LABELS, labels);
        bundle.putFloatArray(CHART_VALUES, values);

        LineChartFragment graphChart = new LineChartFragment();
        graphChart.setArguments(bundle);

        return graphChart;
    }


    /*
    * Overriden
    * */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_line_chart, container, false);

        Bundle bundle = getArguments();
        initializeVariables(bundle);

        setUpWidgets(view);

        return view;
    }


    /*
    * variable methods
    * */

    private void initializeVariables(Bundle bundle) {
        iniitalizeChartTitle(bundle);
        initializeChartLabels(bundle);
        initializeChartValues(bundle);
    }

    private void iniitalizeChartTitle(Bundle bundle) {
        chartTitle = bundle.getString(CHART_TITLE);
    }

    private void initializeChartLabels(Bundle bundle) {
        chartLabels = bundle.getStringArray(CHART_LABELS);
    }

    private void initializeChartValues(Bundle bundle) {
        chartValues = bundle.getFloatArray(CHART_VALUES);
    }


    /*
    * widgets methods
    * */

    private void setUpWidgets(View view) {
        initializeTooltip();
        initializeChartContainer(view);
    }

    private void initializeTooltip() {
        mTip = new Tooltip(getActivity(), R.layout.fragment_line_chart_tooltip, R.id.value);
        mTip.setVerticalAlignment(Tooltip.Alignment.TOP_TOP);
        mTip.setDimensions((int) Tools.fromDpToPx(65), (int) Tools.fromDpToPx(25));

        mTip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

        mTip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

        mTip.setPivotX(Tools.fromDpToPx(65) / 2);
        mTip.setPivotY(Tools.fromDpToPx(0));
    }


    private void initializeChartContainer(View view) {
        initializeChartTitle(view);

        if(chartLabels.length > 0 && chartValues.length > 0
                && chartLabels.length == chartValues.length) {
            initializeChart(view);
        }
    }

    private void initializeChartTitle(View view) {
        TextView stepLengthChartTitle = (TextView) view.findViewById(R.id.lineChartTitle);
        stepLengthChartTitle.setText(chartTitle);
    }

    private void initializeChart(View view) {
        final LineChartView lineChart = (LineChartView) view.findViewById(R.id.lineChart);

        lineChart.setTooltips(mTip);

        LineSet dataSet = new LineSet(chartLabels, chartValues);
        dataSet.setColor(Color.parseColor("#b3b5bb"))
                .setFill(Color.TRANSPARENT)
                .setDotsColor(Color.parseColor("#ffc755"))
                .setThickness(4);
        lineChart.addData(dataSet);

        lineChart.setBorderSpacing(Tools.fromDpToPx(15))
                .setYLabels(AxisController.LabelPosition.NONE)
                .setLabelsColor(Color.parseColor("#6a84c3"))
                .setXAxis(false)
                .setYAxis(false);

        Animation anim = new Animation()
                .setEasing(new BounceEase());

        lineChart.show(anim);
    }

}
