using FluentNHibernate.Mapping;
using Yuliya.DAL.Domain;

namespace Yuliya.DAL.Mapping
{
    public class TicketTypeMap : ClassMap<TicketType>
    {
        public TicketTypeMap()
        {
            Schema("dbo");
            Table("TicketType");
            Id(_ => _.Id).Column("Id").GeneratedBy.Identity();
            Map(_ => _.Name).Column("Name").Not.Nullable();
            Map(_ => _.Cost).Column("Cost").Not.Nullable();
        }
    }
}