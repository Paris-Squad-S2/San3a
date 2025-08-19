package com.paris_2.san3a.di

import com.paris_2.san3a.domain.usecase.notification.AddNotificationUseCase
import com.paris_2.san3a.domain.usecase.user.AddRatingForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.user.AddUserUseCase
import com.paris_2.san3a.domain.usecase.user.CustomizeProfileSettingsUseCase
import com.paris_2.san3a.domain.usecase.services.GetAllServicesUseCase
import com.paris_2.san3a.domain.usecase.requests.GetAvailableJobsUseCase
import com.paris_2.san3a.domain.usecase.location.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.services.GetMostRequestedServicesUseCase
import com.paris_2.san3a.domain.usecase.user.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.user.GetRatingForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.user.GetCustomerRatingOnCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.requests.GetRecentRelatedJobsUseCase
import com.paris_2.san3a.domain.usecase.services.GetServiceByIdUseCase
import com.paris_2.san3a.domain.usecase.user.GetStatsUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserServicesUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserUseCase
import com.paris_2.san3a.domain.usecase.user.GetVersionNameUseCase
import com.paris_2.san3a.domain.usecase.user.GetWorkMediaUseCase
import com.paris_2.san3a.domain.usecase.user.IncrementJobsDoneForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.user.IsOnboardingCompletedUseCase
import com.paris_2.san3a.domain.usecase.requests.RequestServiceUseCase
import com.paris_2.san3a.domain.usecase.user.SavePhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.user.SendOtpUseCase
import com.paris_2.san3a.domain.usecase.user.SetOnboardingCompletedUseCase
import com.paris_2.san3a.domain.usecase.user.SetUpAccountUseCase
import com.paris_2.san3a.domain.usecase.notification.GetNotificationsUseCase
import com.paris_2.san3a.domain.usecase.user.UpdateEarningsForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.services.UpdateNumOfRequestsUseCase
import com.paris_2.san3a.domain.usecase.messaging.CreateChatUseCase
import com.paris_2.san3a.domain.usecase.messaging.DeleteChatByIdUseCase
import com.paris_2.san3a.domain.usecase.messaging.GetChatsByUserIdUseCase
import com.paris_2.san3a.domain.usecase.messaging.GetMessagesByChatIdUseCase
import com.paris_2.san3a.domain.usecase.messaging.MarkMessagesAsSeenUseCase
import com.paris_2.san3a.domain.usecase.messaging.SendMessageUseCase
import com.paris_2.san3a.domain.usecase.notification.GetUnReadNotificationsCountUseCase
import com.paris_2.san3a.domain.usecase.notification.MarkNotificationsAsReadUseCase
import com.paris_2.san3a.domain.usecase.requests.AcceptOfferUseCase
import com.paris_2.san3a.domain.usecase.requests.AddOfferUseCase
import com.paris_2.san3a.domain.usecase.requests.CancelRequestUseCase
import com.paris_2.san3a.domain.usecase.requests.GetAcceptedOfferOnRequestUseCaseUseCase
import com.paris_2.san3a.domain.usecase.requests.GetAcceptedOffersUseCase
import com.paris_2.san3a.domain.usecase.requests.GetCraftManOfferOnRequestUseCase
import com.paris_2.san3a.domain.usecase.requests.GetOffersCountUseCase
import com.paris_2.san3a.domain.usecase.requests.GetOffersUseCase
import com.paris_2.san3a.domain.usecase.requests.GetRequestDetailsByIdUseCase
import com.paris_2.san3a.domain.usecase.requests.GetYourOfferUseCase
import com.paris_2.san3a.domain.usecase.requests.MarkRequestAsDoneUseCase
import com.paris_2.san3a.domain.usecase.requests.GetCustomerRequestsUseCase
import com.paris_2.san3a.domain.usecase.requests.GetCraftsManRequestsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::SendMessageUseCase)
    factoryOf(::GetChatsByUserIdUseCase)
    factoryOf(::GetMessagesByChatIdUseCase)
    factoryOf(::DeleteChatByIdUseCase)
    factoryOf(::SetUpAccountUseCase)
    factoryOf(::IsOnboardingCompletedUseCase)
    factoryOf(::SetOnboardingCompletedUseCase)
    factoryOf(::SavePhoneNumberUseCase)
    factoryOf(::GetAllServicesUseCase)
    factoryOf(::GetServiceByIdUseCase)
    factoryOf(::SendOtpUseCase)
    factoryOf(::GetStatsUseCase)
    factoryOf(::GetRecentRelatedJobsUseCase)
    factoryOf(::GetAvailableJobsUseCase)
    factoryOf(::GetMostRequestedServicesUseCase)
    factoryOf(::RequestServiceUseCase)
    factoryOf(::GetPhoneNumberUseCase)
    factoryOf(::CreateChatUseCase)
    factoryOf(::GetLocationInfoUseCase)
    factoryOf(::GetUserServicesUseCase)
    factoryOf(::GetUserUseCase)
    factoryOf(::AddUserUseCase)
    factoryOf(::GetWorkMediaUseCase)
    factoryOf(::UpdateNumOfRequestsUseCase)
    factoryOf(::MarkMessagesAsSeenUseCase)
    factoryOf(::AddNotificationUseCase)
    factoryOf(::GetNotificationsUseCase)
    factoryOf(::GetUnReadNotificationsCountUseCase)
    factoryOf(::MarkNotificationsAsReadUseCase)
    factoryOf(::CustomizeProfileSettingsUseCase)
    factoryOf(::GetCustomerRequestsUseCase)
    factoryOf(::GetVersionNameUseCase)
    factoryOf(::GetRequestDetailsByIdUseCase)
    factoryOf(::AcceptOfferUseCase)
    factoryOf(::GetOffersUseCase)
    factoryOf(::GetOffersCountUseCase)
    factoryOf(::GetRequestDetailsByIdUseCase)
    factoryOf(::AddOfferUseCase)
    factoryOf(::GetYourOfferUseCase)
    factoryOf(::GetAcceptedOffersUseCase)
    factoryOf(::GetCraftManOfferOnRequestUseCase)
    factoryOf(::GetCraftsManRequestsUseCase)
    factoryOf(::CancelRequestUseCase)
    factoryOf(::GetAcceptedOfferOnRequestUseCaseUseCase)
    factoryOf(::MarkRequestAsDoneUseCase)
    factoryOf(::AddRatingForCraftsmanUseCase)
    factoryOf(::GetRatingForCraftsmanUseCase)
    factoryOf(::GetCustomerRatingOnCraftsmanUseCase)
    factoryOf(::UpdateEarningsForCraftsmanUseCase)
    factoryOf(::IncrementJobsDoneForCraftsmanUseCase)
}