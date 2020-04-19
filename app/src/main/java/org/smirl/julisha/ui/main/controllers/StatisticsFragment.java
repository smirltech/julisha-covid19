package org.smirl.julisha.ui.main.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.smirl.julisha.MainActivity;
import org.smirl.julisha.R;
import org.smirl.julisha.core.Julisha;
import org.smirl.julisha.ui.main.models.TableData;
import org.smirl.julisha.ui.main.views.DetailsActivity;
import org.smirl.julisha.ui.main.views.StatisticsViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class StatisticsFragment extends Fragment {

  private static final String ARG_SECTION_NUMBER = "section_number";
  private MainActivity mActivity;

  private StatisticsViewModel statisticsViewModel;

  TableLayout tableDisplay;

  public static StatisticsFragment newInstance(MainActivity activity, int index) {
    StatisticsFragment fragment = new StatisticsFragment();
    fragment.mActivity = activity;
    Bundle bundle = new Bundle();
    bundle.putInt(ARG_SECTION_NUMBER, index);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    statisticsViewModel = ViewModelProviders.of(this).get(StatisticsViewModel.class);
    int index = 1;
    if (getArguments() != null) {
      index = getArguments().getInt(ARG_SECTION_NUMBER);
    }
    statisticsViewModel.setIndex(index);
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.statistics_fragment_main, container, false);
    tableDisplay = root.findViewById(R.id.tableDisplay);
    tableDisplay.removeViews(1, tableDisplay.getChildCount() - 1);
    populateTable();


    return root;
  }


  public void populateTable() {
    //Utilities.toastIt(mActivity, "Refresh done!");

    int _i = 1;
    for (final TableData c : Julisha.getProvincesTableData()) {
      try {
        TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.item_row_model, null, false);
        TextView tv0 = row.findViewById(R.id.tr_prov);

        tv0.setText(c.name.toUpperCase());
        tv0.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent ii = new Intent(getActivity(), DetailsActivity.class);
            ii.putExtra("province_id", c.id);
            getContext().startActivity(ii);
          }
        });
        ((TextView) row.findViewById(R.id.tr_no)).setText((_i++) + "");
        ((TextView) row.findViewById(R.id.tr_inf)).setText(c.infected + "");
        ((TextView) row.findViewById(R.id.tr_dec)).setText(c.dead + "");
        ((TextView) row.findViewById(R.id.tr_guer)).setText(c.healed + "");

        tableDisplay.addView(row);

        tableDisplay.invalidate();
        tableDisplay.refreshDrawableState();
      } catch (Exception eerr) {
      }
    }
  }
}