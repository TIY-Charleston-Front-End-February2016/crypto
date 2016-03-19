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
    this.$el.append(this.render());
  },
  render: function(){
    var markup = this.template();
    console.log("TEST", markup);
    this.$el.html(markup);
    return this;
  },
  events: {
    'click .sendMsg': 'createCrypto',
    // 'click .back': 'goBack'
  },
  createCrypto: function(evt){
  evt.preventDefault();
  var newCrypto = {
    id: null,
    scramble: null,
    sender: null,
    isSolved: null,
    timeStamp: null,
    recipient: this.$el.find('.recipient').val(),
    hint: this.$el.find('.hint').val(),
    originalMessage: this.$el.find('.message').val(),
  };
  var newMsgModel = new messageModel(newCrypto);
  window.glob = newMsgModel;
  // this.$el.find('input').val('');
  newMsgModel.save();
  this.listenTo(this.collection, 'add', this.addAll);
},
});
