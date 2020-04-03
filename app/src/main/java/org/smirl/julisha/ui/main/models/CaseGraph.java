package org.smirl.julisha.ui.main.models;

public class CaseGraph {
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
        ", date='" + date + '\'' +
        ", infected=" + infected +
        ", dead=" + dead +
        ", healed=" + healed +
        '}';
  }
}
