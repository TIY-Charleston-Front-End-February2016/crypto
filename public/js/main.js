var $ = require('jquery');
var Backbone = require('backbone');
var UserCollection = require('./usercollection');
var UserCollectionView = require('./usercollectionview');
var AddUserView = require('./adduserview');

$(document).ready(function(){
  // var finalBitterCol = new BitterCollection();
  new AddUserView();
  console.log('helloo');
  // $(.navbar).append('<h3>' + 'hellow' + '</h3>');

  // finalBitterCol.fetch().then(function(data){
  //   var collection = new BitterCollection(data);
  //   new BitterCollectionView({collection: collection});

});
