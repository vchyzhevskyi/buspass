using System;
using System.Configuration;
using FluentNHibernate.Cfg;
using FluentNHibernate.Cfg.Db;
using NHibernate;
using NHibernate.Tool.hbm2ddl;
using Yuliya.DAL.Mapping;

namespace Yuliya.DAL
{
    public class NSessionFactory : IDisposable
    {
        private static NSessionFactory _instance;
        private static readonly object _locker = new object();
        private readonly ISessionFactory _internalFactory;
        private readonly FluentConfiguration _sessionConfig;

        private NSessionFactory()
        {
            var mainConnString = ConfigurationManager.ConnectionStrings["main"].ConnectionString;
            _sessionConfig = Fluently.Configure()
#if DEBUG
                .Database(MsSqlConfiguration.MsSql2012.ConnectionString(mainConnString).ShowSql())
#else
                .Database(MsSqlConfiguration.MsSql2012.ConnectionString(mainConnString))
#endif
                .ExposeConfiguration(SchemaMetadataUpdater.QuoteTableAndColumns)
                .Mappings(m => m.FluentMappings.AddFromAssemblyOf<UserMap>());
            _internalFactory = _sessionConfig.BuildSessionFactory();
        }

        public static NSessionFactory Instance
        {
            get
            {
                lock (_locker)
                {
                    return _instance ?? (_instance = new NSessionFactory());
                }
            }
        }

        public FluentConfiguration Config => _sessionConfig;

        public void Dispose()
        {
            Dispose(true);
        }

        ~NSessionFactory()
        {
            Dispose(false);
        }

        public ISession Create()
        {
            return _internalFactory.OpenSession();
        }

        private void Dispose(bool disposing)
        {
            if (!disposing)
                return;

            GC.SuppressFinalize(this);

            if (!_internalFactory.IsClosed)
            {
                _internalFactory.Close();
                _internalFactory.Dispose();
            }
        }
    }
}