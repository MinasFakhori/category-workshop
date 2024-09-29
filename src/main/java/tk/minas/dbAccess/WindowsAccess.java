package tk.minas.dbAccess;

/**
  * Implements management of a Microsoft Access database.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */
class WindowsAccess extends DBAccess {




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

  public void loadDriver() throws Exception
  {
    Class.forName("");
  }


}
