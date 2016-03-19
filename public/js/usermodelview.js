var Backbone = require('backbone');
var tmpl = require('./templates');
var _ = require('underscore');
module.exports = Backbone.View.extend({
  el: '.navbar',
  template: _.template(tmpl.userModel),
  initialize: function () {
    this.listenTo(this.model, 'change', this.render);
  },
  render: function () {

    var markup = this.template(this.model.toJSON());
    console.log(markup)
    this.$el.html(markup);
    return this;
  },
  events:{
    // 'click .delete': 'removeBitter',
},



});




















// var $ = require('jquery');
// var Backbone = require('backbone');
// var tmpl = require('./templates');
// var _ = require('underscore');
// var userModel = require('./usermodel')
// module.exports = Backbone.View.extend({
//   el: '.navbar',
//   template: tmpl.userModel,
//   initialize: function () {
//     this.$el.append(this.render());
//
//
//   },
//   render: function () {
//     var markup = this.template;
//     this.$el.html(markup);
//     return this;
//   },
//   events: {
//     'click .logout': 'logOutUser',
//     'click .send': 'sendMsg'
//   },
//   logOutUser: function(evt){
//     evt.preventDefault();
//     var newUserModel = new userModel(newUser);
//     this.$el.find('input').val('');
//     newUserModel.save();
//     this.listenTo(this.collection, 'add', this.addAll);
//   },
//   sendMsg: function(evt){
//     evt.preventDefault();
//     var newUserModel = new userModel(newUser);
//     this.$el.find('input').val('');
//     newUserModel.save();
//     this.listenTo(this.collection, 'add', this.addAll);
//   }
// });
