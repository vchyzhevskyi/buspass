using NUnit.Framework;
using Yuliya.BL;
using Yuliya.DAL.Domain;

namespace Yuliya.Tests.Unit
{
    public class create_ticket_type_test
    {
        private decimal _ticketCost;
        private int _ticketId;
        private string _ticketName;
        private ITicketTypeRepository _ttRepo;

        [TestFixtureSetUp]
        public void setup()
        {
            _ttRepo = IContainer.Instance.Resolve<ITicketTypeRepository>();
            create_tt();
            create_tt_not_assignable_id();
        }

        private void create_tt_not_assignable_id()
        {
            var tt = new TicketType
            {
                Name = _ticketName,
                Cost = _ticketCost
            };

            _ttRepo.Create(tt);

            tt = _ttRepo.Read(2);
            Assert.IsNotNull(tt);
            Assert.AreEqual(2, tt.Id);
            Assert.AreEqual(_ticketName, tt.Name);
            Assert.AreEqual(_ticketCost, tt.Cost);
        }

        private void create_tt()
        {
            _ticketCost = 1.4m;
            _ticketName = "Student";
            _ticketId = 1;
            var tt = new TicketType
            {
                Id = _ticketId,
                Name = _ticketName,
                Cost = _ticketCost
            };

            _ttRepo.Create(tt);

            tt = _ttRepo.Read(_ticketId);
            Assert.IsNotNull(tt);
            Assert.AreEqual(_ticketId, tt.Id);
            Assert.AreEqual(_ticketName, tt.Name);
            Assert.AreEqual(_ticketCost, tt.Cost);
        }

        [Test]
        public void read_by_id()
        {
            var tt = _ttRepo.Read(_ticketId);
            Assert.IsNotNull(tt);
            Assert.AreEqual(_ticketName, tt.Name);
            Assert.AreEqual(_ticketCost, tt.Cost);
        }

        [Test]
        public void update_ticket_type_data()
        {
            var tt = _ttRepo.Read(_ticketId);
            Assert.IsNotNull(tt);

            var old = tt.Cost;
            Assert.AreEqual(old, tt.Cost);

            tt.Cost = 2.8m;
            _ttRepo.Update(tt);

            tt = _ttRepo.Read(_ticketId);
            Assert.AreNotEqual(old, tt.Cost);
            Assert.AreEqual(_ticketName, tt.Name);
        }

        [TestFixtureTearDown]
        public void down()
        {
            var tt = _ttRepo.Read(_ticketId);
            Assert.IsNotNull(tt);
            _ttRepo.Delete(tt);

            tt = _ttRepo.Read(2);
            if (tt != null)
                _ttRepo.Delete(tt);

            IContainer.Instance.Dispose();
        }
    }
}