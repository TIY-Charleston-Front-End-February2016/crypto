var $ = require('jquery');
var Backbone = require('backbone');
var tmpl = require('./templates');
var _ = require('underscore');
var userModel = require('./usermodel');
var loginModel = require('./loginmodel');
module.exports = Backbone.View.extend({
  el: '.navbar',
  template: _.template(tmpl.addUser),
  templateUser: _.template(tmpl.userModel),
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
    'click .create': 'addUser',
    'click .login': 'logIn',
    'click .logout': 'logOut'
  },
  addUser: function(evt){
    evt.preventDefault();
    var newUser = {
      name: this.$el.find('input[name="usernameC"]').val(),
      passwordHash: this.$el.find('input[name="passwordC"]').val(),

    };
    var newUserModel = new userModel(newUser);
    this.$el.find('input').val('');
    newUserModel.save();
    this.listenTo(this.collection, 'add', this.addAll);
    var markup = this.templateUser(newUser)
    this.$el.html(markup);
  },

  logIn: function(evt){
    evt.preventDefault();
    var exsistingUser = {
      name: this.$el.find('input[name="username"]').val(),
      passwordHash: this.$el.find('input[name="password"]').val(),

    };
    var newExistingModel = new loginModel(exsistingUser);
    this.$el.find('input').val('');
    newExistingModel.save();
    this.listenTo(this.collection, 'add', this.addAll);
    var markup = this.templateUser(exsistingUser)
    this.$el.html(markup);
  },

  logOut: function(evt){
    evt.preventDefault();
    var markup = this.template;
    this.$el.html(markup);
  },


});
