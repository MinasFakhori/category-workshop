package tk.minas.remote;

import tk.minas.catalogue.Product;
import tk.minas.middle.StockException;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Defines the RMI interface for read access to the stock object.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */

public interface RemoteStockR_I extends Remote {
  boolean   exists(String number)
            throws RemoteException, StockException;
  Product   getDetails(String number)
            throws RemoteException, StockException;

  ArrayList<Product> getAll() throws SQLException;

  Product   getDetailsName(String name)
          throws RemoteException, StockException;
  ImageIcon getImage(String number)
            throws RemoteException, StockException;
}

