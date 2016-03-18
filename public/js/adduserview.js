var $ = require('jquery');
var Backbone = require('backbone');
var tmpl = require('./templates');
var _ = require('underscore');
var userModel = require('./usermodel')
module.exports = Backbone.View.extend({
  el: '.navbar',
  template: _.template(tmpl.addUser),
  initialize: function () {
    console.log("I WAS CALLED", this.$el);
  //  console.log(myTmpl);
   console.log(this.template);
    this.$el.append(this.render());


  },
  render: function () {
    var markup = this.template;
    this.$el.html(markup);
    return this;
  },
  events: {
    'click .create': 'addUser'
  },
  addUser: function(evt){
    evt.preventDefault();
    var newUser = {
      name: this.$el.find('input[name="usernameC"]').val(),
      password: this.$el.find('input[name="passwordC"]').val(),

    };
    var newUserModel = new userModel(newUser);
    this.$el.find('input').val('');
    newUserModel.save();
    this.listenTo(this.collection, 'add', this.addAll);
  },

});
