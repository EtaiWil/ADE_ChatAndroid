const tokenService = require('../services/Token');

const Token = async (req, res) => {
  console.log("this is " + req.body.username + req.body.password)
  let result = await tokenService.Token(req.body.username, req.body.password);
  if (result==="not") {
    return res.status(404).send('Incorrect username and/or password');
  }
  console.log("the result is" + result);
  res.send(result);
};

module.exports = {
  Token: Token
};
