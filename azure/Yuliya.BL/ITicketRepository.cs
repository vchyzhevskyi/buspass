using System;
using System.Collections.Generic;
using Yuliya.DAL.Domain;

namespace Yuliya.BL
{
    public interface ITicketRepository : ICRUD<Ticket, Guid>
    {
        IList<Ticket> ReadActiveTickets(Guid userToken);
    }
}