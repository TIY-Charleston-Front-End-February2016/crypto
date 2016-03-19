var Backbone = require('backbone');

module.exports = Backbone.Model.extend({
  urlCryptoRoot: '/cryptograms',
  initialize: function(){
    console.log('message model created');
  }
})
