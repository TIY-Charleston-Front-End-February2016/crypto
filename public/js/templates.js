var templates = {
  message: [
    `<div class="message panel-body">
      <h5><%= username %></h5>
      <p><%= hint %></p>
      <div class="btn-group msgBtns">
        <button type="button" class="btn btn-default" name="play"><span class="glyphicon glyphicon-play"></span></button>
        <button type="button" class="btn btn-default" name="destroy"><span class="glyphicon glyphicon-trash"></span></button>
      </div>
    </div>`
  ].join.(''),
  sendMsgForm: [
    `<form class="encrypt" role="form" action="index.html" method="post">
      <div class="form-group">
        <input type="text" class="form-control recipient" name="recipient" placeholder="username">
      </div>
      <div class="form-group">
        <input type="text" class="form-control hint" name="hint" placeholder="hint">
      </div>
      <div class="form-group">
        <input type="text" class="form-control message" name="message" placeholder="message">
      </div>
      <div class="btn-group sendMsgBtns">
        <button type="submit" class="btn btn-info sendMsg">Send</button>
        <button type="button" class="btn btn-warning back">Back</button>
      </div>
    </form>`
  ].join(''),
  gamePage: [
    `<div class="encryption panel-body">
      <h4 class="scramble"><%= scramble %></h4>
    </div>
      <div class="message panel-body">
        <form class="decrypt" role="form" action="index.html" method="post">
          <div class="form-group">
            <input type="text" class="form-control" name="input" placeholder="Decrypt the Message">
          </div>
          <div class="btn-group decryptBtns">
            <button type="submit" class="btn btn-info sendMsg">Send</button>
            <button type="button" class="btn btn-warning back">Back</button>
            <button type="button" class="btn btn-danger giveUp">Give Up</button>
          </div>
        </form>
      </div>`
  ].join('')
}
