var Backbone = require('backbone');
module.exports = Backbone.Model.extend({
  urlRoot: '/user',
  initialize: function () {
    console.log("user model has been created.");
  }
});
