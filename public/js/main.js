var $ = require('jquery');
var Backbone = require('backbone');
var UserCollection = require('./usercollection');
var UserCollectionView = require('./usercollectionview');
var AddUserView = require('./adduserview');
var MsgView = require('./sendMessageView');

$(document).ready(function(){
  // var finalBitterCo
  // l = new BitterCollection();
  new AddUserView();

  var msgView = new MsgView({})
  msgView.render();
  console.log('helloo');
  // $(.navbar).append('<h3>' + 'hellow' + '</h3>');

  // finalBitterCol.fetch().then(function(data){
  //   var collection = new BitterCollection(data);
  //   new BitterCollectionView({collection: collection});

});
