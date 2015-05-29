using System;
using System.Collections.Generic;
using System.Linq;
using NUnit.Framework;
using Yuliya.BL;
using Yuliya.DAL.Domain;

namespace Yuliya.Tests.Unit
{
    public class create_ticket_test
    {
        private ITicketRepository _tRepo;
        private TicketType _tt;
        private ITicketTypeRepository _ttRepo;
        private User _user;
        private string _userLogin;
        private byte[] _userPassword;
        private IUserRepository _userRepo;
        private Guid _userToken;
        private DateTime _ticketBought;
        private DateTime _ticketBought1;

        [TestFixtureSetUp]
        public void setup()
        {
            _tRepo = IContainer.Instance.Resolve<ITicketRepository>();
            _userRepo = IContainer.Instance.Resolve<IUserRepository>();
            _ttRepo = IContainer.Instance.Resolve<ITicketTypeRepository>();
            create_user();
            create_ticket_type();
            load_dependencies();
            create_ticket();
        }

        private void load_dependencies()
        {
            _tt = _ttRepo.Read(1);
            Assert.IsNotNull(_tt);
            _user = _userRepo.ReadByToken(_userToken);
            Assert.IsNotNull(_user);
        }

        private void create_ticket_type()
        {
            var tt = new TicketType
            {
                Id = 0,
                Name = "Student",
                Cost = 1.4m
            };

            _ttRepo.Create(tt);
        }

        private void create_ticket()
        {
            _ticketBought = DateTime.UtcNow;
            var ticket = new Ticket
            {
                Bought = _ticketBought,
                Type = _tt,
                User = _user
            };

            _tRepo.Create(ticket);

            _ticketBought1 = DateTime.UtcNow.AddMinutes(30);
            ticket = new Ticket
            {
                Bought = _ticketBought1,
                Type = _tt,
                User = _user
            };
            _tRepo.Create(ticket);
        }

        private void create_user()
        {
            _userLogin = "test@test.com";
            _userPassword = MD5Hasher.Hash("test");
            _userToken = Guid.NewGuid();
            var user = new User
            {
                Login = _userLogin,
                Password = _userPassword,
                Token = _userToken
            };

            _userRepo.Create(user);
        }

        [Test]
        public void read_active_tickets()
        {
            var tickets = _tRepo.ReadActiveTickets(_userToken);
            Assert.IsNotNull(tickets);
            CollectionAssert.IsNotEmpty(tickets);
            var ticket = tickets.First();
            Assert.IsNotNull(ticket);
            Assert.That(_ticketBought, Is.EqualTo(ticket.Bought).Within(1).Minutes);
            Assert.AreEqual(_userToken, ticket.User.Token);

            ticket = tickets.Skip(1).First();
            Assert.IsNotNull(ticket);
            Assert.That(_ticketBought1, Is.EqualTo(ticket.Bought).Within(1).Minutes);
            Assert.AreEqual(_userToken, ticket.User.Token);
        }

        [TestFixtureTearDown]
        public void down()
        {
            IContainer.Instance.Dispose();
        }
    }
}