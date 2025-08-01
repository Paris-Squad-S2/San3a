package com.paris_2.san3a.domain

abstract class San3aException(message: String) : Exception(message)

class SendMessageException(id: String) : San3aException("Message with id $id is not send")
class ReadMessagesException(id: String) : San3aException("Messages with related chat id $id is cant be read")

class ReadChatException(id: String) : San3aException("Chat with id $id is cant be read")
class DeleteChatException(id: String) : San3aException("Chat with id $id is cant be deleted")