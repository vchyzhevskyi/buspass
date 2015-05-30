﻿using System;
using System.Collections.Generic;
using System.Net;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using Yuliya.BL;
using Yuliya.DAL.Domain;

namespace Yuliya.Web.Controllers
{
    public class TicketController : ApiController
    {
        private const string AuthHeaderName = "X-APP-Auth";
        private readonly ITicketRepository _repo;
        private readonly IUserRepository _userRepo;
        private ITicketTypeRepository _ttRepo;

        public TicketController()
        {
            _userRepo = IContainer.Instance.Resolve<IUserRepository>();
            _ttRepo = IContainer.Instance.Resolve<ITicketTypeRepository>();
            _repo = IContainer.Instance.Resolve<ITicketRepository>();
        }

        public IEnumerable<Ticket> Get()
        {
            Guid token;
            var authHeader = HttpContext.Current.Request.Headers.Get(AuthHeaderName);
            if (!string.IsNullOrEmpty(authHeader)
                && Guid.TryParse(authHeader, out token)
                && _userRepo.IsTokenValid(token))
            {
                return _repo.ReadActiveTickets(token);
            }

            throw new HttpException((int) HttpStatusCode.Forbidden, string.Empty);
        }

        public void Post([FromBody] Ticket value)
        {
            Guid token;
            var authHeader = HttpContext.Current.Request.Headers.Get(AuthHeaderName);
            if (!string.IsNullOrEmpty(authHeader)
                && Guid.TryParse(authHeader, out token)
                && _userRepo.IsTokenValid(token))
            {
                value.Bought = DateTime.UtcNow;
                value.User = _userRepo.ReadByToken(token);
                _repo.Create(value);
                return;
            }

            throw new HttpException((int) HttpStatusCode.Forbidden, string.Empty);
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