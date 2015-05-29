using System;
using Yuliya.DAL.Domain;

namespace Yuliya.BL
{
    public interface IUserRepository : ICRUD<User, int>
    {
        User ReadByLogin(string login);
        User ReadByToken(Guid token);
    }
}