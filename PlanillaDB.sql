Create database PlanillaDB
go
use PlanillaDB
go

create table Usuario(
	idUsuario int identity(1,1) primary key not null,
	Nombre Varchar(50) not null,
	Apellido Varchar(50) not null,
	Username Varchar(60) not null,
	Password Varchar(40) not null
)
go
create table Empleado(
	idEmpleado int identity(1,1) primary key not null,
	Nombre varchar(80) not null,
	Apellido varchar(80) not null,
	FechaInicio Date not null,
	Estado Varchar(250) not null,
	idUsuario int foreign key(idUsuario) references Usuario(idUsuario) 
)
go

create table SalarioOrdinario(
	idSalarioOrdinario int identity(1,1) primary key not null,
	anio int not null,
	CuotaSalarioOrdinario real not null
)

go
create table Bonificacion(
	idBonificacion int identity(1,1) primary key not null,
	BonificacionEmpleado real not null,
	fechaBonificacion Date not null,
	idEmpleado int foreign key (idEmpleado) references Empleado(idEmpleado) not null,
)
go
create table RetencionISR(
idRetencionISR int identity(1,1) primary key not null,
RetencionEmpleado real not null,
fechaRetencion Date not null,
idEmpleado int foreign key (idEmpleado) references Empleado(idEmpleado) not null,
)
go

create table Igss(
idIgss int identity(1,1) primary key not null,
anio int not null,
Cuotaigss real not null
)
go

 create table planillaSQL(
	 idPlanilla int identity(1,1) not null primary key,
	 idEmpleado int,
	 BonificacionEmpleado real,
	 anio int,
	 CuotaSalarioOrdinario real,
	 total real,
	 RetencionEmpleado real,
	 CuotaIgss real,
	 liquido real,
	 fechaGenerada date,
	 mes varchar(50)
 );


go
insert into Usuario(Nombre,Apellido,Username,Password)values('Susana','Araceli','susana','123')
insert into Usuario(Nombre,Apellido,Username,Password)values('Victor','Yaxon','vyaxon','123')
go
insert into Empleado(Nombre,Apellido,FechaInicio,Estado,idUsuario)values('Susana','Araceli',GetDate(),'Activo','1')
insert into Empleado(Nombre,Apellido,FechaInicio,Estado,idUsuario)values('Victor','Yaxon',GetDate(),'Activo','1')

go
insert into SalarioOrdinario(anio,CuotaSalarioOrdinario)values('2011','4000')
insert into SalarioOrdinario(anio,CuotaSalarioOrdinario)values('2012','3000')
go

insert into Igss(anio,Cuotaigss)values('2011','250')
insert into Igss(anio,Cuotaigss)values('2013','250')
go

insert into Bonificacion(BonificacionEmpleado,fechaBonificacion,idEmpleado) values(500,GetDate(),1)
insert into Bonificacion(BonificacionEmpleado,fechaBonificacion,idEmpleado) values(600,GetDate(),2)
go

insert into RetencionISR(RetencionEmpleado,fechaRetencion,idEmpleado)values('1000',GETDATE(),'1')
insert into RetencionISR(RetencionEmpleado,fechaRetencion,idEmpleado)values('1000',GETDATE(),'1')
go

Select * from Usuario
Select * from Empleado
Select * from Bonificacion
Select * from SalarioOrdinario
Select * from Igss
Select * from RetencionISR
Select * from planillaSQL
go

CREATE procedure [dbo].[sp_AgregarEmpleado] 
	@Nombre varchar(128),
	@Apellido varchar(128),
	@FechaInicio varchar(128),
	@Estado varchar(128),
	@idusuario varchar(128)
as
insert into Empleado(Nombre,Apellido,FechaInicio,Estado,idUsuario)
	values (@Nombre,@Apellido,@FechaInicio,@Estado,@idUsuario)
go
create procedure [dbo].[sp_AgregarIgss]
@anio int,
@CuotaIgss varchar(128)
as
insert into Igss(anio,Cuotaigss)
values (@anio,@CuotaIgss)
go
create procedure [dbo].[sp_AgregarUsuarios]
@nombre varchar(128),
@apellido varchar(128),
@username varchar(128),
@userpassword varchar(128)
as
insert into Usuario(Nombre,Apellido,Username,Password)
values (@nombre,@apellido,@username,@userpassword)
go

create procedure [dbo].[sp_agregarRetencion]
@RetencionEmpleado varchar(90),
@idEmpleado int 
as
insert into RetencionISR(RetencionEmpleado,fechaRetencion,idEmpleado) values
(@RetencionEmpleado,GETDATE(),@idEmpleado)
go
create procedure [dbo].[sp_AgregarSalarioOrdinario]
@anio int,
@CuotaSalarioOrdinario varchar(128)
as
insert into SalarioOrdinario(anio,CuotaSalarioOrdinario)
values (@anio,@CuotaSalarioOrdinario)
go
CREATE procedure [dbo].[sp_AutenticarUsuario] @username varchar(128), @userpassword varchar(128)
as
	select Usuario.idUsuario, Usuario.Nombre, Usuario.Apellido, Usuario.username, Usuario.Password from Usuario where Usuario.username = @username and Usuario.password = @userpassword

GO
CREATE procedure [dbo].[sp_agregarBonificacion]
@BonificacionEmpleado varchar(90),
@idEmpleado int 
as
insert into Bonificacion(BonificacionEmpleado,fechaBonificacion,idEmpleado) values
(@BonificacionEmpleado,GETDATE(),@idEmpleado)

GO

-----Eliminar------
create procedure [dbo].[sp_EliminarBonificaion]
	@idBonificacion int
	as
	delete from Bonificacion
	where idBonificacion=@idBonificacion

GO
create procedure [dbo].[sp_eliminarEmpleados]
	@idEmpleado int 
	as 	
	delete from Empleado
	where idEmpleado = @idEmpleado

GO
create procedure [dbo].[sp_EliminarIgss]
@idIgss int 
	as 	
	delete from Igss
	where idIgss = @idIgss

GO
GO
	create procedure [dbo].[sp_EliminarRetencion]
	@idRetencionISR int
	as
	delete from RetencionISR
	where idRetencionISR =@idRetencionISR

GO
create procedure [dbo].[sp_EliminarSalarioOrdinario]
@idSalarioOrdinario int 
	as 	
	delete from SalarioOrdinario
	where idSalarioOrdinario = @idSalarioOrdinario

GO
-------Listar--------
create procedure [dbo].[sp_listarBonificacion]
as
select b.idBonificacion,b.BonificacionEmpleado,b.fechaBonificacion,empleado.nombre from Bonificacion as b 
inner join empleado on b.idEmpleado = empleado.idempleado

GO
CREATE procedure [dbo].[sp_ListarEmpleados]
as
select 
	idEmpleado,Nombre,Apellido,FechaInicio,Estado,idUsuario from Empleado

GO
create procedure [dbo].[listarBonificacion]
as
select b.idBonificacion,b.BonificacionEmpleado,b.fechaBonificacion,empleado.nombre from Bonificacion as b 
inner join empleado on b.idEmpleado = empleado.idempleado

GO
create procedure [dbo].[sp_listarIgss]
as
select idIgss,anio,Cuotaigss from Igss

GO
create procedure [dbo].[sp_ListarPlanilla]
@Anio Varchar(128),
@Mes varchar(128)
as
select idPlanilla,Empleado.Nombre,Empleado.Apellido,anio,BonificacionEmpleado,CuotaSalarioOrdinario,total,
RetencionEmpleado,CuotaIgss,liquido,fechaGenerada,mes 
from planillaSQL 
inner join 
Empleado on Empleado.idEmpleado=planillaSQL.idEmpleado
where anio=@Anio and mes=@mes

 GO
 create procedure [dbo].[sp_AddPlanillas]
 @Anio varchar(128),
 @mes varchar(128)
 as
 insert into planillaSQL(idEmpleado,BonificacionEmpleado,anio,CuotaSalarioOrdinario,total,RetencionEmpleado,CuotaIgss,liquido,fechaGenerada,mes)(
 Select distinct empleado.idEmpleado,Bonificacion.BonificacionEmpleado,
	   SalarioOrdinario.anio,SalarioOrdinario.CuotaSalarioOrdinario,
	   (SalarioOrdinario.CuotaSalarioOrdinario +BonificacionEmpleado + RetencionISR.RetencionEmpleado) as total,
	   RetencionISR.RetencionEmpleado,igss.Cuotaigss,(SalarioOrdinario.CuotaSalarioOrdinario +BonificacionEmpleado -igss.Cuotaigss)as liquido,
	   (GETDATE()) as fechaGenerada,@mes
 from Empleado,SalarioOrdinario,Igss,Bonificacion,RetencionISR
 where SalarioOrdinario.anio=Igss.anio and RetencionISR.idEmpleado=Empleado.idEmpleado and Bonificacion.idEmpleado=Empleado.idEmpleado and @Anio=Igss.anio and @Anio=SalarioOrdinario.anio)

GO
create procedure [dbo].[sp_listarRetencion]
as
	select r.idRetencionISR,r.RetencionEmpleado,r.fechaRetencion,empleado.nombre from RetencionISR as r 
	inner join empleado on r.idEmpleado = empleado.idempleado
GO
create procedure [dbo].[sp_listarSalarioOrdinario]
	as
	select idSalarioOrdinario,anio,CuotaSalarioOrdinario from SalarioOrdinario

GO
create procedure [dbo].[sp_ListarUsuarios]
	as
	select idUsuario,
			nombre,
			apellido,
			username,
			password
			from Usuario
	return 

GO
----Modificar------
create procedure [dbo].[sp_ModificarBonificacion]
	@idBonificacion int, 
	@BonificacionEmpleado varchar(128),
	@idEmpleado varchar(2)
	as
	begin 
	update Bonificacion set BonificacionEmpleado = @BonificacionEmpleado , fechaBonificacion = GETDATE(), idEmpleado=@idEmpleado 
	where idBonificacion = @idBonificacion
	end

GO
CREATE procedure [dbo].[sp_ModificarEmpleado]
	@idEmpleado int, 
	@nombre varchar(128),
	@apellido varchar(128),
	@Estado varchar(128),
	@idUsuario int
	as
	begin 
	update Empleado set Nombre = @nombre,Apellido=@apellido,FechaInicio=GETDATE(),Estado=@Estado,idUsuario=@idUsuario 
	where idEmpleado = @idEmpleado
	end


GO
create procedure [dbo].[sp_modificarIgss]
	@idIgss int, 
	@anio int,
	@CuotaIgss varchar(128)
	as
	begin 
	update	Igss set anio  = @anio ,Cuotaigss=@CuotaIgss
	where idIgss = @idIgss
	end

GO
create procedure [dbo].[sp_ModificarRetencion]
	@idRetencion int, 
	@RetencionEmpleado varchar(128),
	@idEmpleado varchar(2)
	as
	begin 
	update RetencionISR set RetencionEmpleado = @RetencionEmpleado , fechaRetencion = GETDATE(), idEmpleado=@idEmpleado 
	where idRetencionISR = @idRetencion
	end

GO
create procedure [dbo].[sp_modificarSalarioOrdinairo]
	@idSalarioOrdinario int, 
	@anio int,
	@CuotaSalarioOrdinario varchar(128)
	as
	begin 
	update	SalarioOrdinario set anio  = @anio ,CuotaSalarioOrdinario=@CuotaSalarioOrdinario
	where idSalarioOrdinario = @idSalarioOrdinario
	end

GO

create procedure sp_listarAnios
as
	select SalarioOrdinario.idSalarioOrdinario,SalarioOrdinario.anio,SalarioOrdinario.CuotaSalarioOrdinario from SalarioOrdinario inner join 
	Igss on igss.anio=SalarioOrdinario.anio

go
------------------------------------
 create procedure [dbo].[sp_AddPlanilla]
 @Anio varchar(128),
 @mes varchar(128)
 as
 insert into planillaSQL(idEmpleado,BonificacionEmpleado,anio,CuotaSalarioOrdinario,total,RetencionEmpleado,CuotaIgss,liquido,fechaGenerada,mes)(
 Select distinct empleado.idEmpleado,Bonificacion.BonificacionEmpleado,
	   SalarioOrdinario.anio,SalarioOrdinario.CuotaSalarioOrdinario,
	   (SalarioOrdinario.CuotaSalarioOrdinario +BonificacionEmpleado + RetencionISR.RetencionEmpleado) as total,
	   RetencionISR.RetencionEmpleado,igss.Cuotaigss,(SalarioOrdinario.CuotaSalarioOrdinario +BonificacionEmpleado -igss.Cuotaigss)as liquido,
	   (GETDATE()) as fechaGenerada,@mes
 from Empleado,SalarioOrdinario,Igss,Bonificacion,RetencionISR
 where Bonificacion.idEmpleado=Empleado.idEmpleado and @Anio=Igss.anio and @Anio=SalarioOrdinario.anio and RetencionISR.idEmpleado=Empleado.idEmpleado)
 go
 create procedure sp_modificarPlanilla
	@idPlanilla int,
	@Bonificacion real,
	@igss	real,
	@Retencion real,
	@SueldoOrdinario real
	as
	begin
	update planillaSQL set BonificacionEmpleado=@Bonificacion,CuotaIgss=@igss,RetencionEmpleado=@Retencion,CuotaSalarioOrdinario=@SueldoOrdinario
	where idPlanilla = @idPlanilla
	end
GO

create procedure [dbo].[sp_eliminarPlanilla]
	@idPlanilla int 
	as 	
	delete from planillaSQL
	where idPlanilla = @idPlanilla
go