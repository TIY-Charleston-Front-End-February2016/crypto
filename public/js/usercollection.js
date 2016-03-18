var Backbone = require('backbone');
var UserModel = require('./usermodel');
module.exports = Backbone.Collection.extend({
  model: UserModel,
  url: '/users',
  initialize: function () {

  }
});
