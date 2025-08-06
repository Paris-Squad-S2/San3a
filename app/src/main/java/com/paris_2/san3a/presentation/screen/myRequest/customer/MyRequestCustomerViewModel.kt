package com.paris_2.san3a.presentation.screen.myRequest.customer

import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.delay

class MyRequestCustomerViewModel(
    
) : BaseViewModel<MyRequestCustomerScreenState>(MyRequestCustomerScreenState.Loading) {

    init {
        getRequests()
    }

    private fun getRequests() {
        updateState(MyRequestCustomerScreenState.Loading)

        tryToExecute(
            onSuccess = { result: List<MyRequestCustomerUi> ->
                val ongoing = result.filter { it.status == RequestStatus.ONGOING }
                val completed = result.filter { it.status == RequestStatus.COMPLETED }
                val canceled = result.filter { it.status == RequestStatus.CANCELLED }
                updateState(MyRequestCustomerScreenState.Success(ongoing, completed, canceled))
            },
            onError = {
                updateState(MyRequestCustomerScreenState.Error("Something went wrong"))
            },
            execute = {
                delay(1000)
                listOf(
                    MyRequestCustomerUi(isCraftsmanVerified = true,
                        status = RequestStatus.ONGOING,
                        craftsmanURL = "",
                        isAcceptedOffer = true),
                    MyRequestCustomerUi(status = RequestStatus.ONGOING),
                    MyRequestCustomerUi(status = RequestStatus.CANCELLED)
                )
            }
        )
    }
}