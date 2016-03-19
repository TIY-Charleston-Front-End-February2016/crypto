User {
int id://send as null on create - id is generated server side
String name://Cannot be null, must be unique
String passwordHash://Cannot be null
}


//only hint and isSolved are allowed to be null(unless you are creating a cryptogram, then id  MUST be null, and scramble MAY be null)
Cryptogram {
int id://send as null on create - id is generated server side
String scramble://send as null on create - scramble is generated server side
User sender:
User recipient:
LocalDateTime timeStamp:
String originalMessage:
String hint:
Boolean isSolved:
}
