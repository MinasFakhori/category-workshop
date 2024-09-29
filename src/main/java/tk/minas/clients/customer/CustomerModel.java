package tk.minas.clients.customer;

import tk.minas.catalogue.Basket;
import tk.minas.catalogue.Product;
import tk.minas.debug.DEBUG;
import tk.minas.middle.MiddleFactory;
import tk.minas.middle.OrderProcessing;
import tk.minas.middle.StockException;
import tk.minas.middle.StockReader;

import javax.swing.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Implements the Model of the customer client
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */
public class CustomerModel extends Observable
{
  private Product     theProduct = null;          // Current product
  private Basket      theBasket  = null;          // Bought items

  private String      pn = "";                    // Product being processed

  private boolean colour;

  private StockReader     theStock     = null;
  private OrderProcessing theOrder     = null;
  private ImageIcon       thePic       = null;

  /*
   * Construct the model of the Customer
   * @param mf The factory to create the connection objects
   */
  public CustomerModel(MiddleFactory mf)
  {
    try                                          //
    {
      theStock = mf.makeStockReader();           // Database access
    } catch ( Exception e )
    {
      DEBUG.error("CustomerModel.constructor\n" +
              "Database not created?\n%s\n", e.getMessage() );
    }
    theBasket = makeBasket();                    // Initial Basket
  }

  /**
   * return the Basket of products
   * @return the basket of products
   */
  public Basket getBasket()
  {
    return theBasket;
  }

  /**
   * Check if the product is in Stock
   * @param productNum The product number
   */
  public boolean doCheck(String productNum )
  {
    theBasket.clear();                          // Clear s. list
    String theAction = "";
    pn  = productNum.trim();                    // Product no.
    int    amount  = 1;                         //  & quantity
    try
    {
      if ( theStock.exists( pn ) )              // Stock Exists?
      {                                         // T
        Product pr = theStock.getDetails( pn ); //  Product
        if ( pr.getQuantity() >= amount )       //  In stock?
        {
          colour = true;
          theAction =                           //   Display
                  String.format( "%s : %7.2f (%2d) ", //
                          pr.getDescription(),              //    description
                          pr.getPrice(),                    //    price
                          pr.getQuantity() );               //    quantity
          pr.setQuantity( amount );             //   Require 1
          theBasket.add( pr );                  //   Add to basket
          thePic = theStock.getImage( pn );     //    product
        } else {
          colour = false;
          //  F
          theAction =                           //   Inform
                  pr.getDescription() +               //    product not
                          " not in stock" ;                   //    in stock
        }
      } else {                                  // F
        theAction =                             //  Inform Unknown
                "Unknown product number " + pn;       //  product number
      }
    } catch( StockException e )
    {
      DEBUG.error("CustomerClient.doCheck()\n%s",
              e.getMessage() );
    }
    setChanged(); notifyObservers(theAction);
    return colour;
  }

  /**
   * Clear the products from the basket
   */
  public void doClear()
  {
    String theAction = "";
    theBasket.clear();                        // Clear s. list
    theAction = "Enter Product Number";       // Set display
    thePic = null;                            // No picture
    setChanged(); notifyObservers(theAction);
  }

  /**
   * Return a picture of the product
   * @return An instance of an ImageIcon
   */
  public ImageIcon getPicture()
  {
    return thePic;
  }

  /**
   * ask for update of view callled at start
   */
  private void askForUpdate()
  {
    setChanged(); notifyObservers("START only"); // Notify
  }

  /**
   * Make a new Basket
   * @return an instance of a new Basket
   */
  protected Basket makeBasket()
  {
    return new Basket();
  }

  public boolean findName(String productName ) {
    theBasket.clear();
    String theAction = "";
    pn  = productName;
    int    amount  = 1;
    try {
      if(theStock.nameExists(pn)){
        Product pr = theStock.getNameDetails( pn );
        if ( pr.getQuantity() >= amount ) {
          colour = true;
          theAction =
                  String.format( "%s : %7.2f (%2d) ",
                          pr.getDescription(),
                          pr.getPrice(),
                          pr.getQuantity() );
          pr.setQuantity( amount );
          theBasket.add( pr );
          thePic = theStock.getImage( pr.getProductNum() );
        }else{
          colour = false;
          theAction = pr.getDescription() + " not in stock" ;
        }
      }else {
        theAction = "Unknown product " + pn;
      }
    } catch( Exception e ) {
      DEBUG.error("CustomerClient.doCheck()\n%s",
              e.getMessage() );
    }
    setChanged(); notifyObservers(theAction);
    return colour;
  }


    public void getAll() throws SQLException {
      theBasket.clear();
      String theAction = "";

      ArrayList<Product> products = new ArrayList<>();

      for (Product pr : theStock.getAll()) {
        theAction =                           //   Display
                String.format( "%s : %7.2f (%2d) ", //
                        pr.getDescription(),              //    description
                        pr.getPrice(),                    //    price
                        pr.getQuantity() );               //    quantity

        theBasket.add( pr );                  //   Add to basket
      }
      setChanged(); notifyObservers(theAction);

    }



}
