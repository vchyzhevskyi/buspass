using NHibernate.Criterion;
using Yuliya.DAL;
using Yuliya.DAL.Domain;

namespace Yuliya.BL
{
    public class NTicketTypeRepository : ITicketTypeRepository
    {
        public void Create(TicketType entity)
        {
            using (var dbSession = NSessionFactory.Instance.Create())
            {
                dbSession.Save(entity);
                dbSession.Flush();
            }
        }

        public TicketType Read(int key)
        {
            using (var dbSession = NSessionFactory.Instance.Create())
            {
                var criteria = dbSession.CreateCriteria<TicketType>();
                criteria.Add(Restrictions.Eq("Id", key));
                return criteria.UniqueResult<TicketType>();
            }
        }

        public void Update(TicketType entity)
        {
            using (var dbSession = NSessionFactory.Instance.Create())
            {
                dbSession.Update(entity);
                dbSession.Flush();
            }
        }

        public void Delete(TicketType entity)
        {
            using (var dbSession = NSessionFactory.Instance.Create())
            {
                dbSession.Delete(entity);
                dbSession.Flush();
            }
        }
    }
}