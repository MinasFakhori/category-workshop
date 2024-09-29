package tk.minas.clients.customer;

import java.sql.SQLException;

/**
 * The Customer Controller
 * @author M A Smith (c) June 2014
 */

public class CustomerController
{
  private CustomerModel model = null;
  private CustomerView  view  = null;

  /**
   * Constructor
   * @param model The model 
   * @param view  The view from which the interaction came
   */
  public CustomerController( CustomerModel model, CustomerView view )
  {
    this.view  = view;
    this.model = model;
  }

  /**
   * Check interaction from view
   * @param pn The product number to be checked
   */
  public void doCheck( String pn ) {
    boolean out = false;
    try {
      int test = Integer.parseInt(pn);
      out =  model.doCheck(pn);
      }catch (Exception e) {
        out = model.findName(pn);
      }
  view.setBtnCheck(out);
  }





  /**
   * Clear interaction from view
   */
  public void doClear() {
    model.doClear();
  }

  public void getAll() throws SQLException {
    model.getAll();
  }

  
}

