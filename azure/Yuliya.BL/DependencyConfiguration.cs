using Castle.MicroKernel.Registration;
using Castle.MicroKernel.SubSystems.Configuration;
using Castle.Windsor;

namespace Yuliya.BL
{
    public class DependencyConfiguration : IWindsorInstaller
    {
        public void Install(IWindsorContainer container, IConfigurationStore store)
        {
            container.Register(
                Component
                    .For<IUserRepository>()
                    .ImplementedBy<NUserRepository>()
                    .LifeStyle.Singleton);
            container.Register(
                Component
                    .For<ITicketTypeRepository>()
                    .ImplementedBy<NTicketTypeRepository>()
                    .LifeStyle.Singleton);
        }
    }
}