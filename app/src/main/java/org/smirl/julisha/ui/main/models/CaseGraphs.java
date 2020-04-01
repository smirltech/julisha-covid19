package org.smirl.julisha.ui.main.models;

import java.util.ArrayList;

public class CaseGraphs extends ArrayList<CaseGraph> {

  public boolean newCaseGraph(CaseGraph cg){
    for(CaseGraph c : this){
      if(c.date.equalsIgnoreCase(cg.date)) return false;
    }
    add(cg);
    return true;
  }

  public CaseGraph getCaseGraph(String date){
    for(CaseGraph c : this){
      if(c.date.equalsIgnoreCase(date)) return c;
    }
    return null;
  }

  public CaseGraph getCaseGraph(int id){
    for(CaseGraph c : this){
      if(c.id == id) return c;
    }
    return null;
  }
}
