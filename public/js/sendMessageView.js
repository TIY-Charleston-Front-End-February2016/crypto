var Backbone = require('backbone');
var tmpl = require('./templates');
var _ = require('underscore');
var $ = require('jquery');
var messageModel = require('./messageModel');

module.exports = Backbone.View.extend({
  el: '.sendMsgBody',
  template: _.template(tmpl.sendMsgForm),
  initialize: function(){
    console.log('send message view initted');
  },
  events: function() {
    // 'submit .sendMsg': 'sendMsg',
    // 'click .back': 'goBack'
  },
  addOne: function(){
    var markup = this.template();
    console.log("TEST", markup);
    this.$el.html(markup);
    return this;
  },
  render: function(){
    var markup = this.template();
    console.log("TEST", markup);
    this.$el.html(markup);
    return this;
  }
});
