package com.paris_2.san3a.domain

abstract class San3aException(message: String) : Exception(message)

class UnKnownCityException(message: String = "Unknown city") : San3aException(message)
class UnKnownCountryException(message: String = "Unknown country") : San3aException(message)
class LocationErrorException(message: String = "Location not available") : San3aException(message)