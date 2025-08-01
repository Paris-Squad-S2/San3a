package com.paris_2.san3a.domain

abstract class San3aException(message: String) : Exception(message)

class NoInternetConnectionException(message: String = "No internet connection") : San3aException(message)
class RegisterException(message: String = "register error") : San3aException(message)

class SendMessageException(id: String) : San3aException("Message with id $id is not send")
class ReadMessagesException(id: String) : San3aException("Messages with related chat id $id is cant be read")

class ReadChatException(id: String) : San3aException("Chat with id $id is cant be read")
class DeleteChatException(id: String) : San3aException("Chat with id $id is cant be deleted")

class SavePhoneNumberException : San3aException("Failed to save phone number")
class PhoneNumberCheckException : San3aException("Failed to check if phone number is saved")