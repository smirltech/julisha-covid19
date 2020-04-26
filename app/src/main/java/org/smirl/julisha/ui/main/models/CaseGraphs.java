package org.smirl.julisha.ui.main.models;

import java.util.ArrayList;

public class CaseGraphs extends ArrayList<CaseGraph> {

    public boolean newCaseGraph(CaseGraph cg) {
        for (CaseGraph c : this) {
            if (c.date.equalsIgnoreCase(cg.date)) return false;
        }
        add(cg);
        return true;
    }

    public CaseGraph getCaseGraph(String date) {
        for (CaseGraph c : this) {
            if (c.date.equalsIgnoreCase(date)) return c;
        }
        return null;
    }

    public CaseGraph getCaseGraph(int id) {
        for (CaseGraph c : this) {
            if (c.id == id) return c;
        }
        return null;
    }

    public CaseGraph getLatestCaseGraph() {
      if (size() > 0)  return get(size()-1);
      return null;
    }



    public int getMaxInfected() {
        int y = 0;
        for (CaseGraph c : this) {
            if (c.infected > y)
                y = c.infected;
        }
        return y;
    }

    public int getMaxDead() {
        int y = 0;
        for (CaseGraph c : this) {
            if (c.dead > y)
                y = c.dead;
        }
        return y;
    }

    public int getMaxHealed() {
        int y = 0;
        for (CaseGraph c : this) {
            if (c.healed > y)
                y = c.healed;
        }
        return y;
    }

    public int getMax() {
        int y = getMaxInfected();
        if (getMaxHealed() > y)
            y = getMaxHealed();
        if (getMaxDead() > y)
            y = getMaxDead();
        return y;
    }
}
