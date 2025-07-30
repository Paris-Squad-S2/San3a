package com.paris_2.san3a.domain

abstract class San3aException(message: String) : Exception(message)

class NoInternetConnectionException(message: String = "No internet connection") : San3aException(message)
class RegisterException(message: String = "register error") : San3aException(message)
