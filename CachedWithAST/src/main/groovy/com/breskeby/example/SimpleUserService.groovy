package com.breskeby.example

/**
 * Created by IntelliJ IDEA.
 * User: Rene
 * Date: 08.06.2010
 * Time: 23:11:38
 * To change this template use File | Settings | File Templates.
 */
class SimpleUserService implements UserService{

  def DomainUser getUser(long id) {
    // heavy ldap or db usage

    return new DomainUser(id:(id), firstName:"FirstName", lastName:"LastName");  //To change body of implemented methods use File | Settings | File Templates.
  }
  
}
