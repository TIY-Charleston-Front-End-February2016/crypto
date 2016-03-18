var Backbone = require('backbone');
var _ = require('underscore');
var tmpl = require('./templates');
var ModelView = require('./modelView');
// model view
module.exports = Backbone.View.extend({
  el: '.content',
  initialize: function () {
    this.addAll();
    this.listenTo(this.collection, 'update', this.addAll);
  },
  addOne: function (model) {
    var modelView = new ModelView({model: model});
    this.$el.append(modelView.render().el);
  },
  addAll: function () {
    _.each(this.collection.models, this.addOne, this);
  }
})
