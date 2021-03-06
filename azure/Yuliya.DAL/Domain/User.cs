﻿using System;

namespace Yuliya.DAL.Domain
{
    public class User
    {
        public virtual int Id { get; set; }
        public virtual string Login { get; set; }
        public virtual byte[] Password { get; set; }
        public virtual Guid Token { get; set; }
        public virtual decimal Account { get; set; }
    }
}