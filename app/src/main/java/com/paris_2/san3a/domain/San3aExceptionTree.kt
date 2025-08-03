package com.paris_2.san3a.domain

abstract class San3aException(message: String) : Exception(message)

class NoInternetConnectionException(message: String = "No internet connection") :
    San3aException(message)

class RegisterException(message: String = "register error") : San3aException(message)

class SendMessageException(id: String) : San3aException("Message with id $id is not send")
class ReadMessagesException(id: String) :
    San3aException("Messages with related chat id $id is cant be read")

class ReadChatException(id: String) : San3aException("Chat with id $id is cant be read")
class DeleteChatException(id: String) : San3aException("Chat with id $id is cant be deleted")
class CreateChatException(participants: List<String>): San3aException("Chat with participants $participants is cant be created")

class SavePhoneNumberException : San3aException("Failed to save phone number")
class PhoneNumberCheckException : San3aException("Failed to check if phone number is saved")
class GetAllServicesException : San3aException("Services can't be read")
class SetOnboardingCompletedException : San3aException("Failed to set onboarding completed")
class GetOnboardingCompletedException : San3aException("Failed to get onboarding completed status")

class SaveAccountTypeException : San3aException("Account type couldn't be saved")
class GetAccountTypeException : San3aException("Account type couldn't be retrieved")
class SaveServicesException : San3aException("Services couldn't be saved")
class SaveLocationException : San3aException("Location couldn't be saved")
class SavePersonalInfoException : San3aException("Personal info couldn't be saved")
class SaveWorkShowcaseException : San3aException("Work showcase couldn't be saved")
class GetUserProgressException : San3aException("Couldn't get user progress")
class CompleteUserSetupException : San3aException("Couldn't complete user setup")
class UploadNationalIdImagesException : San3aException("Couldn't upload national ID images")
class GetStatsException : San3aException("Couldn't get user stats")
class GetRecentRelatedJobsException : San3aException("Couldn't get recent related jobs")
class SearchServicesException : San3aException("Couldn't search services")
class GetMostRequestedServicesException : San3aException("Couldn't get most requested services")
class GetAvailableJobsException : San3aException("Couldn't get available jobs")
class NoGovernmentsFoundException : San3aException("No Governments Found")
class NoCitiesFoundException : San3aException("No Cities Found")

class LoginStatusException: San3aException("Failed to check login status")
class GetUserException(message: String = "register error"): San3aException("Failed to get User $message")