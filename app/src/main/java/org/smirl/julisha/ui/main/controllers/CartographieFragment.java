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
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.smirl.julisha.MainActivity;
import org.smirl.julisha.R;
import org.smirl.julisha.core.Julisha;
import org.smirl.julisha.ui.main.models.CaseGraphs;
import org.smirl.julisha.ui.main.models.TableData;
import org.smirl.julisha.ui.main.views.DetailsActivity;
import org.smirl.julisha.ui.main.views.LoneGraph;
import org.smirl.julisha.ui.main.views.MapMe;
import org.smirl.julisha.ui.main.views.StatisticsViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class CartographieFragment extends Fragment {

  private static final String ARG_SECTION_NUMBER = "section_number";
  private MainActivity mActivity;
  private MapMe provsMap;

  public static CartographieFragment newInstance(MainActivity activity, int index) {
    CartographieFragment fragment = new CartographieFragment();
    fragment.mActivity = activity;
    Bundle bundle = new Bundle();
    bundle.putInt(ARG_SECTION_NUMBER, index);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.cartographie_fragment_main, container, false);
    provsMap = root.findViewById(R.id.provs_map);

    return root;
  }

}