/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package termproject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administrator
 */
class MyTableModel extends AbstractTableModel implements Observer {
    private String[] columnNames = {"           IP","        Status"};
    private Object[][] data;
    boolean c;
  //  AliveCheck ac=null;
    MyTableModel(int r,String ip1)
    {
        data=new Object[r+1][2];
        c=true;
        for(int i=0;i<=r;i++)
         {
             data[i][0]=ip1;
             data[i][1]="Scanning";
             new AliveCheck(i,ip1,this);
             String s=ip1.substring(0,ip1.lastIndexOf(".")+1);
             int n=Integer.parseInt(ip1.substring(ip1.lastIndexOf(".")+1, ip1.length()))+1;
             ip1=s+n;
                   //  System.out.println(ip1);
         }
       
    }
    public void update(Observable o,Object a)
    {
         String z[]=((String)a).split("#");
         if(data[Integer.parseInt(z[1])][1]!=z[0])
         {
             data[Integer.parseInt(z[1])][1]=z[0];
             fireTableRowsUpdated(Integer.parseInt(z[1]),Integer.parseInt(z[1]));
         }
    }

     public int getColumnCount() {
        return columnNames.length;
    }
    public int getRowCount() {
        return data.length;
    }

        @Override
        public String getColumnName(int col) {
        return columnNames[col];
    }

   public Object getValueAt(int row, int col) {
        return data[row][col];
    }


        @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
       @Override
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
        @Override
  public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

}
class TableModel extends AbstractTableModel{
    private static final String[] columnnames={"     Restricted Processes"};
    private ArrayList <String> exelist=new ArrayList<String>();
    private static final Class[] columnClasses = {String.class};

    public void addExe(String exe){
      exelist.add(exe);
      fireTableRowsInserted(getRowCount()-1,getRowCount()-1);
    }

   public String getExe(int row){
       return exelist.get(row);
   }
   public void clearExe(int row){
      exelist.remove(row);
      fireTableRowsDeleted(row,row);
   }

  public int getRowCount() {
       return exelist.size();
    }

    public int getColumnCount() {
        return columnnames.length;
    }
    public Class getColumnClass(int index){
		return columnClasses[index];
	}
    public String getColumnName(int col){
       return columnnames[col];
    }
    public Object getValueAt(int row, int col) {
    String exe=exelist.get(row);
      switch(col){

          case 0:
              return exe;
        }
      return "";
    }
}

