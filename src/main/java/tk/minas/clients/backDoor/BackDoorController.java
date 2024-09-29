package tk.minas.clients.backDoor;



import tk.minas.clients.Sound;

/**
 * The BackDoor Controller
 * @author M A Smith (c) June 2014
 */

public class BackDoorController
{
  private BackDoorModel model = null;
  private BackDoorView  view  = null;

  private Sound sound = new Sound("src/main/resources/sound/noise.wav");
  /**
   * Constructor
   * @param model The model 
   * @param view  The view from which the interaction came
   */
  public BackDoorController( BackDoorModel model, BackDoorView view ) {
    this.view  = view;
    this.model = model;
  }

  /**
   * Query interaction from view
   * @param pn The product number to be checked
   */
  public void doQuery( String pn )
  {
    model.doQuery(pn);
    sound.play();
  }
  
  /**
   * RStock interaction from view
   * @param pn       The product number to be re-stocked
   * @param quantity The quantity to be re-stocked
   */
  public void doRStock( String pn, String quantity ) {
    model.doRStock(pn, quantity);
    sound.play();

  }

  /**
   * Clear interaction from view
   */
  public void doClear() {
    model.doClear();
    sound.play();
  }



  
}

