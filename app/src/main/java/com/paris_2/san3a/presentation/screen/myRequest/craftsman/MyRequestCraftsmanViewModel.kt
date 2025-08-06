package com.paris_2.san3a.presentation.screen.myRequest.craftsman

import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.delay

class MyRequestCraftsmanViewModel : BaseViewModel<MyRequestCraftsmanScreenState>(MyRequestCraftsmanScreenState.Loading) {

    init {
        getRequests()
    }

    private fun getRequests() {
        updateState(MyRequestCraftsmanScreenState.Loading)

        tryToExecute(
            onSuccess = { result: List<MyRequestCraftsmanUi> ->
                val ongoing = result.filter { it.status == RequestStatus.ONGOING }
                val completed = result.filter { it.status == RequestStatus.COMPLETED }
                val canceled = result.filter { it.status == RequestStatus.CANCELLED }
                updateState(MyRequestCraftsmanScreenState.Success(ongoing, completed, canceled))
            },
            onError = {
                updateState(MyRequestCraftsmanScreenState.Error("Something went wrong"))
            },
            execute = {
                delay(1000)
                listOf(
                    MyRequestCraftsmanUi(isCraftsmanVerified = true,
                        status = RequestStatus.ONGOING,
                        craftsmanURL = "",
                        isAcceptedOffer = true),
                    MyRequestCraftsmanUi(status = RequestStatus.ONGOING),
                    MyRequestCraftsmanUi(status = RequestStatus.CANCELLED)
                )
            }
        )
    }
}