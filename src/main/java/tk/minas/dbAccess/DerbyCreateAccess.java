package tk.minas.dbAccess;

/**
  * Implements management of an Apache Derby database.
  *  that is too be created
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */
 
class DerbyCreateAccess extends DBAccess
{
  private static final String URLdb =
                 "jdbc:mysql://178.128.37.54:3306/mf600_CI553";
  private static final String DRIVER =
                 "com.mysql.cj.jdbc.Driver";

  public void loadDriver() throws Exception
  {
    Class.forName(DRIVER).newInstance();
  }

  public String urlOfDatabase()
  {
    return URLdb;
  }
}

