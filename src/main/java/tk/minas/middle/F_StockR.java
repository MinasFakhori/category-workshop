package tk.minas.middle;

/**
 * Facade for read access to the stock list.
 * The actual implementation of this is held on the middle tier.
 * The actual stock list is held in a relational DataBase on the 
 * third tier.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */

import tk.minas.catalogue.Product;
import tk.minas.debug.DEBUG;
import tk.minas.remote.RemoteStockR_I;
import tk.minas.catalogue.Product;

import javax.swing.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Setup connection to the middle tier
 */

public class F_StockR implements StockReader {
  private RemoteStockR_I aR_StockR   = null;
  private String         theStockURL = null;

  public F_StockR( String url ) {
    DEBUG.trace("F_StockR: %s", url );
    theStockURL = url;
  }
  
  private void connect() throws StockException
  {
    try                                             // Setup
    {                                               //  connection
      aR_StockR =                                   //  Connect to
        (RemoteStockR_I) Naming.lookup(theStockURL);// Stub returned
    }
    catch ( Exception e )                           // Failure to
    {                                               //  attach to the
      aR_StockR = null;
      throw new StockException( "Com: " + 
                                e.getMessage()  );  //  object
      
    }
  }

  /**
   * Checks if the product exits in the stock list
   * @return true if exists otherwise false
   */

  public synchronized boolean exists( String number ) throws StockException {
    try {
      if ( aR_StockR == null ) connect();
      return aR_StockR.exists( number );
    }catch ( RemoteException e ) {
      aR_StockR = null;
      throw new StockException( "Net: " + e.getMessage() );
    }
  }


  public synchronized ArrayList<Product> getAll() throws SQLException {
    try {
      if (aR_StockR == null) {
        connect();
      }
    }catch (StockException e){
      aR_StockR = null;
      throw new SQLException(e.getMessage());
    }
    return aR_StockR.getAll();
  }


  @Override
  public boolean nameExists(String pName) throws StockException, SQLException, RemoteException {
        if ( aR_StockR == null ) connect();
        return aR_StockR.exists( pName );
      }



  /**
   * Returns details about the product in the stock list
   *
   * @return StockNumber, Description, Price, Quantity
   */

  public synchronized Product getDetails(String number )
         throws StockException
  {
    DEBUG.trace("F_StockR:getDetails()" );
    try
    {
      if ( aR_StockR == null ) connect();
      return aR_StockR.getDetails( number );
    } catch ( RemoteException e )
    {
      aR_StockR = null;
      throw new StockException( "Net: " + e.getMessage() );
    }
  }

  @Override
  public Product getNameDetails(String name) throws StockException, RemoteException {
        if ( aR_StockR == null ) connect();
        return aR_StockR.getDetailsName( name );
      }


  public synchronized ImageIcon getImage( String number )
         throws StockException
  {
    DEBUG.trace("F_StockR:getImage()" );
    try
    {
      if ( aR_StockR == null ) connect();
      return aR_StockR.getImage( number );
    }
    catch ( RemoteException e )
    {
      aR_StockR = null;
      throw new StockException( "Net: " + e.getMessage() );
    }
  }

}
