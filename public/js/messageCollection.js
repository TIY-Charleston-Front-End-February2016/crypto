var Backbone = require('backbone');
var messageModel = require('./messageModel');

module.exports = Backbone.Collection.extend({
  model: messageModel,
  url: '/cryptograms',
  initialize: function(){
    console.log('message collection initted');
  }
})
