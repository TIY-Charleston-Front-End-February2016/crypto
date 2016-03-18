var Backbone = require('backbone');
var tmpl = require('./templates');
var _ = require('underscore');
var $ = require('jquery');

module.exports = Backbone.View.extend({
  el: '.appendGame',
  template: _.template(tmpl.gamePage),
  initialize: function(){},
  events: function(){},
  addOne: function(){
    var markup = this.template(this.model.toJSON());
    console.log(markup);
    this.$el.html(markup);
    return this;
  },
  render: function(){}
});
