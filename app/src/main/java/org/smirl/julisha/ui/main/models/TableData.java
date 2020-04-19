package org.smirl.julisha.ui.main.models;

import java.util.Objects;

public class TableData implements Comparable<TableData> {
  public  int id;
  public String name;
  public int infected, dead, healed;

  public TableData(int id, String name, int infected, int dead, int healed) {
    this.id = id;
    this.name = name;
    this.infected = infected;
    this.dead = dead;
    this.healed = healed;
  }

  @Override
  public int compareTo(TableData td) {
    return Integer.compare(td.infected, infected);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TableData tableData = (TableData) o;
    return infected == tableData.infected;
  }

  @Override
  public int hashCode() {
    return Objects.hash(infected);
  }
}
