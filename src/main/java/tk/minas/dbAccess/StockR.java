package tk.minas.dbAccess;

/**
 * Implements Read access to the stock list
 * The stock list is held in a relational DataBase
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */

import tk.minas.catalogue.Product;
import tk.minas.debug.DEBUG;
import tk.minas.middle.StockException;
import tk.minas.middle.StockReader;
import tk.minas.catalogue.Product;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// There can only be 1 ResultSet opened per statement
// so no simultaneous use of the statement object
// hence the synchronized methods

// mySQL
//    no spaces after SQL statement ;

/**
  * Implements read only access to the stock database.
  */
public class StockR implements StockReader {
  private Connection theCon    = null;      // Connection to database
  private Statement  theStmt   = null;      // Statement object

  /**
   * Connects to database
   * Uses a factory method to help setup the connection
   * @throws StockException if problem
   */
  public StockR()
         throws StockException
  {
    try
    {
      DBAccess dbDriver = (new DBAccessFactory()).getNewDBAccess();
      dbDriver.loadDriver();
    
      theCon  = DriverManager.getConnection
                  ( dbDriver.urlOfDatabase(), 
                    dbDriver.username(), 
                    dbDriver.password() );

      theStmt = theCon.createStatement();
      theCon.setAutoCommit( true );
    }
    catch ( SQLException e )
    {
      throw new StockException( "SQL problem:" + e.getMessage() );
    }
    catch ( Exception e )
    {
      throw new StockException("Can not load database driver.");
    }
  }


  /**
   * Returns a statement object that is used to process SQL statements
   * @return A statement object used to access the database
   */
  
  protected Statement getStatementObject()
  {
    return theStmt;
  }

  /**
   * Returns a connection object that is used to process
   * requests to the DataBase
   * @return a connection object
   */

  protected Connection getConnectionObject()
  {
    return theCon;
  }

  @Override
  public synchronized boolean nameExists(String name) throws SQLException {
    PreparedStatement statement = theCon.prepareStatement("SELECT * FROM ProductTable WHERE ProductTable.description = ?");
    statement.setString(1, name);
    ResultSet rs = statement.executeQuery();
    boolean res = rs.next();
    return res;
  }



  /**
   * Checks if the product exits in the stock list
   * @param pNum The product number
   * @return true if exists otherwise false
   */
  public synchronized boolean exists( String pNum ) throws StockException{
    try {
      ResultSet rs   = getStatementObject().executeQuery(
        "select price from ProductTable " +
        "  where  ProductTable.productNo = '" + pNum + "'"
      );
      boolean res = rs.next();
      DEBUG.trace( "DB StockR: exists(%s) -> %s", 
                    pNum, ( res ? "T" : "F" ) );
      return res;
    } catch ( SQLException e ) {
      throw new StockException( "SQL exists: " + e.getMessage() );
    }
  }

  public  synchronized ArrayList<Product> getAll() throws SQLException {
    ArrayList<Product> allProducts = new ArrayList<>();
    ResultSet rs = getStatementObject().executeQuery(
            "SELECT ProductTable.productNo, description, price, stockLevel " +
                    "  FROM ProductTable INNER JOIN StockTable " +
                    "  ON  ProductTable.productNo = StockTable.productNo ");

    if (rs.next()){
      Product p = new Product(rs.getString("productNo") , rs.getString("description") , rs.getDouble("price"), rs.getInt("stockLevel") );
      allProducts.add(p);
    }

    return  allProducts;
  }



  /**
   * Returns details about the product in the stock list.
   * Assumed to exist in database.
   *
   * @param pNum The product number
   * @return Details in an instance of a Product
   */
  public synchronized Product getDetails(String pNum )
         throws StockException
  {
    try
    {
      Product   dt = new Product( "0", "", 0.00, 0 );
      ResultSet rs = getStatementObject().executeQuery(
        "select description, price, stockLevel " +
        "  from ProductTable, StockTable " +
        "  where  ProductTable.productNo = '" + pNum + "' " +
        "  and    StockTable.productNo   = '" + pNum + "'"
      );
      if ( rs.next() )
      {
        dt.setProductNum( pNum );
        dt.setDescription(rs.getString( "description" ) );
        dt.setPrice( rs.getDouble( "price" ) );
        dt.setQuantity( rs.getInt( "stockLevel" ) );
      }
      rs.close();
      return dt;
    } catch ( SQLException e )
    {
      throw new StockException( "SQL getDetails: " + e.getMessage() );
    }
  }

  /**
   * Returns 'image' of the product
   * @param pNum The product number
   *  Assumed to exist in database.
   * @return ImageIcon representing the image
   */
  public synchronized ImageIcon getImage( String pNum )
         throws StockException
  {
    String filename = "default.jpg";
    try
    {
      ResultSet rs   = getStatementObject().executeQuery(
        "select picture from ProductTable " +
        "  where  ProductTable.productNo = '" + pNum + "'"
      );
      
      boolean res = rs.next();
      if ( res )
        filename = "src/main/resources/" +rs.getString( "picture" );
      rs.close();
    } catch ( SQLException e )
    {
      DEBUG.error( "getImage()\n%s\n", e.getMessage() );
      throw new StockException( "SQL getImage: " + e.getMessage() );
    }
    
    //DEBUG.trace( "DB StockR: getImage -> %s", filename );
    return new ImageIcon( filename );
  }


  public synchronized Product getNameDetails(String name ) throws StockException {
    try {
      Product p = null;

      PreparedStatement statement = theCon.prepareStatement(
              "SELECT ProductTable.productNo, description, price, stockLevel " +
                      "  FROM ProductTable INNER JOIN StockTable " +
                      "  ON  ProductTable.productNo = StockTable.productNo " +
                      "  WHERE ProductTable.description  = ?");

      statement.setString(1, name);

      ResultSet rs = statement.executeQuery();

      if (rs.next()) {
        p = new Product( rs.getString( "productNo" ), rs.getString( "description" ), rs.getDouble( "price" ) , rs.getInt( "stockLevel" ));
      }
      rs.close();
      return p;
    } catch ( SQLException e )
    {
      throw new StockException( "SQL getDetails: " + e.getMessage() );
    }
  }




}
