using System;

namespace Yuliya.DAL.Domain
{
    public class Ticket
    {
        public virtual Guid Id { get; set; }
        public virtual User User { get; set; }
        public virtual TicketType Type { get; set; }
        public virtual DateTime Bought { get; set; }
    }
}