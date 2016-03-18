var templates = {};
templates.addUser = [

  `<div class="container-fluid navbar-left">
    <form class="form-inline" role="form" action="index.html" method="post">
      <div class="form-group">
        <input type="text" class="form-control username" name="username" placeholder="username">
        <input type="text" class="form-control password" name="password" placeholder="password">
      </div>
      <button type="submit" class="btn btn-info login">Login</button>
    </form>
  </div>
  <div class="container-fluid navbar-right">
    <form class="form-inline" role="form" action="index.html" method="post">
      <div class="form-group">
        <input type="text" class="form-control username" name="usernameC" placeholder="username">
        <input type="text" class="form-control password" name="passwordC" placeholder="password">
      </div>
      <button type="submit" class="btn btn-warning create">Create User</button>
    </form>
  </div>`


].join('');


templates.userModel =[
  `<div class="container-fluid welcome"><h1>Welcome, <%= name %> </h1> <button type="button" class="btn btn-danger logout">log out</button>
    <div class="container-fluid navbar-right">
      <button type="button" class="btn btn-default send">Send Message</button>
    </div>
  </div>`
].join('');

module.exports = templates;
