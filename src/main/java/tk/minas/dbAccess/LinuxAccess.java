package tk.minas.dbAccess;

/**
  * Implements management of an mySQL database on Linux.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */
class LinuxAccess extends DBAccess
{
  public void loadDriver() throws Exception
  {
    Class.forName("org.gjt.mm.mysql.Driver").newInstance();
  }




  public String urlOfDatabase()
  {
    return "";
  }

  public String username()
  {
    return "";
  }

  public String password()
  {
    return "";
  }



}
