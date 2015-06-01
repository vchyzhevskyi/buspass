
    if exists (select 1 from sys.objects where object_id = OBJECT_ID(N'dbo.[FKE14EC53658EA206A]') AND parent_object_id = OBJECT_ID('dbo.Ticket'))
alter table dbo.Ticket  drop constraint FKE14EC53658EA206A


    if exists (select 1 from sys.objects where object_id = OBJECT_ID(N'dbo.[FKE14EC5364E8AD196]') AND parent_object_id = OBJECT_ID('dbo.Ticket'))
alter table dbo.Ticket  drop constraint FKE14EC5364E8AD196


    if exists (select * from dbo.sysobjects where id = object_id(N'dbo.Ticket') and OBJECTPROPERTY(id, N'IsUserTable') = 1) drop table dbo.Ticket

    if exists (select * from dbo.sysobjects where id = object_id(N'dbo.TicketType') and OBJECTPROPERTY(id, N'IsUserTable') = 1) drop table dbo.TicketType

    if exists (select * from dbo.sysobjects where id = object_id(N'dbo.[User]') and OBJECTPROPERTY(id, N'IsUserTable') = 1) drop table dbo.[User]

    create table dbo.Ticket (
        Id UNIQUEIDENTIFIER not null,
       Bought DATETIME not null,
       BusNumber INT not null,
       UserId INT not null,
       TicketTypeId INT not null,
       primary key (Id)
    )

    create table dbo.TicketType (
        Id INT not null,
       Name NVARCHAR(255) not null,
       Cost DECIMAL(19,5) not null,
       primary key (Id)
    )

    create table dbo.[User] (
        Id INT IDENTITY NOT NULL,
       Login NVARCHAR(255) not null unique,
       Password VARBINARY(MAX) not null,
       Token UNIQUEIDENTIFIER not null,
       Account DECIMAL(19,5) not null check( Account >= 0) ,
       primary key (Id)
    )

    alter table dbo.Ticket 
        add constraint FKE14EC53658EA206A 
        foreign key (UserId) 
        references dbo.[User]

    alter table dbo.Ticket 
        add constraint FKE14EC5364E8AD196 
        foreign key (TicketTypeId) 
        references dbo.TicketType
