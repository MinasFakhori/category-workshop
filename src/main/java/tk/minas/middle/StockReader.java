package tk.minas.middle;

import tk.minas.catalogue.Product;
import tk.minas.catalogue.Product;

import javax.swing.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
  * Interface for read access to the stock list.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */

public interface StockReader {

 /**
   * Checks if the product exits in the stock list
   * @param pNum Product nymber
   * @return true if exists otherwise false
   * @throws StockException if issue
   */
  boolean exists(String pNum) throws StockException;

 ArrayList<Product> getAll() throws SQLException;

 boolean nameExists(String pNum) throws StockException, SQLException, RemoteException;
         
  /**
   * Returns details about the product in the stock list
   *
   * @param pNum Product nymber
   * @return StockNumber, Description, Price, Quantity
   * @throws StockException if issue
   */
  
  Product getDetails(String pNum) throws StockException;

 Product getNameDetails(String name) throws StockException, RemoteException;

  
  
  /**
   * Returns an image of the product in the stock list
   * @param pNum Product nymber
   * @return Image
   * @throws StockException if issue
   */
  
  ImageIcon getImage(String pNum) throws StockException;
}
