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
import org.smirl.julisha.core.Utilities;
import org.smirl.julisha.ui.main.models.Cases;
import org.smirl.julisha.ui.main.models.Provinces;
import org.smirl.julisha.ui.main.models.Villes;
import org.smirl.julisha.ui.main.views.DetailsActivity;
import org.smirl.julisha.ui.main.views.StatisticsViewModel;

import java.util.HashSet;

/**
 * A placeholder fragment containing a simple view.
 */
public class StatisticsVillesFragment extends Fragment {

  private static final String ARG_SECTION_NUMBER = "section_number";
  private MainActivity mActivity;

  private StatisticsViewModel statisticsViewModel;

  TableLayout tableDisplay;

  public static StatisticsVillesFragment newInstance(MainActivity activity, int index) {
    StatisticsVillesFragment fragment = new StatisticsVillesFragment();
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
    View root = inflater.inflate(R.layout.statistics_villes_fragment_main, container, false);
    tableDisplay = root.findViewById(R.id.tableDisplay);
    tableDisplay.removeViews(1, tableDisplay.getChildCount() - 1);
    populateTable();

    return root;
  }

  public void populateTable() {
    Cases cs = Julisha.cases();
    Villes vl = Julisha.villes();

    HashSet<Integer> fs = cs.getVilleIds();
    int _i = 1;
    for (final int c : fs.toArray(new Integer[]{})) {
      try {
        TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.item_row_model, null, false);
        TextView tv0 = row.findViewById(R.id.tr_prov);

        tv0.setText(vl.getVille(c).nom.toUpperCase());
        ((TextView) row.findViewById(R.id.tr_no)).setText((_i++) + "");
        ((TextView) row.findViewById(R.id.tr_inf)).setText(cs.number(c, 1) + "");
        ((TextView) row.findViewById(R.id.tr_dec)).setText(cs.number(c, 2) + "");
        ((TextView) row.findViewById(R.id.tr_guer)).setText(cs.number(c, 3) + "");

        tableDisplay.addView(row);

        tableDisplay.invalidate();
        tableDisplay.refreshDrawableState();
      } catch (Exception eerr) {
      }
    }
  }
}