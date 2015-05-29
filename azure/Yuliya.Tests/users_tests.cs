using System;
using NUnit.Framework;
using Yuliya.BL;
using Yuliya.DAL.Domain;

namespace Yuliya.Tests.Unit
{
    public class create_user_test
    {
        private readonly string _userLogin = "test@test.com";
        private byte[] _userPassword;
        private IUserRepository _userRepo;
        private Guid _userToken;

        [TestFixtureSetUp]
        public void setup()
        {
            _userRepo = IContainer.Instance.Resolve<IUserRepository>();
            create_user();
        }

        public void create_user()
        {
            _userToken = Guid.NewGuid();
            _userPassword = MD5Hasher.Hash("test");
            var user = new User
            {
                Login = _userLogin,
                Password = _userPassword,
                Token = _userToken
            };

            _userRepo.Create(user);
        }

        [Test]
        public void read_user_by_login()
        {
            var user = _userRepo.ReadByLogin(_userLogin);
            Assert.IsNotNull(user);
            Assert.AreEqual(_userLogin, user.Login);
        }

        [Test]
        public void read_user_by_token()
        {
            var user = _userRepo.ReadByToken(_userToken);
            Assert.IsNotNull(user);
            Assert.AreEqual(_userLogin, user.Login);
            Assert.AreEqual(_userToken, user.Token);
        }

        [Test]
        public void update_user_data()
        {
            var user = _userRepo.ReadByLogin(_userLogin);
            Assert.AreEqual(_userLogin, user.Login);

            var old = (byte[])user.Password.Clone();
            CollectionAssert.AreEqual(old, user.Password);

            user.Password = MD5Hasher.Hash("anotherTest");
            _userRepo.Update(user);

            user = _userRepo.ReadByLogin(_userLogin);

            CollectionAssert.AreNotEqual(old, user.Password);
        }

        [TestFixtureTearDown]
        public void down()
        {
            var user = _userRepo.ReadByLogin(_userLogin);
            _userRepo.Delete(user);

            IContainer.Instance.Dispose();
        }
    }
}