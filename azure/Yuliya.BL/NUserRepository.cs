using System;
using NHibernate.Criterion;
using Yuliya.DAL;
using Yuliya.DAL.Domain;

namespace Yuliya.BL
{
    public class NUserRepository : IUserRepository
    {
        public void Create(User entity)
        {
            using (var dbSession = NSessionFactory.Instance.Create())
            {
                dbSession.Save(entity);
                dbSession.Flush();
            }
        }

        public User Read(int key)
        {
            throw new NotImplementedException();
        }

        public void Update(User entity)
        {
            using (var dbSession = NSessionFactory.Instance.Create())
            {
                dbSession.Update(entity);
                dbSession.Flush();
            }
        }

        public void Delete(User entity)
        {
            using (var dbSession = NSessionFactory.Instance.Create())
            {
                dbSession.Delete(entity);
                dbSession.Flush();
            }
        }

        public User ReadByLogin(string login)
        {
            using (var dbSession = NSessionFactory.Instance.Create())
            {
                var criteria = dbSession.CreateCriteria<User>();
                criteria.Add(Restrictions.Eq("Login", login));
                return criteria.UniqueResult<User>();
            }
        }

        public User ReadByToken(Guid token)
        {
            using (var dbSession = NSessionFactory.Instance.Create())
            {
                var criteria = dbSession.CreateCriteria<User>();
                criteria.Add(Restrictions.Eq("Token", token));
                return criteria.UniqueResult<User>();
            }
        }
    }
}