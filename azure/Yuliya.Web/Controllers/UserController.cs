using System;
using System.Net;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using Yuliya.BL;
using Yuliya.DAL.Domain;

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

            if (hashedFromUser.Equals(user.Password))
            {
                HttpContext.Current.Response.Headers.Add(AuthHeaderName, user.Token.ToString());
                return user;
            }

            BAD_REQUEST:
            return new HttpStatusCodeResult((int) HttpStatusCode.BadRequest);
        }

        public void Post([FromBody] User value)
        {
            try
            {
                _repo.Create(value);
            }
            catch (Exception)
            {
                throw new HttpException((int) HttpStatusCode.BadRequest, string.Empty);
            }
        }

        public void Put(int id, [FromBody] string value)
        {
            throw new HttpException((int) HttpStatusCode.NotImplemented, string.Empty);
        }

        public void Delete(int id)
        {
            throw new HttpException((int) HttpStatusCode.NotImplemented, string.Empty);
        }
    }
}