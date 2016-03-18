var Backbone = require('backbone');
module.exports = Backbone.Model.extend({
  urlRoot: '/users',
  initialize: function () {
    console.log("user model has been created.");
  }
});
