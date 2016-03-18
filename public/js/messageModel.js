var Backbone = require('backbone');

module.exports = Backbone.Model.extend({
  urlRoot: '/cryptograms',
  initialize: function(){
    console.log('message model initted');
  }
})
