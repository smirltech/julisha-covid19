package org.smirl.julisha.ui.main.models;

public class CaseGraph implements Comparable<CaseGraph>{
  public int id;
  public String date;
  public int infected;
  public int dead;
  public int healed;

  public CaseGraph(int id, String date) {
    this.id = id;
    this.date = date;
  }

  @Override
  public String toString() {
    return "CaseGraph{" +
        "id=" + id +
        ", date='" + date +
        ", infected=" + infected +
        ", dead=" + dead +
        ", healed=" + healed +
        '}';
  }

  public boolean isZeroed(){
    return infected == 0 && dead == 0 && healed == 0;
  }

  @Override
  public int compareTo(CaseGraph p1) {
    // TODO: Implement this method
    return Integer.compare(p1.infected, infected);
  }

  @Override
  public boolean equals(Object obj) {
    // TODO: Implement this method
    return Integer.compare(((CaseGraph)obj).infected, infected) == 0;
  }
}
