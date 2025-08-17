package com.paris_2.san3a.di

import com.paris_2.san3a.domain.usecase.notification.AddNotificationUseCase
import com.paris_2.san3a.domain.usecase.AddRatingForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.AddUserUseCase
import com.paris_2.san3a.domain.usecase.CustomizeProfileSettingsUseCase
import com.paris_2.san3a.domain.usecase.GetAllServicesUseCase
import com.paris_2.san3a.domain.usecase.GetAvailableJobsUseCase
import com.paris_2.san3a.domain.usecase.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.GetMostRequestedServicesUseCase
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetRatingForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.GetCustomerRatingOnCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.GetRecentRelatedJobsUseCase
import com.paris_2.san3a.domain.usecase.GetServiceByIdUseCase
import com.paris_2.san3a.domain.usecase.GetStatsUseCase
import com.paris_2.san3a.domain.usecase.GetUserServicesUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.GetVersionNameUseCase
import com.paris_2.san3a.domain.usecase.GetWorkMediaUseCase
import com.paris_2.san3a.domain.usecase.IncrementJobsDoneForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.IsLoggedInUseCase
import com.paris_2.san3a.domain.usecase.IsOnboardingCompletedUseCase
import com.paris_2.san3a.domain.usecase.RequestServiceUseCase
import com.paris_2.san3a.domain.usecase.SavePhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.SendOtpUseCase
import com.paris_2.san3a.domain.usecase.SetLoginUseCase
import com.paris_2.san3a.domain.usecase.SetOnboardingCompletedUseCase
import com.paris_2.san3a.domain.usecase.SetUpAccountUseCase
import com.paris_2.san3a.domain.usecase.notification.GetNotificationsUseCase
import com.paris_2.san3a.domain.usecase.UpdateEarningsForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.UpdateNumOfRequestsUseCase
import com.paris_2.san3a.domain.usecase.messages.CreateChatUseCase
import com.paris_2.san3a.domain.usecase.messages.DeleteChatByIdUseCase
import com.paris_2.san3a.domain.usecase.messages.GetChatsByUserIdUseCase
import com.paris_2.san3a.domain.usecase.messages.GetMessagesByChatIdUseCase
import com.paris_2.san3a.domain.usecase.messages.MarkMessagesAsSeenUseCase
import com.paris_2.san3a.domain.usecase.messages.SendMessageUseCase
import com.paris_2.san3a.domain.usecase.notification.GetUnReadNotificationsCountUseCase
import com.paris_2.san3a.domain.usecase.notification.MarkNotificationsAsReadUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.AcceptOfferUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.AddOfferUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.CancelRequestUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetAcceptedOfferOnRequestUseCaseUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetAcceptedOffersUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetCraftManOfferOnRequestUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetOffersCountUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetOffersUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetRequestDetailsByIdUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetYourOfferUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.MarkRequestAsDoneUseCase
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
    factoryOf(::IsLoggedInUseCase)
    factoryOf(::SetLoginUseCase)
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