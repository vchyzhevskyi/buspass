using System.Collections.Generic;
using Yuliya.DAL.Domain;

namespace Yuliya.BL
{
    public interface ITicketTypeRepository : ICRUD<TicketType, int>
    {
        IEnumerable<TicketType> ReadList();
    }
}