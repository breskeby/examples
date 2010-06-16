package com.breskeby.example

/**
 * Created by IntelliJ IDEA.
 * User: Rene
 * Date: 08.06.2010
 * Time: 23:19:37
 * To change this template use File | Settings | File Templates.
 */
class CachedUserService implements UserService{
  UserService simpleService = new SimpleUserService()

  Map<Long, Object> cachedUser = new HashMap<Long, Object>();
  
  // @Todo add elvis operator here!
  def DomainUser getUser(long id) {
    def DomainUser user = cachedUsers.get(id)
    if(!user){
      user = simpleService.getUser(id)
      cachedUser.put(user)
    }
    return user;  //To change body of implemented methods use File | Settings | File Templates.
  }
}