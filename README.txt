User {
int id://send as null on create - id is generated server side
String name://Cannot be null, must be unique
String passwordHash://Cannot be null
}


//only hint and isSolved are allowed to be null(unless you are creating a cryptogram, then id has to be null)
Cryptogram {
int id://send as null on create - id is generated server side
String scramble:
User sender:
User recipient:
LocalDateTime timeStamp:
String originalMessage:
String hint:
Boolean isSolved:
}
