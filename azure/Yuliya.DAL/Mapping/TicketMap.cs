using FluentNHibernate.Mapping;
using Yuliya.DAL.Domain;

namespace Yuliya.DAL.Mapping
{
    public class TicketMap : ClassMap<Ticket>
    {
        public TicketMap()
        {
            Schema("dbo");
            Table("Ticket");
            Id(_ => _.Id).Column("Id").GeneratedBy.Guid();
            References(_ => _.User).Column("UserId").Not.Nullable().Cascade.Delete();
            References(_ => _.Type).Column("TicketTypeId").Not.Nullable().Cascade.Delete();
            Map(_ => _.Bought).Column("Bought").Not.Nullable();
            Map(_ => _.BusNumber).Column("BusNumber").Not.Nullable();
        }
    }
}