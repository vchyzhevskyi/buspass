using System.IO;
using NHibernate.Cfg;
using NUnit.Framework;
using Yuliya.DAL;
using Environment = System.Environment;

namespace Yuliya.Tests.Unit
{
    public class SchemaExport
    {
        [Test]
        [Ignore]
        public void Export()
        {
            var currentDirectory = Environment.CurrentDirectory;
            var path = Path.Combine(currentDirectory, "../../", "schema.sql");
            Assert.IsNotEmpty(path);
            var configuration = NSessionFactory.Instance.Config.BuildConfiguration();

            var schemaExport = new NHibernate.Tool.hbm2ddl.SchemaExport(configuration);
            schemaExport.SetOutputFile(path).Create(true, true);
        }
    }
}