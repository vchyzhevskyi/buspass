namespace Yuliya.DAL.Domain
{
    public class TicketType
    {
        public virtual int Id { get; set; }
        public virtual string Name { get; set; }
        public virtual decimal Cost { get; set; }
    }
}