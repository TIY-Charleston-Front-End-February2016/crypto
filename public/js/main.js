var $ = require('jquery');
var Backbone = require('backbone');
var UserCollection = require('./usercollection');
var UserCollectionView = require('./usercollectionview');
var AddUserView = require('./adduserview');
var MsgCollection = require('./messageCollection');
var MsgCollectionView = require('./messageCollectionView');
var AddMsgView = require('./sendMessageView');
var MsgModel = require('./messageModel');
var MsgModelView = require('./messageModelView');

$(document).ready(function(){

  new AddUserView();

  new AddMsgView();

  var cryptograms = new MsgCollection();
    cryptograms.fetch().then(function (data) {
      new MsgCollectionView({collection: cryptograms});
      var addMsgForm = new AddMsgView({collection: cryptograms});
 });

});
