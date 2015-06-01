using System;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using Yuliya.BL;
using Yuliya.DAL.Domain;
using Yuliya.Web.Models;

namespace Yuliya.Web.Controllers
{
    public class UserController : ApiController
    {
        private const string AuthHeaderName = "X-APP-Auth";
        private readonly IUserRepository _repo;

        public UserController()
        {
            _repo = IContainer.Instance.Resolve<IUserRepository>();
        }

        public HttpStatusCodeResult Get()
        {
            return new HttpStatusCodeResult((int) HttpStatusCode.MethodNotAllowed);
        }

        public object Get(string login, string password)
        {
            User user;

            try
            {
                user = _repo.ReadByLogin(login);
            }
            catch (Exception)
            {
                goto BAD_REQUEST;
            }

            if (user == null)
            {
                goto BAD_REQUEST;
            }

            var hashedFromUser = MD5Hasher.Hash(password);

            if (hashedFromUser.SequenceEqual(user.Password))
            {
                HttpContext.Current.Response.Headers.Add(AuthHeaderName, user.Token.ToString());
                return user;
            }

            BAD_REQUEST:
            return new HttpStatusCodeResult((int) HttpStatusCode.BadRequest);
        }

        public void Post([FromBody] UserRegistrationModel value)
        {
            try
            {
                var user = new User
                {
                    Login = value.Login,
                    Password = MD5Hasher.Hash(value.Password),
                    Token = Guid.NewGuid()
                };
                _repo.Create(user);
                HttpContext.Current.Response.Headers.Add(AuthHeaderName, user.Token.ToString());
            }
            catch (Exception)
            {
                throw new HttpException((int) HttpStatusCode.BadRequest, string.Empty);
            }
        }

        public void Put(int id, [FromBody] User value)
        {
            Guid token;
            var authHeader = HttpContext.Current.Request.Headers.Get(AuthHeaderName);
            if (!string.IsNullOrEmpty(authHeader)
                && Guid.TryParse(authHeader, out token))
            {
                var user = _repo.ReadByToken(token);
                user.Account = value.Account;
                _repo.Update(user);
                return;
            }
            throw new HttpException((int) HttpStatusCode.Forbidden, string.Empty);
        }

        public void Delete(int id)
        {
            throw new HttpException((int) HttpStatusCode.NotImplemented, string.Empty);
        }
    }
}