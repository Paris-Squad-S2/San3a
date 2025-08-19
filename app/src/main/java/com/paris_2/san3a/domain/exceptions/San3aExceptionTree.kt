package com.paris_2.san3a.domain.exceptions

abstract class San3aException(message: String) : Exception(message)
class NoInternetConnectionException() : San3aException("No internet connection")
class FailException(message: String) : San3aException(message)