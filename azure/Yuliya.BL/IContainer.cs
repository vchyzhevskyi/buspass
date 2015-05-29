using System;
using Castle.Windsor;

namespace Yuliya.BL
{
    public class IContainer : IDisposable
    {
        private static IContainer _instance;
        private static readonly object _locker = new object();
        private readonly WindsorContainer _container;

        private IContainer()
        {
            _container = new WindsorContainer();
            _container.Install(new DependencyConfiguration());
        }

        public static IContainer Instance
        {
            get
            {
                lock (_locker)
                {
                    return _instance ?? (_instance = new IContainer());
                }
            }
        }

        public void Dispose()
        {
            Dispose(true);
        }

        ~IContainer()
        {
            Dispose(false);
        }

        public T Resolve<T>()
        {
            return _container.Resolve<T>();
        }

        private void Dispose(bool disposing)
        {
            if (!disposing)
                return;

            GC.SuppressFinalize(this);

            _container.Dispose();
        }
    }
}