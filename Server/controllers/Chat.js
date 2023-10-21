const jwt = require("jsonwebtoken");
const isLoggedIn = require("./User").isLoggedIn;
const chatService = require("../services/Chat");
const {response} = require("express");

const getMessages= async(req,res)=>{
    console.log("hellohello")

  const username = isLoggedIn(req);
if (username) {
      const x = await chatService.getMessages(req.params.id);
    res.json(x);
} else {
      res.status(401).send("Not logged in");
    }
}

const addMessage = async (req, res) => {
  const username = isLoggedIn(req);
  if (username) {
      console.log("somebody is trying to add a message");
      console.log(req.body);
    // console.log(req.params.id); // gets the unique id
    // console.log(req.params.body.msg); 
    res.json(await chatService.addMessage(username, req.params.id, req.body.msg));
  } else {
    res.status(401).send("Not logged in");
  }
};
const getAllChats= async(req,res)=>{
  const username=isLoggedIn(req);
   if (username) {
      const x = await chatService.getAllChats(username);
      res.json(x);
    } else {
      res.status(401).send("Not logged in");
    }
}

const addChat = async (req, res) => {

  console.log("somebody is trying to add chat");
  console.log(req.body);
  const username = isLoggedIn(req);
  console.log("the user name is "+username);
  console.log("the token is "+req.header("Authorization"))
  console.log("From addChat, the username is: " + req.body.username);
  console.log((req.body));
  if (username) {
    const result = await chatService.addChat(req.body.username, username);
        if(result==="not"){
          return res.status(401).send("Unauthorized");
        }
    res.json(result);
  } else {
    res.status(401).send("Not logged in");
  }
};


const deleteChat=async(req,res)=>{
const username=isLoggedIn(req);
if(username){
  let result=await chatService.deleteChat( req.params.id);
  if(result==="not"){
   return  res.status(204).send('Unauthorized');
  }
}
  res.status(401).send('Unauthorized');
}






const getChat = async (req,res)=>{

  const username = isLoggedIn(req);
  if (username) {
    let result=await chatService.getChat(req.params.id,username);
    //res.json(await userService.getUser(username));
      var t=3;
    if(result==="not"){
      return res.status(401).send("Unauthorized");
    }
    res.json(result);
  } else {
    res.status(401).send("Unauthorized");
  }
}

module.exports = {
  deleteChat: deleteChat,
  getAllChats: getAllChats,
  addMessage: addMessage,
  addChat: addChat,
  getChat: getChat,
  getMessages: getMessages
};
