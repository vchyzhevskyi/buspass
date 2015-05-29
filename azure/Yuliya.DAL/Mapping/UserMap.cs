using FluentNHibernate.Mapping;
using NHibernate.Type;
using Yuliya.DAL.Domain;

namespace Yuliya.DAL.Mapping
{
    public class UserMap : ClassMap<User>
    {
        public UserMap()
        {
            Schema("dbo");
            Table("User");
            Id(_ => _.Id).Column("Id").GeneratedBy.Identity();
            Map(_ => _.Login).Column("Login").Not.Nullable().Unique();
            Map(_ => _.Password).Column("Password").CustomType<BinaryBlobType>().Not.Nullable();
            Map(_ => _.Token).Column("Token").Not.Nullable();
        }
    }
}