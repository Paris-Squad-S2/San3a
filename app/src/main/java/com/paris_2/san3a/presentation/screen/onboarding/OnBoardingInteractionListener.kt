package com.paris_2.san3a.presentation.screen.onboarding

interface OnBoardingInteractionListener {
    fun onNextClicked()
    fun onSkipClicked()
    fun onPageChanged(index: Int)
}