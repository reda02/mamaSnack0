var express = require('express');
var app = express();
var http    = require('http').Server(app);
var io = require('socket.io')(http);
var mysql=require('mysql');
var cnx=mysql.createConnection({
    host:'localhost',
    user:'root',
    password:'',
    database:'db_mamasnack'
});
var users={};
/*cnx.query("select * from categorie",function(err,rows,field){
    if(!err){
        console.log("connect to wamp");
        console.log(rows);
    } 
});*/

/*var cat={nom_categorie:"dinner"};
cnx.query("insert into categorie set ?",cat,function(err,res){
    console.log(res);
});*/
var msg,dt,usermsg;
var cpt=5;
io.on('connection', function(socket){
  console.log("we have a connection");
  socket.on("chat message", function(data){
	  dt=new Date();
	  msg={
		  contenu_mesg:data,
		  date_enregistrement:dt.getFullYear()+"-"+(dt.getMonth()+1)+"-"+ dt.getDate()+ " "+dt.getHours()+":"+dt.getMinutes()+":"+ dt.getSeconds(),  
	  };
	  
	  
	  cnx.query("insert into message set ?",msg,function(err,res){
		    //console.log(res);
		    usermsg={
				id_user_dist:socket.des,
				id_user_exp:socket.rec,
				id_msg:res.insertId,
			};
		    cnx.query("insert into users_and_message set ?",usermsg,function(err,res){
			  	//console.log(res);
		    });
	  });
	 
	  io.emit("new message", {msg: data, des:socket.des, rec:socket.rec});
  });
  socket.on("users",function(data,callback){
	  callback(true);
	  socket.des=data.des;
	  socket.rec=data.rec;
	  cnx.query("select u.id_user_dist,m.contenu_mesg from message m inner join users_and_message u on m.id_msg=u.id_msg where (u.id_user_dist="+socket.des+" and u.id_user_exp="+socket.rec+") or (u.id_user_dist="+socket.rec+" and u.id_user_exp="+socket.des+") order by m.date_enregistrement",function(err,rows,fields){
		    if(!err){
		    	console.log(rows);
		        io.emit("load message",rows,data.des);
		    }
		});
  });
  
});

http.listen('3000', function(){
  console.log("we are connected");
});
