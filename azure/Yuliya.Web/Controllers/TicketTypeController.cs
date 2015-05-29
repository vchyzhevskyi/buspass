using System;
using System.Collections.Generic;
using System.Net;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using Yuliya.BL;
using Yuliya.DAL.Domain;

namespace Yuliya.Web.Controllers
{
    public class TicketTypeController : ApiController
    {
        private const string AuthHeaderName = "X-APP-Auth";
        private readonly ITicketTypeRepository _repo;
        private readonly IUserRepository _userRepo;

        public TicketTypeController()
        {
            _repo = IContainer.Instance.Resolve<ITicketTypeRepository>();
            _userRepo = IContainer.Instance.Resolve<IUserRepository>();
        }

        public IEnumerable<TicketType> Get()
        {
            Guid token;
            var authHeader = HttpContext.Current.Request.Headers.Get(AuthHeaderName);
            if (!string.IsNullOrEmpty(authHeader)
                && Guid.TryParse(authHeader, out token)
                && _userRepo.IsTokenValid(token))
            {
                return _repo.ReadList();
            }

            throw new HttpException((int) HttpStatusCode.Forbidden, string.Empty);
        }

        public HttpStatusCodeResult Post([FromBody] string value)
        {
            return new HttpStatusCodeResult((int) HttpStatusCode.MethodNotAllowed);
        }

        public HttpStatusCodeResult Put(int id, [FromBody] string value)
        {
            return new HttpStatusCodeResult((int) HttpStatusCode.MethodNotAllowed);
        }

        public HttpStatusCodeResult Delete(int id)
        {
            return new HttpStatusCodeResult((int) HttpStatusCode.MethodNotAllowed);
        }
    }
}